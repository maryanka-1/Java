import java.util.HashMap;

public class Calculate {
    private int sum;
    private final int arraySize;
    private final int countOfThreads;
    private int sumOfThreads;
    private int[] array;

    public Calculate(int arraySize, int countOfThreads) {
        this.arraySize = arraySize;
        this.countOfThreads = countOfThreads;
    }

    public void run() throws InterruptedException {
        getArray(this.arraySize);
        System.out.println("Sum: " + getSum());
        sumOfTreads();

    }

    private int getSum() {
        setSum(this.array);
        return sum;
    }

    private void setSum(int[] array) {
        int sum = 0;
        for (int j : array) {
            sum += j;
        }
        this.sum = sum;
    }

    private void getArray(int arraySize) {
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = (int) (Math.random() * 10);
        }
        this.array = array;
    }

    private void sumOfTreads() throws InterruptedException {
        int period = arraySize / countOfThreads;
        Thread[] threads = new Thread[countOfThreads];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < countOfThreads; i++) {
            int fin = i;
            map.put(i + 1, fin);
            threads[i] = new Thread(() -> {
                int start = fin * period;
                int end = start + period;
                if (fin + 1 == countOfThreads) {
                    end = arraySize;
                }
                int sumTemp = 0;
                for (int j = start; j < end; j++) {
                    sumTemp += array[j];
                }
                map.put(fin + 1, sumTemp);
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        int start = 0;

        for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
            int fin = entry.getKey();
            int end = start + period - 1;
            if (fin == countOfThreads) {
                end = arraySize;
            }
            System.out.println("Thread " + entry.getKey() + ": from " + start + " to " + end + " sum is " + entry.getValue());
            start += period;
            end += period;
            sumOfThreads += entry.getValue();
        }
        System.out.println("Sum by threads: " + sumOfThreads);
    }
}
