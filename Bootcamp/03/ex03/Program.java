import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Program {
    public static void main(String[] args) throws InterruptedException, IOException {
        InputStream file = new FileInputStream("files_urls.txt");
        Path path = Paths.get("files_urls.txt").toAbsolutePath().getParent();

        Scanner scanner = new Scanner(file);
        List<String> links = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(" ");
            links.add(split[1]);
        }

        CountDownLatch latch = new CountDownLatch(links.size());
        int param = parse(args);
        ExecutorService executorService = Executors.newFixedThreadPool(param);
        for (int i = 0; i < links.size(); i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String[] numberThread = Thread.currentThread().getName().split("-");
                        System.out.println("Thread-" + numberThread[3] + " start download file number " + (index + 1));
                        load(links.get(index), path, index + 1);

                        System.out.println("Thread-" + numberThread[3] + " finish download file number " + (index + 1));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        latch.countDown(); // Уменьшаем счетчик
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown(); // Завершаем ExecutorService
        System.out.println("All downloads completed.");
    }

    public static void load(String link, Path path, int i) throws IOException {
        String[] split = link.split("\\.");
        String name = i + "." + split[split.length - 1];
        Path pathFile = path.resolve(name);
        URL url = new URL(link);
        try {
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "chrome-extension");

            try (InputStream in = connection.getInputStream()) {
                Files.copy(in, pathFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int parse(String[] args) {
        int a = 0;
        if (args.length != 1) {
            System.out.println("java Program --threadsCount=3");
        } else {
            String[] tmp = args[0].split("=");
            try {
                a = Integer.parseInt(tmp[1]);
            } catch (java.lang.Exception e) {
                System.out.println("It's not a integer");
                System.exit(-1);
            }
        }
        return a;
    }
}
