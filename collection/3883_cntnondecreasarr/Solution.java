class Solution {

    private int upper(List<Integer> nums, int x) {
        int n = nums.size();
        int l = 0;
        int r = n - 1;
        int result = -1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums.get(mid) > x) {
                r = mid - 1;
            } else {
                l = mid + 1;
                result = mid;
            }
        }
        return result;
    }

    public int countArrays(int[] digitSum) {
        int mod = 1_000_000_007;
        List<Integer>[] sums = new List[41];
        for (int i = 0; i <= 31; i++)
            sums[i] = new ArrayList<>();
        // Precompute all possible sums
        for (int i = 0; i <= 5000; i++) {
            int csum = 0;
            int x = i;
            while (x > 0) {
                csum += x % 10;
                x /= 10;
            }
            sums[csum].add(i);
        }
        // Iterate and build prefixes with count
        int n = digitSum.length;
        List<Integer> currPrefix = new ArrayList<>();
        currPrefix.add(1);
        List<Integer> currNums = new ArrayList<>();
        currNums.add(0);
        for (int i = 0; i < n; i++) {
            if (digitSum[i] > 31) 
                return 0;
            List<Integer> nextNums = new ArrayList<>();
            List<Integer> nextPrefix = new ArrayList<>();
            int prevPrefix = 0;
            for (int x: sums[digitSum[i]]) {
                int idx = upper(currNums, x);
                if (idx == -1)  // Not able to build any sequnce with current
                    continue;
                int cnt = (currPrefix.get(idx) + prevPrefix) % mod;
                prevPrefix = cnt;
                nextNums.add(x);
                nextPrefix.add(cnt);
            }
            if (nextPrefix.size() == 0) 
                return 0;
            currPrefix = nextPrefix;
            currNums = nextNums;
        }
        return currPrefix.getLast();
    }
}
