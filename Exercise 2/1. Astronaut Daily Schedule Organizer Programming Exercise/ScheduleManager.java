import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks;
    private List<Task> deletedTasks;
    private List<TaskObserver> observers;
    private static final Logger logger = Logger.getLogger(ScheduleManager.class.getName());

    private ScheduleManager() {
        tasks = new ArrayList<>();
        deletedTasks = new ArrayList<>();
        observers = new ArrayList<>();
        setupLogger();
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    private void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("schedule_manager.log", true); // Append mode
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            System.err.println("Failed to set up logger: " + e.getMessage());
        }
    }

    public void addTask(Task task) {
        if (validateTask(task)) {
            tasks.add(task);
            notifyObservers(task);
            logger.info("Task added: " + task.getDescription());
        } else {
            logger.warning("Task conflict detected: " + task.getDescription());
        }
    }

    public void removeTask(String description) {
        Task task = findTaskByDescription(description);
        if (task != null) {
            tasks.remove(task);
            deletedTasks.add(task);
            logger.info("Task removed: " + task.getDescription());
        } else {
            logger.warning("Task not found: " + description);
        }
    }

    public void editTask(String oldDescription, String newDescription, LocalTime startTime, LocalTime endTime, int priority) {
        Task task = findTaskByDescription(oldDescription);
        if (task != null) {
            task.setDescription(newDescription);
            task.setStartTime(startTime);
            task.setEndTime(endTime);
            task.setPriority(priority);
            logger.info("Task edited: " + oldDescription + " to " + newDescription);
        } else {
            logger.warning("Task not found: " + oldDescription);
        }
    }

    public void markTaskAsCompleted(String description) {
        Task task = findTaskByDescription(description);
        if (task != null) {
            task.setCompleted(true);
            logger.info("Task marked as completed: " + description);
        } else {
            logger.warning("Task not found: " + description);
        }
    }

    public List<Task> getTasks() {
        return tasks.stream()
                .sorted((t1, t2) -> t1.getStartTime().compareTo(t2.getStartTime()))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksByPriority(int priority) {
        return tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .sorted((t1, t2) -> t1.getStartTime().compareTo(t2.getStartTime()))
                .collect(Collectors.toList());
    }

    public List<Task> getDeletedTasks() {
        return deletedTasks;
    }

    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(Task task) {
        for (TaskObserver observer : observers) {
            observer.update(task);
        }
    }

    private boolean validateTask(Task newTask) {
        for (Task task : tasks) {
            if (task.getStartTime().isBefore(newTask.getEndTime()) && newTask.getStartTime().isBefore(task.getEndTime())) {
                return false;
            }
        }
        return true;
    }

    private Task findTaskByDescription(String description) {
        return tasks.stream()
                .filter(task -> task.getDescription().equals(description))
                .findFirst()
                .orElse(null);
    }
}
