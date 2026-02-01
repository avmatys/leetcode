class Solution {

    private int[] nums;
    private int[] dp;
    private int n;
    private int k;

    private int solve(int i) {
        if (i == n) return 0;
        if (dp[i] == -1) {
            int max = 0;
            for (int j = i; j < Math.min(n, i + k); j++) {
                max = Math.max(max, nums[j]);
                dp[i] = Math.max(dp[i], (j - i + 1) * max + solve(j + 1));
            }
        }
        return dp[i];
    }

    public int maxSumAfterPartitioning(int[] arr, int k) {
        this.nums = arr;
        this.k = k;
        this.n = arr.length;
        this.dp = new int[n];
        Arrays.fill(dp, -1);
        return solve(0);
    }
}
