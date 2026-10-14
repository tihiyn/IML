public class Task13 {
    /**
     * {P: всегда истинно}
     * {Q: max >= a && max >= b && (max == a || max == b)}
     */
    public static int max(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }

    /**
     * {P: x > Integer.MIN_VALUE}
     * {Q: abs >= 0 && (abs == x || abs == -x)}
     */
    public static int abs(int x) {
        if (x < 0) {
            return  -x;
        }
        return x;
    }

    /**
     * {P: a > Integer.MIN_VALUE && b > Integer.MIN_VALUE}
     * {Q: maxAbs >= |a| && maxAbs >= |b| && (maxAbs = |a| || maxAbs = |b|)}
     */
    public static int maxAbs(int a, int b) {
        return max(abs(a), abs(b));
    }
}
