import javax.management.monitor.Monitor;

public class PrintThread extends Thread {
    private String word;
    private int count;
    private static final Object lock = new Object();
    private static boolean isTurn = true;

    public PrintThread(String word, int count) {
        this.count = count;
        this.word = word;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            synchronized (lock) {
                while ((word.equals("Egg") && !isTurn) || (word.equals("Heg")) && isTurn) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(word);
                isTurn = !isTurn;
                lock.notifyAll();
            }
        }
    }
}
