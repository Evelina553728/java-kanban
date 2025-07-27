package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.*;

import java.util.List;

public interface TaskManager {

    void addTask(Task task);

    Task getTask(int id);

    List<Task> getAllTasks();

    void updateTask(Task task);

    void removeTaskById(int id);

    void removeAllTasks();

    void addEpic(Epic epic);

    Epic getEpic(int id);

    List<Epic> getAllEpics();

    void updateEpic(Epic epic);

    void removeEpicById(int id);

    void removeAllEpics();

    void addSubtask(Subtask subtask);

    Subtask getSubtask(int id);

    List<Subtask> getAllSubtasks();

    void updateSubtask(Subtask subtask);

    void removeSubtaskById(int id);

    void removeAllSubtasks();

    List<Subtask> getSubtasksOfEpic(int epicId);

    List<Task> getHistory();
}