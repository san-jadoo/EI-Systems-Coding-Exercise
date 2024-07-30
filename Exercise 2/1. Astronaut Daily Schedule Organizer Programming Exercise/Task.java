import java.time.LocalTime;

public class Task {
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private int priority;
    private boolean completed;

    public Task(String description, LocalTime startTime, LocalTime endTime, int priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.completed = false;
    }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
