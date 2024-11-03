import java.io.IOException;
import java.util.stream.Stream;

public class BruteForce {
    public static void bruteForce(String inputFilePath) {
        for (int key = 0; key < Cipher.ALPHABET.length; key++) {
            String outputFileName = "brute_force_result_key_" + key + ".txt";
            try (Stream<String> lines = FileWorker.readFile(inputFilePath)) {
                int finalKey = key;
                Stream<String> decryptedLines = lines.map(line -> Cipher.processLine(line, finalKey, false));
                FileWorker.writeFile(outputFileName, decryptedLines);
            } catch (IOException e) {
                System.err.println("Ошибка при записи файла: " + e.getMessage());
            }
        }
        System.out.println("Brute-force завершен. Все возможные результаты записаны в файлы.");
    }
}