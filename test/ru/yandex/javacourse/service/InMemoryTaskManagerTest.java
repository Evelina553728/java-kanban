package ru.yandex.javacourse.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private static final String TASK_TITLE = "Задача";
    private static final String TASK_DESCRIPTION = "Описание";
    private static final String EPIC_TITLE = "Эпик";
    private static final String EPIC_DESCRIPTION = "Описание эпика";
    private static final String SUBTASK_TITLE = "Подзадача";
    private static final String SUBTASK_DESCRIPTION = "Описание подзадачи";

    @Test
    @DisplayName("addTask() должен сохранять задачу в менеджер")
    void addTask_givenValidTask_shouldStoreTask() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task(TASK_TITLE, TASK_DESCRIPTION, Status.NEW);

        manager.addTask(task);
        List<Task> allTasks = manager.getAllTasks();

        assertEquals(1, allTasks.size());
        assertEquals(TASK_TITLE, allTasks.get(0).getTitle());
    }

    @Test
    @DisplayName("addEpic() должен сохранять эпик")
    void addEpic_givenValidEpic_shouldStoreEpic() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_TITLE, EPIC_DESCRIPTION);

        manager.addEpic(epic);
        Epic storedEpic = manager.getEpic(epic.getId());

        assertNotNull(storedEpic);
        assertEquals(EPIC_TITLE, storedEpic.getTitle());
    }

    @Test
    @DisplayName("addSubtask() должен добавлять подзадачу к эпику")
    void addSubtask_givenValidSubtask_shouldLinkToEpic() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_TITLE, EPIC_DESCRIPTION);
        manager.addEpic(epic);

        Subtask subtask = new Subtask(SUBTASK_TITLE, SUBTASK_DESCRIPTION, Status.NEW, epic.getId());
        manager.addSubtask(subtask);

        List<Subtask> subtasks = manager.getSubtasksOfEpic(epic.getId());

        assertEquals(1, subtasks.size());
        assertEquals(SUBTASK_TITLE, subtasks.get(0).getTitle());
    }

    @Test
    @DisplayName("updateSubtask() должен менять статус подзадачи и обновлять статус эпика")
    void updateSubtask_whenStatusChanged_shouldUpdateEpicStatus() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_TITLE, EPIC_DESCRIPTION);
        manager.addEpic(epic);

        Subtask subtask = new Subtask(SUBTASK_TITLE, SUBTASK_DESCRIPTION, Status.NEW, epic.getId());
        manager.addSubtask(subtask);

        subtask.setStatus(Status.DONE);
        manager.updateSubtask(subtask);

        Epic updatedEpic = manager.getEpic(epic.getId());
        assertEquals(Status.IN_PROGRESS, updatedEpic.getStatus());
    }

    @Test
    @DisplayName("removeTask() должен удалять задачу")
    void removeTask_givenExistingTask_shouldRemoveIt() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task(TASK_TITLE, TASK_DESCRIPTION, Status.NEW);
        manager.addTask(task);

        manager.removeTask(task.getId());

        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Test
    @DisplayName("clearTasks() должен очищать все задачи")
    void clearTasks_whenCalled_shouldEmptyTaskList() {
        TaskManager manager = new InMemoryTaskManager();
        manager.addTask(new Task(TASK_TITLE, TASK_DESCRIPTION, Status.NEW));
        manager.addTask(new Task(TASK_TITLE, TASK_DESCRIPTION, Status.NEW));

        manager.clearTasks();

        assertEquals(0, manager.getAllTasks().size());
    }
}