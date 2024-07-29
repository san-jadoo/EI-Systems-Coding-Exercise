import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    public static void main(String[] args) {
        // Setup file handler for logging
        try {
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Failed to setup logger file handler.");
            e.printStackTrace();
        }

        ScheduleManager manager = ScheduleManager.getInstance();
        manager.addObserver(new TaskConflictObserver());
        Scanner scanner = new Scanner(System.in);

        try {
            int choice;
            do {
                displayMenu();
                choice = getUserChoice(scanner);
                handleUserChoice(choice, manager, scanner);
            } while (choice != 8);  // Exit condition based on menu choice

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            logger.severe("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void displayMenu() {
        System.out.println("1. Add Task\n2. Remove Task\n3. View Tasks\n4. Edit Task\n5. Mark Task as Completed\n6. View Tasks by Priority\n7. View Deleted Tasks\n8. Exit");
    }

    private static int getUserChoice(Scanner scanner) {
        System.out.print("Choice : ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            logger.warning("Invalid input. User provided non-numeric input.");
            scanner.nextLine();  // Clear the buffer
            choice = -1;  // Invalid choice
        }
        return choice;
    }

    private static void handleUserChoice(int choice, ScheduleManager manager, Scanner scanner) {
        switch (choice) {
            case 1:
                addTask(manager, scanner);
                break;
            case 2:
                removeTask(manager, scanner);
                break;
            case 3:
                viewTasks(manager);
                break;
            case 4:
                editTask(manager, scanner);
                break;
            case 5:
                markTaskAsCompleted(manager, scanner);
                break;
            case 6:
                viewTasksByPriority(manager, scanner);
                break;
            case 7:
                viewDeletedTasks(manager);
                break;
            case 8:
                logger.info("Exiting application.");
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                logger.warning("User entered an invalid choice: " + choice);
        }
    }

    private static void addTask(ScheduleManager manager, Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        LocalTime startTime = readTime(scanner, "Enter start time (H:mm): ");
        LocalTime endTime = readTime(scanner, "Enter end time (H:mm): ");
        System.out.print("Enter priority: ");
        int priority = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Task task = TaskFactory.createTask(description, startTime, endTime, priority);
        manager.addTask(task);
    }

    private static void removeTask(ScheduleManager manager, Scanner scanner) {
        System.out.print("Enter description of task to remove: ");
        String descToRemove = scanner.nextLine();
        manager.removeTask(descToRemove);
    }

    private static void viewTasks(ScheduleManager manager) {
        List<Task> tasks = manager.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            logger.info("No tasks scheduled for the day.");
        } else {
            for (Task t : tasks) {
                System.out.println(t.getStartTime() + " - " + t.getEndTime() + ": " + t.getDescription() + " [" + t.getPriority() + "]");
            }
            logger.info("Displayed tasks sorted by start time.");
        }
    }

    private static void editTask(ScheduleManager manager, Scanner scanner) {
        System.out.print("Enter description of task to edit: ");
        String oldDescription = scanner.nextLine();
        System.out.print("Enter new description: ");
        String newDescription = scanner.nextLine();
        LocalTime newStartTime = readTime(scanner, "Enter new start time (H:mm): ");
        LocalTime newEndTime = readTime(scanner, "Enter new end time (H:mm): ");
        System.out.print("Enter new priority: ");
        int newPriority = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        manager.editTask(oldDescription, newDescription, newStartTime, newEndTime, newPriority);
        logger.info("Edited task from: " + oldDescription + " to: " + newDescription);
    }

    private static void markTaskAsCompleted(ScheduleManager manager, Scanner scanner) {
        System.out.print("Enter description of task to mark as completed: ");
        String descToComplete = scanner.nextLine();
        manager.markTaskAsCompleted(descToComplete);
    }

    private static void viewTasksByPriority(ScheduleManager manager, Scanner scanner) {
        System.out.print("Enter priority level to view: ");
        int viewPriority = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        List<Task> tasks = manager.getTasksByPriority(viewPriority);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found with priority level " + viewPriority + ".");
            logger.info("No tasks found with priority level " + viewPriority + ".");
        } else {
            for (Task t : tasks) {
                System.out.println(t.getStartTime() + " - " + t.getEndTime() + ": " + t.getDescription() + " [" + t.getPriority() + "]");
            }
            logger.info("Displayed tasks with priority level " + viewPriority + ".");
        }
    }

    private static void viewDeletedTasks(ScheduleManager manager) {
        List<Task> deletedTasks = manager.getDeletedTasks();
        if (deletedTasks.isEmpty()) {
            System.out.println("No tasks have been removed.");
            logger.info("No tasks have been removed.");
        } else {
            for (Task t : deletedTasks) {
                System.out.println(t.getStartTime() + " - " + t.getEndTime() + ": " + t.getDescription() + " [" + t.getPriority() + "]");
            }
            logger.info("Displayed deleted tasks.");
        }
    }

    private static LocalTime readTime(Scanner scanner, String prompt) {
        LocalTime time = null;
        while (time == null) {
            System.out.print(prompt);
            String timeInput = scanner.nextLine();
            try {
                time = LocalTime.parse(timeInput, TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use H:mm (e.g., 5:00).");
                logger.warning("Invalid time format entered: " + timeInput);
            }
        }
        return time;
    }
}
