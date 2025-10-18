package ru.yandex.javacourse.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, Status.NEW, Duration.ZERO, null);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public void clearSubtasks() {
        subtaskIds.clear();
    }

    public void removeSubtaskId(int id) {
        subtaskIds.remove((Integer) id);
    }

    public void updateStatus(List<Subtask> subtasks) {
        if (subtasks.isEmpty()) {
            this.status = Status.NEW;
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask sub : subtasks) {
            if (sub.getStatus() != Status.NEW) {
                allNew = false;
            }
            if (sub.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            this.status = Status.NEW;
        } else if (allDone) {
            this.status = Status.DONE;
        } else {
            this.status = Status.IN_PROGRESS;
        }
    }

    public void updateTime(List<Subtask> subtasks) {
        if (subtasks.isEmpty()) {
            this.startTime = null;
            this.duration = Duration.ZERO;
            return;
        }

        LocalDateTime start = null;
        LocalDateTime end = null;
        Duration totalDuration = Duration.ZERO;

        for (Subtask sub : subtasks) {
            if (sub.getStartTime() != null) {
                if (start == null || sub.getStartTime().isBefore(start)) {
                    start = sub.getStartTime();
                }
                LocalDateTime subEnd = sub.getEndTime();
                if (end == null || (subEnd != null && subEnd.isAfter(end))) {
                    end = subEnd;
                }
            }
            if (sub.getDuration() != null) {
                totalDuration = totalDuration.plus(sub.getDuration());
            }
        }

        this.startTime = start;
        this.duration = totalDuration;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtaskIds=" + subtaskIds +
                '}';
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }
}