package ru.yandex.javacourse.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    private final File file = new File("test.csv");

    @Override
    protected FileBackedTaskManager createTaskManager() {
        return new FileBackedTaskManager(file);
    }

    @Test
    @DisplayName("Сохранение и загрузка задачи из файла")
    void shouldSaveAndLoadTask() {
        FileBackedTaskManager manager = createTaskManager();
        Task task = new Task("Task1", "desc", Status.NEW,
                Duration.ofMinutes(30), LocalDateTime.now());
        manager.createTask(task);

        FileBackedTaskManager loaded = FileBackedTaskManager.loadFromFile(file);
        List<Task> tasks = loaded.getTasks();

        assertEquals(1, tasks.size());
        assertEquals("Task1", tasks.get(0).getName());
    }
}