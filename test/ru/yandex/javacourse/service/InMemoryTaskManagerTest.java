package ru.yandex.javacourse.service;

import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Task;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    @Test
    void addTaskShouldStoreTask() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Задача", "Описание");
        manager.addTask(task);

        assertEquals(1, manager.getAllTasks().size());
    }
}