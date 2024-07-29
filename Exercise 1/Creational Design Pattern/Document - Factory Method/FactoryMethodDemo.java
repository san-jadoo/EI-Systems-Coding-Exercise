import java.util.Scanner;

public class FactoryMethodDemo {

    public static void main(String[] args) {
        DocumentFactory documentFactory = new ConcreteDocumentFactory();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter document type (Word/Excel): ");
        String type = scanner.nextLine();

        try {
            Document document = documentFactory.createDocument(type);
            document.open();
            document.save();
            document.close();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
