import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "./signature.txt";
        Map<String, String[]> hm = new HashMap<>();
        FileInputStream fileInputStream = new FileInputStream(path);
        Scanner scanner = new Scanner(fileInputStream);
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            String[] line = nextLine.replace(" ", "").split(",");
            hm.put(line[1], line);
        }
        File result = new File("./result.txt");
//        result.createNewFile();
        scanner = new Scanner(System.in);
        String pathFile;

        while(!(pathFile = scanner.nextLine()).equals("42")) {
            try {
                fileInputStream = new FileInputStream(pathFile);
                byte[] buffer = new byte[64];
                StringBuilder tmpResult = new StringBuilder();
                int bytesRead = fileInputStream.read(buffer);
                if (bytesRead != 0x0A) {
                    for (int i = 0; i < bytesRead; i++) {
                        tmpResult.append(String.format("%02X", buffer[i] & 0xFF));
                    }
                }
                Writer writer = new FileWriter("./result.txt", true);
                boolean flag = false;
                for (Map.Entry<String, String[]> entry : hm.entrySet()) {
                    String key = entry.getKey();
//                        System.out.println(tmpResult + " - " + key + " - " + pathFile);
                    if (tmpResult.toString().contains(key)) {
                        writer.write(entry.getValue()[0] + "\n");
                        flag = true;
                    }
                }
                if (flag) {
                    System.out.println("PROCESSED");
                } else {
                    System.out.println("UNDEFINED");
                }
                writer.close();

            } catch (Exception e) {
                System.out.println("No such file or directory");
            }
        }
    }
}