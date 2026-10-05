import java.util.Random;

public class Task10 {
    private static final int SIZE = 1000000;

    public static void main(String[] args) {
        final int sum = new Random().ints(SIZE, 0, 100)
            .parallel()
            .sum();
        System.out.println("Sum of all elements: " + sum);
    }
}
