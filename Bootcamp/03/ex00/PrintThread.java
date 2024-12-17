public class PrintThread extends Thread {
    private String word;
    private int count;

    public PrintThread(String word, int count) {
        this.count = count;
        this.word = word;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println(word);
        }
    }
}
