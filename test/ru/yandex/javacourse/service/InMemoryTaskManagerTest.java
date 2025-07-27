package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;
import ru.yandex.javacourse.service.InMemoryTaskManager;
import ru.yandex.javacourse.service.TaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для InMemoryTaskManager")
class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();

        task = new Task("Обычная задача", "Описание задачи", Status.NEW);
        task.setId(1);

        epic = new Epic("Эпик задача", "Описание эпика");
        epic.setId(2);

        subtask = new Subtask("Подзадача", "Описание подзадачи", Status.NEW, epic.getId());
        subtask.setId(3);
    }

    @Test
    @DisplayName("Добавление задачи должно работать корректно")
    void addTask_shouldAppearInTaskList() {
        taskManager.addTask(task);
        Task saved = taskManager.getTask(task.getId());

        assertNotNull(saved);
        assertEquals(task, saved);
    }

    @Test
    @DisplayName("Добавление эпика и подзадачи должно связывать их")
    void addSubtask_shouldLinkToEpic() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        Subtask savedSubtask = taskManager.getSubtask(subtask.getId());
        Epic savedEpic = taskManager.getEpic(epic.getId());

        assertNotNull(savedSubtask);
        assertEquals(epic.getId(), savedSubtask.getEpicId());
        assertEquals(1, savedEpic.getSubtaskIds().size());
        assertTrue(savedEpic.getSubtaskIds().contains(subtask.getId()));
    }

    @Test
    @DisplayName("Удаление задачи должно убирать её из менеджера")
    void removeTask_shouldDeleteFromManager() {
        taskManager.addTask(task);
        taskManager.removeTaskById(task.getId());

        assertNull(taskManager.getTask(task.getId()));
    }

    @Test
    @DisplayName("Удаление эпика должно также удалять его подзадачи")
    void removeEpic_shouldAlsoRemoveSubtasks() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        taskManager.removeEpicById(epic.getId());

        assertNull(taskManager.getEpic(epic.getId()));
        assertNull(taskManager.getSubtask(subtask.getId()));
    }

    @Test
    @DisplayName("Обновление задачи должно сохранять изменения")
    void updateTask_shouldReflectChanges() {
        taskManager.addTask(task);
        task.setStatus(Status.DONE);

        taskManager.updateTask(task);
        Task updated = taskManager.getTask(task.getId());

        assertEquals(Status.DONE, updated.getStatus());
    }

    @Test
    @DisplayName("Получение всех задач, эпиков и подзадач")
    void getAllEntities_shouldReturnCorrectLists() {
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        List<Task> tasks = taskManager.getAllTasks();
        List<Epic> epics = taskManager.getAllEpics();
        List<Subtask> subtasks = taskManager.getAllSubtasks();

        assertEquals(1, tasks.size());
        assertEquals(1, epics.size());
        assertEquals(1, subtasks.size());
    }
}