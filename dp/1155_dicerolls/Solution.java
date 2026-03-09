class Solution {

    private static int MOD = 1_000_000_007;

    private int[][] memo;
    private int k;

    private int solve(int remDices, int remSum) {
        // We have no more dices
        if (remDices == 1) {
            return remSum > 0 && remSum <= k ? 1 : 0;
        }
        // Check memo
        if (memo[remDices][remSum] != -1) 
            return memo[remDices][remSum];
        // Try to roll dices
        int result = 0;
        for (int i = 1; i <= Math.min(remSum, k); i++) {
            result = (result + solve(remDices - 1, remSum - i)) % MOD;
        }
        memo[remDices][remSum] = result;
        return result;
    }


    public int numRollsToTarget(int n, int k, int target) {
        this.k = k;
        this.memo = new int[n + 1][target + 1];
        for (int i = 0; i <= n; i++)
            Arrays.fill(this.memo[i], -1);
        return solve(n, target);
    }
}
