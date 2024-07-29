public abstract class DocumentFactory {
    public abstract Document createDocument(String type);
}

class ConcreteDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String type) {
        switch (type) {
            case "Word":
                return new WordDocument();
            case "Excel":
                return new ExcelDocument();
            default:
                throw new IllegalArgumentException("Unknown document type");
        }
    }
}
