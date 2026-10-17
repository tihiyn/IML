public class Task14 {
    /**
     * {P: arr.length > 0}
     * {Q: result = max(arr)}
     */
    public static int findMax(int[] arr) {
        int mx = arr[0];
        // I: 1 <= i <= n && mx = max(arr[0...i-1])
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > mx) {
                mx = arr[i];
            }
        }
        return mx;
    }
}
