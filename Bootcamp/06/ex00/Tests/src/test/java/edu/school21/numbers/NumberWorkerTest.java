package edu.school21.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class NumberWorkerTest {
    public NumberWorker worker;

    @BeforeEach
    public void setUp() {
        this.worker = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 5, 7, 11, 13, 17, 19, 23, 29})
    public void isPrimeForPrimes(int argument){
        assertTrue(worker.isPrime(argument));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 9, 15, 21, 27, 28, 30, 33})
    public void isPrimeForNotPrimes(int argument){
        assertFalse(worker.isPrime(argument));
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -10, -1, 0, 1, 2})
    public void isPrimeForIncorrectNumbers(int argument){
        assertThrows(IllegalArgumentException.class, () -> worker.isPrime(argument));

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void digitsSumFromCvs(int number, int expectedSum) {
        assertEquals(worker.digitsSum(number), expectedSum);
    }
}
