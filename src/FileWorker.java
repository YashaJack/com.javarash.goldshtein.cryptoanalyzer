import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class FileWorker {

    // Метод для чтения содержимого файла построчно
    public static Stream<String> readFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        if (Files.notExists(path)) {
            throw new IOException("Файл не найден: " + filePath);
        }
        return Files.lines(path);
    }

    // Метод для записи строки в файл
    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.CREATE)) {
            writer.write(content);
            writer.newLine();
        }
    }

    // Метод для записи нескольких строк в файл (например, для больших файлов)
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

    // Проверка существования файла
    public static boolean fileExists(String filePath) {
        return Files.exists(Path.of(filePath));
    }
}