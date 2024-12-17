import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class FileManager {
    private Path directory;

    public FileManager(Path directory) {
        this.directory = directory;
    }

    public void setDirectory(Path directory) {
        this.directory = directory;
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            processing(scanner.nextLine());
            System.out.print("-> ");
        }
    }

    private void processing(String input) throws IOException {
        if (input.isEmpty()) return;
        String[] tokens = input.split(" ");
        switch (tokens[0]) {
            case "ls":
                ls(this.directory);
                break;
            case "mv":
                if (tokens.length == 3) {
                    mv(tokens[1], tokens[2]);
                } else error();
                break;
            case "cd":
                if (tokens.length == 2) {
                    cd(tokens[1]);
                } else error();
                break;
            case "pwd":
                pwd(this.directory);
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                error();
                break;
        }
    }

    private static void ls(Path directory) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path path : directoryStream) {
                System.out.println(path.getFileName() + " " + Files.size(path) / 1024 + " Kb"); // Выводим имя файла
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " not directory");
        }
    }

    private void mv(String what, String where) throws IOException {
        Path whatFile = Paths.get(what);
        Path whereFile = Paths.get(where);
        if (Files.exists(this.directory.resolve(whatFile.getFileName()))) {
            if (Files.isDirectory(whereFile)) {
                Files.move(this.directory.resolve(whatFile), whereFile.resolve(whatFile.getFileName()));
            } else if ((!whereFile.startsWith("./") ||
                    !whereFile.startsWith("../")) && Files.isDirectory(this.directory.resolve(whereFile).normalize())
//                    ||!whereFile.startsWith(".\\") ||!whereFile.startsWith("..\\")
                    ) {
                Files.move(this.directory.resolve(whatFile), this.directory.resolve(whereFile).normalize().resolve(whatFile.getFileName()));
            } else if (whereFile.startsWith("./") || whereFile.startsWith("../")
//                || whereFile.startsWith(".\\") || whereFile.startsWith("..\\")
            ) {
                Files.move(this.directory.resolve(whatFile), this.directory.resolveSibling(whereFile).normalize().resolve(whatFile.getFileName()));
            } else {

                Files.move(this.directory.resolve(whatFile), this.directory.resolve(whereFile), StandardCopyOption.REPLACE_EXISTING);
            }
        } else System.out.println(whatFile + " does not exist");

    }

    private void pwd(Path directory) throws IOException {
        System.out.println(this.directory.toAbsolutePath());
    }

    private void cd(String directory) {
        Path newDirectory = Path.of(directory);
        if (!newDirectory.isAbsolute()) {
            if (!newDirectory.startsWith("./") ||
                    !newDirectory.startsWith("../") ||
                    !newDirectory.startsWith(".\\") ||
                    !newDirectory.startsWith("..\\")) {
                setDirectory(this.directory.resolve(newDirectory).normalize());
            } else {
                setDirectory(this.directory.resolveSibling(newDirectory).normalize());
            }
            System.out.println(this.directory);
        } else if (Files.isDirectory(newDirectory)) {
            setDirectory(newDirectory);
            System.out.println(this.directory);
        } else System.out.println(directory + " is not a directory. Repeat the correct input");
    }

    private static void error() {
        System.out.println("Enter the correct command and arguments");
    }


}
