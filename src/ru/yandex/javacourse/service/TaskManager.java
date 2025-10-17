package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.*;

import java.util.List;

public interface TaskManager {

    List<Task> getTasks();
    List<Epic> getEpics();
    List<Subtask> getSubtasks();

    Task getTaskById(int id);
    Epic getEpicById(int id);
    Subtask getSubtaskById(int id);

    Task createTask(Task task);
    Epic createEpic(Epic epic);
    Subtask createSubtask(Subtask subtask);

    void updateTask(Task task);
    void updateEpic(Epic epic);   // ⚡️ обязательно public
    void updateSubtask(Subtask subtask);

    void deleteTasks();
    void deleteEpics();
    void deleteSubtasks();

    void deleteTaskById(int id);
    void deleteEpicById(int id);
    void deleteSubtaskById(int id);

    List<Subtask> getSubtasksOfEpic(int epicId);
    List<Task> getHistory();
}