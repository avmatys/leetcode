class Solution {

    private int[][] memo;
    private String ring;
    private String key;

    private int solve(int keyIdx, int ringState) {
        if (keyIdx == key.length()) {
            return 0;
        }
        if (memo[keyIdx][ringState] != -1) {
            return memo[keyIdx][ringState];
        }
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < ring.length(); i++) {
            if (ring.charAt(i) == key.charAt(keyIdx)) {
                int rotate = Math.abs(i - ringState);
                result = Math.min(result, Math.min(ring.length() - rotate, rotate) + 1 + solve(keyIdx + 1, i));
            }
        }
        memo[keyIdx][ringState] = result;
        return result;
    }

    public int findRotateSteps(String ring, String key) {
        this.ring = ring;
        this.key = key;
        this.memo = new int[key.length()][ring.length()];
        for (int i = 0; i < key.length(); i++) {
            Arrays.fill(memo[i], -1);
        }
        return solve(0, 0);
    }
}
