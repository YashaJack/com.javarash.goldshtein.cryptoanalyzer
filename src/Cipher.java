import java.io.IOException;

import java.util.List;
import java.util.stream.Stream;

public class Cipher {
    public static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
            'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
    };
    private static final int ALPHABET_SIZE = ALPHABET.length;

    public static void encrypt(String inputFilePath, String outputFilePath, int key) {
        processFile(inputFilePath, outputFilePath, key, true);
    }

    public static void decrypt(String inputFilePath, String outputFilePath, int key) {
        processFile(inputFilePath, outputFilePath, key, false);
    }

    private static void processFile(String inputFilePath, String outputFilePath, int key, boolean encrypt) {
        try (Stream<String> lines = FileWorker.readFile(inputFilePath)) {
            // Для отрицательного ключа сделаем корректный модуль
            int effectiveKey = ((key % ALPHABET_SIZE) + ALPHABET_SIZE) % ALPHABET_SIZE;

            // Если мы расшифровываем, инвертируем сдвиг
            effectiveKey = encrypt ? effectiveKey : -effectiveKey;

            // Собираем обработанные строки в список
            List<String> processedLines = processLines(lines, effectiveKey);
            FileWorker.writeFile(outputFilePath, processedLines.stream());
            System.out.println((encrypt ? "Шифрование" : "Расшифровка") + " завершено.");
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    private static List<String> processLines(Stream<String> lines, int key) {
        List<String> processedLines = new java.util.ArrayList<>();
        lines.forEach(line -> {
            processedLines.add(processLine(line, key, false));
        });
        return processedLines;
    }

    static String processLine(String line, int key, boolean b) {
        StringBuilder processedLine = new StringBuilder();
        for (char c : line.toCharArray()) {
            int index = findCharIndex(c);
            if (index != -1) {
                int shiftedIndex = (index + key + ALPHABET_SIZE) % ALPHABET_SIZE;
                processedLine.append(ALPHABET[shiftedIndex]);
            } else {
                processedLine.append(c);  // Пропускаем символ, если его нет в алфавите
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