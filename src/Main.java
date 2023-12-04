import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Ввод пользователем названия и ссылки на песню
            System.out.println("Введите название песни: ");
            String name = scanner.nextLine();

            System.out.println("Введите ссылку на песню: ");
            String url = scanner.nextLine();

            // Загрузка файла
            boolean success = downloadSong(url, name);

            // Вывод результата
            if (success) {
                System.out.println("Файлзагружен: " + name + ".mp3");
            } else {
                System.out.println("Не удалось загрузить файл.");
            }
        }
    }

    private static boolean downloadSong(String url, String name) {
        try {
            // Создаем URL объект для передачи ввода пользователя
            URL website = new URL(url);
            // Создаем канал для чтения байтов из URL
            try (ReadableByteChannel rbc = Channels.newChannel(website.openStream())) {
                // FileOutputStream для записи байтов в файл
                try (FileOutputStream fos = new FileOutputStream(name + ".mp3")) {
                    //байты из канала в файл
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    return true;
                } catch (IOException e) {
                    System.err.println("Ошибка при сохранении файла: " + e.getMessage());
                }
            } catch (IOException e) {
                System.err.println("Ошибка при открытии URL: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
        return false;
    }
}
