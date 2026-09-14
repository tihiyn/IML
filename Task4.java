import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AverageCalculator {
    public double calculateAverage(int[] numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Массив чисел не должен быть null");
        }
        return Arrays.stream(numbers)
            .average()
            .orElseThrow();
    }

    public double calculateAverageWithError(int[] numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Массив чисел не должен быть null");
        }
        if (numbers.length == 0) {
            throw new NoSuchElementException();
        }
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return (double) sum / numbers.length;
    }
}

public class Task4 {
    private final AverageCalculator ac = new AverageCalculator();

    @Test
    void testCalculateAverage() {
        int[] numbers = new int[] {1, 2, 3, 4, 5};
        assertEquals(3.0, ac.calculateAverage(numbers));
    }

    @Test
    void testCalculateAverageIfSingle() {
        int[] numbers = new int[] {9};
        assertEquals(9.0, ac.calculateAverage(numbers));
    }

    @Test
    void testCalculateAverageWithNegative() {
        int[] numbers = new int[] {-1, 1, 2, 3};
        assertEquals(1.25, ac.calculateAverage(numbers));
    }

    @Test
    void testCalculateAverageIfEmpty() {
        int[] numbers = new int[] {};
        assertThrows(NoSuchElementException.class, () -> ac.calculateAverage(numbers));
    }

    @Test
    void testCalculateAverageIfNull() {
        assertThrows(IllegalArgumentException.class, () -> ac.calculateAverage(null));
    }

    @Test
    void testCalculateAverageWithMaxValue() {
        int[] numbers = new int[] {Integer.MAX_VALUE};
        assertEquals(2147483647.0, ac.calculateAverage(numbers));
    }

    @Test
    void testCalculateAverageWithMinValue() {
        int[] numbers = new int[] {Integer.MIN_VALUE};
        assertEquals(-2147483648.0, ac.calculateAverage(numbers));
    }

    @Test
    void testCalculateAverageWithMinAndMaxValue() {
        int[] numbers = new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE, 1};
        assertEquals(0.0, ac.calculateAverage(numbers));
    }

    @Disabled("Демонстрация ошибки с переполнением, если бы не было теста")
    @Test
    void testCalculateAverageWithErrorIfOverflow() {
        int[] numbers = new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE};
        assertEquals(2147483647.0, ac.calculateAverageWithError(numbers));
    }
}
