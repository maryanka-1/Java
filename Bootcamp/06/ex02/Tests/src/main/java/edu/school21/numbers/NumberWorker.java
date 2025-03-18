package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number < 3) {
            throw new IllegalArgumentException("Number must be greater than 2");
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        int result = 0;
        int num = Math.abs(number);
        while (num > 0) {
            result += num % 10;
            num /= 10;
        }
        return result;
    }
}
