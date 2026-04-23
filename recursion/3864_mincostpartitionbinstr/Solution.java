class Solution {

    private int fcost;
    private int ecost;

    private long solve(int[] prefix, int l, int r) {
        int len = r - l + 1;
        int x = prefix[r + 1] - prefix[l];
        long result = x == 0 ? 1L * fcost : 1L * len * ecost * x;
        if (len % 2 == 0) {
            int m = l + (len / 2);
            result = Math.min(result, solve(prefix, l, m - 1) + solve(prefix, m, r));
        }
        return result;
    }
    
    public long minCost(String s, int encCost, int flatCost) {
        fcost = flatCost;
        ecost = encCost;
        int n = s.length();
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++)
            prefix[i + 1] = prefix[i] + (s.charAt(i) - '0');
        return solve(prefix, 0, n - 1);
    }
}
