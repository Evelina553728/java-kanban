package ru.yandex.javacourse.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Task;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для класса Managers")
class ManagersTest {

    @Test
    @DisplayName("getDefault() должен возвращать InMemoryTaskManager")
    void getDefault_shouldReturnInstanceOfInMemoryTaskManager() {
        TaskManager manager = Managers.getDefault();

        assertNotNull(manager, "Менеджер задач не должен быть null");
        assertInstanceOf(InMemoryTaskManager.class, manager,
                "Ожидался экземпляр InMemoryTaskManager");
    }

    @Test
    @DisplayName("getDefaultHistory() должен возвращать InMemoryHistoryManager")
    void getDefaultHistory_shouldReturnInstanceOfInMemoryHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(historyManager, "Менеджер истории не должен быть null");
        assertInstanceOf(InMemoryHistoryManager.class, historyManager,
                "Ожидался экземпляр InMemoryHistoryManager");
    }
}