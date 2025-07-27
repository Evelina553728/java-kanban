package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Task;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.service.HistoryManager;
import ru.yandex.javacourse.service.InMemoryHistoryManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для InMemoryHistoryManager")
class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        task2 = new Task("Задача 2", "Описание 2", Status.IN_PROGRESS);
        task3 = new Task("Задача 3", "Описание 3", Status.DONE);

        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
    }

    @Test
    @DisplayName("История должна быть пустой при инициализации")
    void getHistory_initialState_shouldBeEmpty() {
        List<Task> history = historyManager.getHistory();

        assertNotNull(history);
        assertTrue(history.isEmpty(), "История должна быть пустой");
    }

    @Test
    @DisplayName("Добавление задач должно отражаться в истории")
    void add_addTasksToHistory_shouldAppearInHistory() {
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    @DisplayName("Повторное добавление задачи не должно дублировать её")
    void add_duplicateTask_shouldNotBeDuplicated() {
        historyManager.add(task1);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task1, history.get(0));
    }

    @Test
    @DisplayName("Удаление задачи из истории должно работать корректно")
    void remove_taskById_shouldRemoveFromHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertFalse(history.contains(task2));
    }

    @Test
    @DisplayName("Удаление первой, средней и последней задач")
    void remove_firstMiddleLastTasks_shouldHandleAllCorrectly() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(1);
        historyManager.remove(2);
        historyManager.remove(3);

        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "Все задачи должны быть удалены");
    }
}