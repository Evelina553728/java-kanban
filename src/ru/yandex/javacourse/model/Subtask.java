package ru.yandex.javacourse.model;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return String.format(
                "Subtask{id=%d, name='%s', description='%s', status=%s, epicId=%d, type=%s}",
                id, name, description, status, epicId, getType()
        );
    }
}