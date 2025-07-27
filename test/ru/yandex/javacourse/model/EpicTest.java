package ru.yandex.javacourse.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    @DisplayName("Статус эпика без подзадач должен быть NEW")
    void epicWithoutSubtasks_ShouldBeNew() {
        Epic epic = new Epic("Эпик без подзадач", "Описание");
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    @DisplayName("Статус эпика с подзадачами со статусом NEW должен быть NEW")
    void epicWithAllNewSubtasks_ShouldBeNew() {
        Epic epic = new Epic("Эпик", "Описание");
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);

        Subtask sub1 = new Subtask("Подзадача 1", "desc", Status.NEW, epic.getId());
        Subtask sub2 = new Subtask("Подзадача 2", "desc", Status.NEW, epic.getId());

        epic.updateStatus(List.of(sub1, sub2));

        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    @DisplayName("Статус эпика с подзадачами со статусом DONE должен быть DONE")
    void epicWithAllDoneSubtasks_ShouldBeDone() {
        Epic epic = new Epic("Эпик", "Описание");
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);

        Subtask sub1 = new Subtask("Подзадача 1", "desc", Status.DONE, epic.getId());
        Subtask sub2 = new Subtask("Подзадача 2", "desc", Status.DONE, epic.getId());

        epic.updateStatus(List.of(sub1, sub2));

        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    @DisplayName("Статус эпика с подзадачами NEW и DONE должен быть IN_PROGRESS")
    void epicWithMixedSubtasks_ShouldBeInProgress() {
        Epic epic = new Epic("Эпик", "Описание");
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);

        Subtask sub1 = new Subtask("Подзадача 1", "desc", Status.NEW, epic.getId());
        Subtask sub2 = new Subtask("Подзадача 2", "desc", Status.DONE, epic.getId());

        epic.updateStatus(List.of(sub1, sub2));

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    @DisplayName("Статус эпика с подзадачами IN_PROGRESS должен быть IN_PROGRESS")
    void epicWithAllInProgressSubtasks_ShouldBeInProgress() {
        Epic epic = new Epic("Эпик", "Описание");
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);

        Subtask sub1 = new Subtask("Подзадача 1", "desc", Status.IN_PROGRESS, epic.getId());
        Subtask sub2 = new Subtask("Подзадача 2", "desc", Status.IN_PROGRESS, epic.getId());

        epic.updateStatus(List.of(sub1, sub2));

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }
}