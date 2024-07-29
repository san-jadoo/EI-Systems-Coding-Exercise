import java.util.logging.Logger;

public class TaskConflictObserver implements TaskObserver {
    private static final Logger logger = Logger.getLogger(TaskConflictObserver.class.getName());

    @Override
    public void update(Task task) {
        logger.info("Task conflict detected: " + task.getDescription());
    }
}
