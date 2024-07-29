public class LoggerTest {
    public static void main(String[] args) {
        // Get the single instance of Logger
        Logger logger = Logger.getInstance();

        // Log messages
        logger.log("Application started.");
        logger.log("Performing some operations.");
        logger.log("Application finished.");

        // Close the logger
        logger.close();

        System.out.println(logger);
    }
}
