class Solution {

    Map<Integer, Boolean> memo;
    int n; 

    private boolean play(int sum, int used) {
        if (sum <= 0)
            return false;
        if (memo.containsKey(used)) 
            return memo.get(used);
        boolean result = false;
        for (int bit = 1; bit <= n; bit++) {
            if ((used & (1 << bit)) > 0) {
                continue; // already used bit
            }
            if (bit >= sum || !play(sum - bit, used | (1 << bit))) {
                result = true;
                break;
            }
        }
        memo.put(used, result);
        return result;
    }

    public boolean canIWin(int n, int total) {
        if (n >= total)
            return true;
        if ((1 + n) / 2 * n < total) 
            return false;
        this.n = n;
        this.memo = new HashMap<>();
        return play(total, 0);
    }
}
