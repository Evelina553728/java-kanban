package ru.yandex.javacourse.service;

import org.junit.jupiter.api.*;
import ru.yandex.javacourse.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для FileBackedTaskManager")
class FileBackedTaskManagerTest {

    private File tempFile;
    private FileBackedTaskManager fileManager;

    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("tasks", ".csv");
        fileManager = new FileBackedTaskManager(tempFile);

        task = new Task("Обычная задача", "Описание задачи", Status.NEW);
        task.setId(1);

        epic = new Epic("Эпик задача", "Описание эпика");
        epic.setId(2);

        subtask = new Subtask("Подзадача", "Описание подзадачи", Status.NEW, epic.getId());
        subtask.setId(3);
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    @DisplayName("Сохранение и загрузка задач из файла должно работать корректно")
    void saveAndLoad_shouldRestoreTasksFromFile() {
        fileManager.addTask(task);
        fileManager.addEpic(epic);
        fileManager.addSubtask(subtask);

        FileBackedTaskManager loaded = FileBackedTaskManager.loadFromFile(tempFile);

        List<Task> tasks = loaded.getAllTasks();
        List<Epic> epics = loaded.getAllEpics();
        List<Subtask> subtasks = loaded.getAllSubtasks();

        assertEquals(1, tasks.size());
        assertEquals(1, epics.size());
        assertEquals(1, subtasks.size());

        assertEquals(task.getName(), tasks.get(0).getName());
        assertEquals(epic.getName(), epics.get(0).getName());
        assertEquals(subtask.getName(), subtasks.get(0).getName());
    }

    @Test
    @DisplayName("Удаление задачи должно сохраняться в файле")
    void removeTask_shouldBeSavedInFile() throws IOException {
        fileManager.addTask(task);
        fileManager.removeTaskById(task.getId());

        String content = Files.readString(tempFile.toPath());
        assertFalse(content.contains(task.getName()), "Файл не должен содержать удалённую задачу");
    }

    @Test
    @DisplayName("Удаление эпика должно удалять и его подзадачи из файла")
    void removeEpic_shouldRemoveSubtasksInFile() throws IOException {
        fileManager.addEpic(epic);
        fileManager.addSubtask(subtask);

        fileManager.removeEpicById(epic.getId());

        String content = Files.readString(tempFile.toPath());
        assertFalse(content.contains(epic.getName()), "Файл не должен содержать удалённый эпик");
        assertFalse(content.contains(subtask.getName()), "Файл не должен содержать подзадачу удалённого эпика");
    }
}