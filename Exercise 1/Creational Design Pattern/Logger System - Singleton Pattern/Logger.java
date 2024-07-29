import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private PrintWriter writer;
    private static final String LOG_FILE = "application.log";

    // Private constructor to prevent instantiation
    private Logger() {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true), true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize logger");
        }
    }

    // Method to get the single instance of Logger
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Method to log messages
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.println(timestamp + " - " + message);
    }

    // Close the writer
    public void close() {
        writer.close();
    }
}
