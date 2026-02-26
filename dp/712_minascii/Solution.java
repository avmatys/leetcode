class Solution {

    private char[] s1;
    private char[] s2;
    private int[][] memo;

    // Try to solve a subtask one by one
    private int solve(int i, int j) {
        // Not sure with base case
        if (i >= s1.length) {
            // Remove the rest of the chars
            int total = 0;
            for (int k = j; k < s2.length; k++) {
                total += s2[k];
            }
            return total;
        }
        if (j >= s2.length) {
            // Remove the rest of the chars
            int total = 0;
            for (int k = i; k < s1.length; k++) {
                total += s1[k];
            }
            return total;
        }
        // Check if the same state was already calculated
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        // This means that we total cost 0 + cost of i + 1, j + 1
        int result = Integer.MAX_VALUE;
        if (s1[i] == s2[j]) {
            // Case 1 - symbols are equal
            result = solve(i + 1, j + 1);
        } else {
             // Case 2 - symbols are not equal
            result = Math.min(s1[i] + solve(i + 1, j), s2[j] + solve(i, j + 1));
        }
        memo[i][j] = result;
        return result;
    }

    public int minimumDeleteSum(String s1, String s2) {
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();
        this.memo = new int[this.s1.length][this.s2.length];
        for (int i = 0; i < this.s1.length; i++) {
            Arrays.fill(this.memo[i], -1);
        }
        return solve(0, 0);
    }
}
