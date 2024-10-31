import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Выберите режим работы:");
            System.out.println("1 - Шифрование");
            System.out.println("2 - Дешифрование");
            System.out.println("3 - Взлом методом brute force");
            int mode = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер

            System.out.println("Введите путь к входному файлу:");
            String inputFilePath = scanner.nextLine();

            if (!Files.exists(Paths.get(inputFilePath))) {
                System.err.println("Файл не найден: " + inputFilePath);
                return;
            }

            String outputFilePath;
            switch (mode) {
                case 1 -> { // Шифрование
                    System.out.println("Введите путь к выходному файлу:");
                    outputFilePath = scanner.nextLine();
                    System.out.println("Введите ключ для шифрования:");
                    int key = scanner.nextInt();
                    FileWorker.encryptFile(inputFilePath, outputFilePath, key);
                    System.out.println("Файл успешно зашифрован!");
                }
                case 2 -> { // Дешифрование
                    System.out.println("Введите путь к выходному файлу:");
                    outputFilePath = scanner.nextLine();
                    System.out.println("Введите ключ для дешифрования:");
                    int key = scanner.nextInt();
                    FileWorker.decryptFile(inputFilePath, outputFilePath, key);
                    System.out.println("Файл успешно расшифрован!");
                }
                case 3 -> { // Взлом
                    System.out.println("Введите путь к выходному файлу:");
                    outputFilePath = scanner.nextLine();
                    System.out.println("Введите путь к файлу-примеру (или оставьте пустым):");
                    String exampleFilePath = scanner.nextLine();
                    if (exampleFilePath.isEmpty()) {
                        exampleFilePath = null;
                    }
                    BruteForce.bruteForceDecrypt(inputFilePath, outputFilePath, exampleFilePath);
                    System.out.println("Взлом завершен. Результат сохранен в файл!");
                }
                default -> System.err.println("Неверный режим.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Ошибка: Введено некорректное значение.");
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
