package ru.yandex.javacourse.http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager manager;

    public EpicsHandler(TaskManager manager, Gson gson) {
        super(gson);
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {
                case "GET":
                    if (query != null && query.startsWith("id=")) {
                        int id = Integer.parseInt(query.substring(3));
                        Epic epic = manager.getEpicById(id);
                        if (epic != null) {
                            sendText(exchange, gson.toJson(epic), 200);
                        } else {
                            sendNotFound(exchange);
                        }
                    } else {
                        sendText(exchange, gson.toJson(manager.getEpics()), 200);
                    }
                    break;

                case "POST":
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    Epic epic = gson.fromJson(body, Epic.class);
                    if (manager.getEpicById(epic.getId()) != null) {
                        manager.updateEpic(epic);
                    } else {
                        manager.createEpic(epic);
                    }
                    sendText(exchange, "{\"result\": \"ok\"}", 201);
                    break;

                case "DELETE":
                    if (query != null && query.startsWith("id=")) {
                        int id = Integer.parseInt(query.substring(3));
                        manager.deleteEpicById(id);
                        sendText(exchange, "{\"result\": \"deleted\"}", 200);
                    } else {
                        manager.deleteEpics();
                        sendText(exchange, "{\"result\": \"all deleted\"}", 200);
                    }
                    break;

                default:
                    sendText(exchange, "{\"error\": \"Unsupported method\"}", 405);
            }
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }
}