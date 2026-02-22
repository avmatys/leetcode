class Solution {

    private static int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private static int MOD = 1_000_000_000 + 7;

    private int[][][] dp;
    private int m;
    private int n;

    private int solve(int i, int j, int remain) {
        // Check if we are not out of the bounds
        if (i < 0 || j < 0 || i >= m || j >= n) {
            return 1;
        }
        // Check if the current state was already calculated
        if (dp[i][j][remain] != -1) {
            return dp[i][j][remain];
        }
        // Accumulate result
        int result = 0;
        if (remain > 0) {
            for (int[] d: dirs) {
                result = (result + solve(i + d[0], j + d[1], remain - 1)) % MOD;
            }
        }
        dp[i][j][remain] = result;
        return dp[i][j][remain];
    }

    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        this.m = m;
        this.n = n;
        this.dp = new int[m][n][maxMove + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++){
                Arrays.fill(dp[i][j], -1);
            }
        }
        return this.solve(startRow, startColumn, maxMove);
    }
}
