class Solution {

    private static int MOD = 1_000_000_007;

    private int[][][] memo;

    private int solve(int remain, int A, int L) {
        if (remain == 0) {
            return 1; // No more options to add
        }
        if (memo[remain][A][L] != -1) {
            return memo[remain][A][L];
        }
        int result = 0;
        // Case 1 - add P
        result = (result + solve(remain - 1, A, 0)) % MOD;
        // Case 2 - try to add A
        if (A == 0) {
            result = (result + solve(remain - 1, 1, 0)) % MOD;
        }
        // Case 3 - try to add L
        if (L < 2) {
            result = (result + solve(remain - 1, A, L + 1)) % MOD;
        }
        memo[remain][A][L] = result;
        return result;
    }


    public int checkRecord(int n) {
        this.memo = new int[n + 1][2][3];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(memo[i][0], -1);
            Arrays.fill(memo[i][1], -1);
        }
        return solve(n, 0, 0);
    }
}
