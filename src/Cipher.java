import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.io.IOException;
import java.util.stream.Stream;

public class Cipher {
    public static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
    };
    public static final int ALPHABET_SIZE = ALPHABET.length;

    public static void encrypt(String inputFilePath, String outputFilePath, int key) {
        processFile(inputFilePath, outputFilePath, key, true);
    }

    public static void decrypt(String inputFilePath, String outputFilePath, int key) {
        processFile(inputFilePath, outputFilePath, key, false);
    }

    private static void processFile(String inputFilePath, String outputFilePath, int key, boolean encrypt) {
        try (Stream<String> lines = FileWorker.readFile(inputFilePath)) {
            Stream<String> processedLines = lines.map(line -> processLine(line, key, encrypt));
            FileWorker.writeFile(outputFilePath, processedLines);
            System.out.println((encrypt ? "Шифрование" : "Расшифровка") + " завершено.");
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    static String processLine(String line, int key, boolean encrypt) {
        StringBuilder processedLine = new StringBuilder();
        for (char c : line.toCharArray()) {
            int index = findCharIndex(c);
            if (index != -1) {
                int shiftedIndex = encrypt ?
                        (index + key) % ALPHABET_SIZE :
                        (index - key + ALPHABET_SIZE) % ALPHABET_SIZE;
                processedLine.append(ALPHABET[shiftedIndex]);
            }
        }
        return processedLine.toString();
    }

    private static int findCharIndex(char c) {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (ALPHABET[i] == c) {
                return i;
            }
        }
        return -1;
    }
}