public class FileSystemTest {
    public static void main(String[] args) {
        // Create files
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");

        // Create directories
        Directory dir1 = new Directory("dir1");
        Directory dir2 = new Directory("dir2");

        // Add files to directories
        dir1.addComponent(file1);
        dir2.addComponent(file2);

        // Create a root directory and add subdirectories
        Directory root = new Directory("root");
        root.addComponent(dir1);
        root.addComponent(dir2);

        // Display the file system hierarchy
        root.display("");
    }
}
