class Solution {

    private static class Fenwick {
        private int[] tree;
        private int n;
        public Fenwick(int n) {
            this.tree = new int[n];
            this.n = n;
        }
        public void update(int idx, int delta) {
            for(;idx < n; idx = (idx | (idx + 1))) {
                tree[idx] += delta;
            }
        }
        public int query(int idx) {
            int result = 0;
            for (;idx >= 0; idx = (idx & (idx + 1)) - 1) {
                result += tree[idx];
            }
            return result;
        }
    }


    public int countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        int rsum = n; // Middle for Fenwick
        Fenwick tree = new Fenwick(2 * n + 2);
        tree.update(n, 1); // Initial state
        int result = 0;
        for(int i = 0; i < n; i++) {
            if (nums[i] == target) {
                rsum += 1;
            } else {
                rsum -= 1;
            }
            result += tree.query(rsum - 1);
            tree.update(rsum, 1);
        }
        return result;
    }
}
