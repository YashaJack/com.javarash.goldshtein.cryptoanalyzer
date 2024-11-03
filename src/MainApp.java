import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите режим работы:");
        System.out.println("1. Шифрование");
        System.out.println("2. Расшифровка");
        System.out.println("3. Brute-force");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Введите путь к файлу с исходным текстом: ");
                String inputFilePath = scanner.nextLine();
                System.out.print("Введите путь для сохранения зашифрованного текста: ");
                String outputFilePath = scanner.nextLine();
                System.out.print("Введите ключ (сдвиг): ");
                int key = scanner.nextInt();

                if (FileWorker.fileExists(inputFilePath)) {
                    Cipher.encrypt(inputFilePath, outputFilePath, key);
                } else {
                    System.err.println("Файл не найден: " + inputFilePath);
                }
            }
            case 2 -> {
                System.out.print("Введите путь к зашифрованному файлу: ");
                String inputFilePath = scanner.nextLine();
                System.out.print("Введите путь для сохранения расшифрованного текста: ");
                String outputFilePath = scanner.nextLine();
                System.out.print("Введите ключ (сдвиг): ");
                int key = scanner.nextInt();

                if (FileWorker.fileExists(inputFilePath)) {
                    Cipher.decrypt(inputFilePath, outputFilePath, key);
                } else {
                    System.err.println("Файл не найден: " + inputFilePath);
                }
            }
            case 3 -> {
                System.out.print("Введите путь к зашифрованному файлу для brute-force: ");
                String inputFilePath = scanner.nextLine();

                if (FileWorker.fileExists(inputFilePath)) {
                    BruteForce.bruteForce(inputFilePath);
                } else {
                    System.err.println("Файл не найден: " + inputFilePath);
                }
            }
            default -> System.out.println("Некорректный выбор. Попробуйте еще раз.");
        }

        scanner.close();
    }
}