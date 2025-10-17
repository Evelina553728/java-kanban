package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Status;
import ru.yandex.javacourse.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
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

        task1 = new Task("Задача 1", "Описание 1", Status.NEW,
                Duration.ofMinutes(10), LocalDateTime.of(2025, 10, 1, 10, 0));
        task1.setId(1);

        task2 = new Task("Задача 2", "Описание 2", Status.IN_PROGRESS,
                Duration.ofMinutes(20), LocalDateTime.of(2025, 10, 1, 11, 0));
        task2.setId(2);

        task3 = new Task("Задача 3", "Описание 3", Status.DONE,
                Duration.ofMinutes(15), LocalDateTime.of(2025, 10, 1, 12, 0));
        task3.setId(3);
    }

    @Test
    @DisplayName("История должна быть пустой при создании")
    void historyShouldBeEmptyInitially() {
        assertTrue(historyManager.getHistory().isEmpty(),
                "История новой сессии должна быть пустой");
    }

    @Test
    @DisplayName("Добавление задач в историю должно работать корректно")
    void addTaskToHistory() {
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать 2 задачи");
        assertEquals(task1, history.get(0), "Первая задача должна совпадать");
    }

    @Test
    @DisplayName("Повторное добавление задачи не должно дублировать её")
    void addingDuplicateTaskShouldNotDuplicateHistory() {
        historyManager.add(task1);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История не должна содержать дубликаты");
    }

    @Test
    @DisplayName("Удаление из истории: начало")
    void removeFromBeginning() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "После удаления должно остаться 2 задачи");
        assertFalse(history.contains(task1), "Удалённая задача не должна быть в истории");
    }

    @Test
    @DisplayName("Удаление из истории: середина")
    void removeFromMiddle() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task2.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "После удаления должно остаться 2 задачи");
        assertFalse(history.contains(task2), "Удалённая задача не должна быть в истории");
    }

    @Test
    @DisplayName("Удаление из истории: конец")
    void removeFromEnd() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task3.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "После удаления должно остаться 2 задачи");
        assertFalse(history.contains(task3), "Удалённая задача не должна быть в истории");
    }
}