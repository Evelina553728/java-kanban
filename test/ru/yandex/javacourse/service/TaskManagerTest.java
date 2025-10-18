package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для TaskManager")
abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    protected abstract T createTaskManager();

    @BeforeEach
    void setUp() {
        taskManager = createTaskManager();
    }

    @Test
    @DisplayName("Создание задач")
    void shouldCreateTasks() {
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW,
                Duration.ofMinutes(30), LocalDateTime.now());
        Task task2 = new Task("Задача 2", "Описание 2", Status.NEW,
                Duration.ofMinutes(60), LocalDateTime.now().plusHours(1));

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        List<Task> tasks = taskManager.getTasks();

        assertEquals(2, tasks.size());
    }

    @Test
    @DisplayName("Создание эпика")
    void shouldCreateEpic() {
        Epic epic = new Epic("Эпик 1", "Описание эпика");

        Epic savedEpic = taskManager.createEpic(epic);

        assertNotNull(savedEpic);
        assertEquals("Эпик 1", savedEpic.getName());
    }

    @Test
    @DisplayName("Создание подзадач для эпика")
    void shouldCreateSubtasksForEpic() {
        Epic epic = new Epic("Эпик 1", "Описание эпика");
        Epic savedEpic = taskManager.createEpic(epic);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1",
                Status.NEW, savedEpic.getId(), Duration.ofMinutes(15), LocalDateTime.now());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2",
                Status.NEW, savedEpic.getId(), Duration.ofMinutes(45), LocalDateTime.now().plusMinutes(20));

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        List<Subtask> subtasks = taskManager.getSubtasks();

        assertEquals(2, subtasks.size());
    }
}