package ru.yandex.javacourse.http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.javacourse.service.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager manager;

    public PrioritizedHandler(TaskManager manager, Gson gson) {
        super(gson);
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                sendText(exchange, gson.toJson(manager.getTasks()), 200);
            } else {
                sendText(exchange, "{\"error\": \"Unsupported method\"}", 405);
            }
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }
}