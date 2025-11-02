package ru.yandex.javacourse.http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.javacourse.model.Task;
import ru.yandex.javacourse.service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager manager;

    public TasksHandler(TaskManager manager, Gson gson) {
        super(gson);
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {
                case "GET":
                    if (query != null && query.startsWith("id=")) {
                        int id = Integer.parseInt(query.substring(3));
                        Task task = manager.getTaskById(id);
                        if (task != null) {
                            sendText(exchange, gson.toJson(task), 200);
                        } else {
                            sendNotFound(exchange);
                        }
                    } else {
                        sendText(exchange, gson.toJson(manager.getTasks()), 200);
                    }
                    break;

                case "POST":
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    Task task = gson.fromJson(body, Task.class);
                    if (manager.getTaskById(task.getId()) != null) {
                        manager.updateTask(task);
                        sendText(exchange, "{\"result\": \"updated\"}", 201);
                    } else {
                        manager.createTask(task);
                        sendText(exchange, "{\"result\": \"created\"}", 201);
                    }
                    break;

                case "DELETE":
                    if (query != null && query.startsWith("id=")) {
                        int id = Integer.parseInt(query.substring(3));
                        manager.deleteTaskById(id);
                        sendText(exchange, "{\"result\": \"deleted\"}", 200);
                    } else {
                        manager.deleteTasks();
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