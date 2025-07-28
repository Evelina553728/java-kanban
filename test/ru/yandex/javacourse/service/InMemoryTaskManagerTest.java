package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

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

        epic = new Epic("Эпик задача", "Описание эпика");
        taskManager.addEpic(epic);

        subtask = new Subtask("Подзадача", "Описание подзадачи", Status.NEW, epic.getId());

        task = new Task("Обычная задача", "Описание задачи", Status.NEW);
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
        taskManager.addSubtask(subtask);

        Subtask savedSubtask = taskManager.getSubtask(subtask.getId());
        Epic savedEpic = taskManager.getEpic(epic.getId());

        assertNotNull(savedSubtask, "Подзадача не сохранилась");
        assertEquals(epic.getId(), savedSubtask.getEpicId(), "ID эпика в подзадаче не совпадает");
        assertEquals(1, savedEpic.getSubtaskIds().size(), "Количество подзадач в эпике должно быть 1");
        assertTrue(savedEpic.getSubtaskIds().contains(savedSubtask.getId()), "Эпик не содержит ID подзадачи");
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
        taskManager.addSubtask(subtask);

        taskManager.removeEpicById(epic.getId());

        assertNull(taskManager.getEpic(epic.getId()), "Эпик не удалён");
        assertNull(taskManager.getSubtask(subtask.getId()), "Подзадача не удалена вместе с эпиком");
    }

    @Test
    @DisplayName("Обновление задачи должно сохранять изменения")
    void updateTask_shouldReflectChanges() {
        taskManager.addTask(task);

        task.setStatus(Status.DONE);
        taskManager.updateTask(task);

        Task updated = taskManager.getTask(task.getId());
        assertEquals(Status.DONE, updated.getStatus(), "Статус задачи не обновился");
    }

    @Test
    @DisplayName("Получение всех задач, эпиков и подзадач")
    void getAllEntities_shouldReturnCorrectLists() {
        taskManager.addTask(task);
        taskManager.addSubtask(subtask);

        List<Task> tasks = taskManager.getAllTasks();
        List<Epic> epics = taskManager.getAllEpics();
        List<Subtask> subtasks = taskManager.getAllSubtasks();

        assertEquals(1, tasks.size(), "Неверное количество обычных задач");
        assertEquals(1, epics.size(), "Неверное количество эпиков");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач");
    }
}