import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileWorker {


    public static void encryptFile(String inputFilePath, String outputFilePath, int key) {
        key = Cipher.validateKey(key);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(Cipher.shiftText(line, key));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при шифровании файла: " + e.getMessage());
        }
    }

    public static void decryptFile(String inputFilePath, String outputFilePath, int key) {
        key = Cipher.validateKey(-key);
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(Cipher.shiftText(line, key));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при дешифровании файла: " + e.getMessage());
        }
    }
}