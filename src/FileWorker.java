import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class FileWorker {


    public static Stream<String> readFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        if (Files.notExists(path)) {
            throw new IOException("Файл не найден: " + filePath);
        }
        return Files.lines(path);
    }


    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.CREATE)) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("Ошибка при записи в файл: " + filePath, e);
        }
    }


    public static void writeFile(String filePath, Stream<String> content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.CREATE)) {
            content.forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка записи в файл: " + e.getMessage());
                }
            });
        }
    }


    public static boolean fileExists(String filePath) {
        return Files.exists(Path.of(filePath));
    }
}