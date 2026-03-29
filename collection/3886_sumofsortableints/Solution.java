class Solution {
    
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int result = 0;
        for (int i = 1; i < n + 1; i++) {
            if (n % i != 0) continue;
            int k = n / i;
            boolean allValid = true;
            int prevMax = Integer.MIN_VALUE;
            for (int j = 0; j < n && allValid; j += k) {
                int currMax = nums[j];
                int currMin = nums[j];
                int drops = 0;
                for (int len = 0; len < k && drops < 2; len++) {
                    currMin = Math.min(currMin, nums[j + len]);
                    currMax = Math.max(currMax, nums[j + len]);
                    if (nums[j + len] > nums[j + ((len + 1)% k)]) {
                        drops++;
                    }
                }
                if (drops > 1 || currMin < prevMax) {
                    allValid = false;
                } else {
                    prevMax = currMax;
                }
            }
            if (allValid) {
                result += k;
            }
        }
        return result;
    }
}
