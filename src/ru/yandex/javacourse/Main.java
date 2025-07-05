package ru.yandex.javacourse;

import ru.yandex.javacourse.model.*;
import ru.yandex.javacourse.service.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager(); // Используем конкретную реализацию интерфейса

        Task task1 = new Task("Собрать коробки", "Переезд", Status.NEW);
        Task task2 = new Task("Упаковать кошку", "Переезд", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Переезд", "Сделать всё");
        manager.addEpic(epic1);

        Subtask sub1 = new Subtask("Сказать слова прощания", "Речь", Status.NEW, epic1.getId());
        Subtask sub2 = new Subtask("Прощальный взгляд", "Жалко же", Status.NEW, epic1.getId());
        manager.addSubtask(sub1);
        manager.addSubtask(sub2);

        System.out.println("Все задачи: " + manager.getAllTasks());
        System.out.println("Все эпики: " + manager.getAllEpics());
        System.out.println("Все подзадачи эпика: " + manager.getSubtasksOfEpic(epic1.getId()));

        sub1.setStatus(Status.DONE);
        manager.updateSubtask(sub1);

        Epic updatedEpic = manager.getEpic(epic1.getId());
        System.out.println("Обновлённый статус эпика: " + updatedEpic.getStatus());
    }
}