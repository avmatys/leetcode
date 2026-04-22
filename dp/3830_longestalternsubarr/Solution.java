class Solution {
    public int longestAlternating(int[] nums) {
        int n = nums.length;
        int[][] lr = new int[n][2];
        int[][] rl = new int[n][2];
        for (int i = 0; i < n; i++) {
            lr[i][0] = lr[i][1] = 1;
            rl[i][0] = rl[i][1] = 1;
        }
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                lr[i][1] = lr[i - 1][0] + 1;
            } else if (nums[i] < nums[i - 1]) { 
                lr[i][0] = lr[i - 1][1] + 1;
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                rl[i][1] = rl[i + 1][0] + 1;
            } else if (nums[i] < nums[i + 1]) { 
                rl[i][0] = rl[i + 1][1] + 1;
            }
        }
        int result = 0;
        for (int i = 0; i < n; i++){ 
            result = Math.max(result, Math.max(lr[i][0], lr[i][1]));
        }
        if (result == n) return n;
        for(int i = 1; i < n - 1; i++) {
            if (nums[i - 1] < nums[i + 1]) {
                result = Math.max(result, lr[i - 1][0] + rl[i + 1][1]);
            } else if (nums[i - 1] > nums[i + 1]) {
                result = Math.max(result, lr[i - 1][1] + rl[i + 1][0]);
            }
        }
        return result;
    }
}
