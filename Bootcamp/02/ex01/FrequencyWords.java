import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FrequencyWords {
    private final String file1;
    private final String file2;
    private final Set<String> dictionary;
    private final List<String> content1;
    private final List<String> content2;

    public FrequencyWords(String file1, String file2) {
        this.file1 = file1;
        this.file2 = file2;
        this.content1 = new ArrayList<>();
        this.content2 = new ArrayList<>();
        this.dictionary = new HashSet<>();
    }

    public void run() throws IOException {
        int[] vector1 = null;
        int[] vector2 = null;
        try {
            fillContent(file1, content1);
            fillContent(file2, content2);
            createDictionary();
            vector1 = fillVector(content1);
            vector2 = fillVector(content2);

            double similarity = similarity(numerator(vector1, vector2), denominator(vector1, vector2));
            System.out.printf("Similary - %.2f\n", Math.floor(similarity * 100) / 100);
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    public void fillContent(String file, List<String> content) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) break;
                for (String word : line.split(" ")) {
                    content.add(word);
                    dictionary.add(word);
                }
            }
        } catch (Exception e) {
            throw new IOException(e);

        }
    }

    public void createDictionary() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))){
            for (String word : dictionary) {
                writer.write(word);
                writer.write(" ");
            }
            writer.newLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int[] fillVector(List<String> content) {
        int[] vector = new int[dictionary.size()];
        int i = 0;
        for (String dict : dictionary) {
            for (String word : content) {
                if (word.equals(dict)) vector[i]++;
            }
            i++;
        }
        return vector;
    }

    public double numerator(int[] vector1, int[] vector2) {
        double numerator = 0;
        for (int i = 0; i < vector1.length; i++) {
            numerator += vector1[i]*vector2[i];
        }
        return numerator;
    }

    public double denominator(int[] vector1, int[] vector2) {
        double numVector1 = 0;
        double numVector2 =0;
        for (int i = 0; i < vector1.length; i++) {
            numVector1 += vector1[i]*vector1[i];
            numVector2 += vector2[i]*vector2[i];
        }
        return Math.sqrt(numVector1)*Math.sqrt(numVector2);
    }

    public double similarity(double numerator, double denominator) {
        return numerator / denominator;
    }
}
