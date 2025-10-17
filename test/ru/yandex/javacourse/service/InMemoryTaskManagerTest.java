package ru.yandex.javacourse.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    protected InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }

    @Test
    @DisplayName("Добавление и получение задачи")
    void shouldCreateAndGetTask() {
        Task task = new Task("Task1", "desc", Status.NEW,
                Duration.ofMinutes(15), LocalDateTime.now());
        taskManager.createTask(task);

        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Task1", tasks.get(0).getName());
    }

    @Test
    @DisplayName("Эпик и его подзадачи")
    void shouldCreateEpicAndSubtasks() {
        Epic epic = new Epic("Epic1", "desc");
        Epic savedEpic = taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Sub1", "desc", Status.NEW, savedEpic.getId(),
                Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.createSubtask(subtask);

        List<Subtask> epicSubtasks = taskManager.getSubtasksOfEpic(savedEpic.getId());
        assertEquals(1, epicSubtasks.size());
    }
}