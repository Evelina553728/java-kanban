package ru.yandex.javacourse;

import ru.yandex.javacourse.model.*;
import ru.yandex.javacourse.service.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task(
                "Собрать коробки",
                "Переезд",
                Status.NEW,
                Duration.ofHours(2),
                LocalDateTime.now()
        );

        Task task2 = new Task(
                "Упаковать кошку",
                "Переезд",
                Status.NEW,
                Duration.ofHours(1),
                LocalDateTime.now().plusHours(3)
        );

        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic(
                "Переезд",
                "Сделать всё"
        );
        manager.createEpic(epic1);

        Subtask sub1 = new Subtask(
                "Сказать слова прощания",
                "Речь",
                Status.NEW,
                epic1.getId(),
                Duration.ofMinutes(30),
                LocalDateTime.now().plusHours(5)
        );

        Subtask sub2 = new Subtask(
                "Прощальный взгляд",
                "Жалко же",
                Status.NEW,
                epic1.getId(),
                Duration.ofMinutes(45),
                LocalDateTime.now().plusHours(6)
        );

        manager.createSubtask(sub1);
        manager.createSubtask(sub2);

        System.out.println("Все задачи: " + manager.getTasks());
        System.out.println("Все эпики: " + manager.getEpics());
        System.out.println("Все подзадачи эпика: " + manager.getSubtasksOfEpic(epic1.getId()));

        sub1.setStatus(Status.DONE);
        manager.updateSubtask(sub1);

        Epic updatedEpic = manager.getEpicById(epic1.getId());
        System.out.println("Обновлённый статус эпика: " + updatedEpic.getStatus());
    }
}