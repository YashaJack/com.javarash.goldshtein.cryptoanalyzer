import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BruteForce {
    public static void bruteForceDecrypt(String inputFilePath, String outputFilePath, String exampleFilePath) throws IOException {
        Map<String, Integer> dictionary = buildDictionary(exampleFilePath); // Создаем словарь слов из примера текста
        int bestMatchCount = 0; // Хранит количество совпадений для лучшего варианта
        String bestDecryption = ""; // Текст лучшего варианта

        // Читаем исходный файл полностью
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String originalText = reader.lines().collect(Collectors.joining("\n")); // Собираем файл в одну строку

            for (int key = 0; key < Cipher.ALPHABET_SIZE; key++) { // Перебираем все возможные ключи
                String decryptedText = Cipher.shiftText(originalText, -key); // Дешифруем текст
                int matchCount = evaluateMatch(decryptedText, dictionary); // Оцениваем совпадения с образцом

                if (matchCount > bestMatchCount) { // Если текущее совпадение лучше, обновляем результат
                    bestMatchCount = matchCount;
                    bestDecryption = decryptedText;
                }
            }
        }

        // Записываем лучший результат в файл
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            writer.write("Наилучший ключ: " + bestMatchCount + "\n"); // Сохраняем лучший ключ
            writer.write(bestDecryption); // Сохраняем лучший вариант текста
        }
    }

    // Метод для построения словаря из файла-примера
    private static Map<String, Integer> buildDictionary(String exampleFilePath) throws IOException {
        Map<String, Integer> dictionary = new HashMap<>();
        if (exampleFilePath != null && Files.exists(Paths.get(exampleFilePath))) { // Проверяем наличие файла
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(exampleFilePath))) {
                reader.lines().forEach(line -> {
                    for (String word : line.toLowerCase().split("\\W+")) { // Разбиваем строку на слова и добавляем в словарь
                        dictionary.put(word, dictionary.getOrDefault(word, 0) + 1);
                    }
                });
            }
        }
        return dictionary;
    }

    // Метод для оценки совпадений с текстом-примером
    private static int evaluateMatch(String text, Map<String, Integer> dictionary) {
        int matchCount = 0;
        for (String word : text.toLowerCase().split("\\W+")) { // Проверяем каждое слово на совпадение
            if (dictionary.containsKey(word)) {
                matchCount++;
            }
        }
        return matchCount; // Возвращаем количество совпадений
    }
}
