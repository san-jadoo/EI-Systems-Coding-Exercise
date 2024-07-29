public abstract class Document {
    public abstract void open();
    public abstract void close();
    public abstract void save();
}

class WordDocument extends Document {
    @Override
    public void open() {
        System.out.println("Opening Word Document");
    }

    @Override
    public void close() {
        System.out.println("Closing Word Document");
    }

    @Override
    public void save() {
        System.out.println("Saving Word Document");
    }
}

class ExcelDocument extends Document {
    @Override
    public void open() {
        System.out.println("Opening Excel Document");
    }

    @Override
    public void close() {
        System.out.println("Closing Excel Document");
    }

    @Override
    public void save() {
        System.out.println("Saving Excel Document");
    }
}
