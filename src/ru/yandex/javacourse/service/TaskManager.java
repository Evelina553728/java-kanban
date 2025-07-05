package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.*;

import java.util.List;

public interface TaskManager {
    void addTask(Task task);
    void addEpic(Epic epic);
    void addSubtask(Subtask subtask);

    List<Task> getAllTasks();
    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);

    List<Epic> getAllEpics();
    List<Subtask> getAllSubtasks();
    List<Subtask> getSubtasksOfEpic(int epicId);

    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    void removeTask(int id);
    void removeEpic(int id);
    void removeSubtask(int id);

    void clearTasks();
    void clearEpics();
    void clearSubtasks();
}