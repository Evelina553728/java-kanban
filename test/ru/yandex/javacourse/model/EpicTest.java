package ru.yandex.javacourse.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    @DisplayName("Эпик без подзадач должен иметь статус NEW")
    void epicWithoutSubtasksShouldBeNew() {
        Epic epic = new Epic("Epic1", "desc");
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    @DisplayName("Эпик с NEW подзадачами должен быть NEW")
    void epicWithAllNewSubtasksShouldBeNew() {
        Epic epic = new Epic("Epic1", "desc");
        Subtask sub1 = new Subtask("Sub1", "desc", Status.NEW, 1,
                Duration.ofMinutes(10), LocalDateTime.now());
        Subtask sub2 = new Subtask("Sub2", "desc", Status.NEW, 1,
                Duration.ofMinutes(15), LocalDateTime.now().plusMinutes(20));

        epic.addSubtaskId(sub1.getId());
        epic.addSubtaskId(sub2.getId());

        epic.updateStatus(java.util.List.of(sub1, sub2));
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    @DisplayName("Эпик с DONE подзадачами должен быть DONE")
    void epicWithAllDoneSubtasksShouldBeDone() {
        Epic epic = new Epic("Epic1", "desc");
        Subtask sub1 = new Subtask("Sub1", "desc", Status.DONE, 1,
                Duration.ofMinutes(10), LocalDateTime.now());
        Subtask sub2 = new Subtask("Sub2", "desc", Status.DONE, 1,
                Duration.ofMinutes(15), LocalDateTime.now().plusMinutes(20));

        epic.addSubtaskId(sub1.getId());
        epic.addSubtaskId(sub2.getId());

        epic.updateStatus(java.util.List.of(sub1, sub2));
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    @DisplayName("Эпик с NEW и DONE подзадачами должен быть IN_PROGRESS")
    void epicWithMixedSubtasksShouldBeInProgress() {
        Epic epic = new Epic("Epic1", "desc");
        Subtask sub1 = new Subtask("Sub1", "desc", Status.NEW, 1,
                Duration.ofMinutes(10), LocalDateTime.now());
        Subtask sub2 = new Subtask("Sub2", "desc", Status.DONE, 1,
                Duration.ofMinutes(15), LocalDateTime.now().plusMinutes(20));

        epic.addSubtaskId(sub1.getId());
        epic.addSubtaskId(sub2.getId());

        epic.updateStatus(java.util.List.of(sub1, sub2));
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    @DisplayName("Эпик с IN_PROGRESS подзадачами должен быть IN_PROGRESS")
    void epicWithInProgressSubtasksShouldBeInProgress() {
        Epic epic = new Epic("Epic1", "desc");
        Subtask sub1 = new Subtask("Sub1", "desc", Status.IN_PROGRESS, 1,
                Duration.ofMinutes(10), LocalDateTime.now());

        epic.addSubtaskId(sub1.getId());

        epic.updateStatus(java.util.List.of(sub1));
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }
}