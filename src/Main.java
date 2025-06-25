public class Main {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        Task task1 = new Task("Собрать коробки", "Переезд", 0, Status.NEW);
        Task task2 = new Task("Упаковать кошку", "Переезд", 0, Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Переезд", "Сделать всё");
        manager.addEpic(epic1);

        Subtask sub1 = new Subtask("Сказать слова прощания", "Речь", 0, Status.NEW, epic1.getId());
        Subtask sub2 = new Subtask("Прощальный взгляд", "Жалко же", 0, Status.NEW, epic1.getId());
        manager.addSubtask(sub1);
        manager.addSubtask(sub2);

        System.out.println("Все задачи: " + manager.getAllTasks());
        System.out.println("Все эпики: " + manager.getAllEpics());
        System.out.println("Все подзадачи эпика: " + manager.getSubtasksOfEpic(epic1.getId()));

        sub1.setStatus(Status.DONE);
        manager.updateSubtask(sub1);
    }
}