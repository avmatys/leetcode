class Solution {

    private int solve(String s) {
        int n = s.length();
        int[] prev = new int[2 * n + 1];
        Arrays.fill(prev, Integer.MAX_VALUE);
        prev[n] = -1; // -n ... 0 ... +n
                      //  0 ... n ... 2n
        boolean[] has_zero = new boolean[n + 1];
        boolean[] has_ones = new boolean[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            has_zero[i] = s.charAt(i) == '0' || has_zero[i + 1];
            has_ones[i] = s.charAt(i) == '1' || has_ones[i + 1];
        }
        int result = 0;
        int[] freq = new int[2];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - '0']++;
            int diff = n + freq[1] - freq[0];
            if (prev[diff] != Integer.MAX_VALUE) {
                result = Math.max(result, i - prev[diff]);
            } else {
                prev[diff] = i;
            }
            if (diff - 2 > 0 && prev[diff - 2] != Integer.MAX_VALUE && has_zero[i + 1]) {
                result = Math.max(result, i - prev[diff - 2]);
            }
            if (diff + 2 < prev.length && prev[diff + 2] != Integer.MAX_VALUE && has_ones[i + 1]) {
                result = Math.max(result, i - prev[diff + 2]);
            }
        }
        return result;
    }

    public int longestBalanced(String s) {
        return Math.max(solve(s), solve(new StringBuilder(s).reverse().toString()));
    }
}
