import java.util.HashMap;
import java.util.Map;

public class Cipher {
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};


    public static final int ALPHABET_SIZE = ALPHABET.length;
    private static final Map<Character, Integer> ALPHABET_MAP = new HashMap<>();

    static {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            ALPHABET_MAP.put(ALPHABET[i], i);
        }
    }

    public static String shiftText(String text, int shift) {
        StringBuilder result = new StringBuilder(); // Используем StringBuilder для эффективного построения строки
        for (char character : text.toLowerCase().toCharArray()) { // Преобразуем каждый символ в нижний регистр
            if (ALPHABET_MAP.containsKey(character)) { // Проверка, есть ли символ в алфавите
                int originalPosition = ALPHABET_MAP.get(character); // Получаем текущую позицию символа
                int shiftedPosition = (originalPosition + shift + ALPHABET_SIZE) % ALPHABET_SIZE; // Расчет новой позиции
                result.append(ALPHABET[shiftedPosition]); // Добавляем зашифрованный символ к результату
            } else {
                result.append(character); // Если символа нет в алфавите, оставляем его неизменным
            }
        }
        return result.toString();
    }

    public static int validateKey(int key) {
        return (key % ALPHABET_SIZE + ALPHABET_SIZE) % ALPHABET_SIZE;
    }
}
