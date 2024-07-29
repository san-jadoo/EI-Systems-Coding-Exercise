import java.time.LocalTime;

public class TaskFactory {
    public static Task createTask(String description, LocalTime startTime, LocalTime endTime, int priority) {
        return new Task(description, startTime, endTime, priority);
    }
}
