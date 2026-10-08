import java.util.Arrays;

public class Task11 {
    public static long sumImperative(int[] nums) {
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += i;
        }
        return sum;
    }

    public static long sumFunctional(int[] nums) {
        return Arrays.stream(nums).sum();
    }
}
