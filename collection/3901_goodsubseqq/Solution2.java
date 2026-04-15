class Solution {

    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private class GcdTree {

        private int[] vals;
        private int n;
        private int p;

        GcdTree(int[] nums, int n, int p) {
            this.vals = new int[4 * n];
            this.n = n;
            this.p = p;
            build(nums, 1, 0, n - 1);
        }

        private void build(int[] nums, int node, int l, int r) {
            if (l == r) {
                vals[node] = nums[l] % p == 0 ? nums[l] / p : 0;
                return;
            }
            int m = (l + r) / 2;
            build(nums, 2 * node, l, m);
            build(nums, 2 * node + 1, m + 1, r);
            vals[node] = gcd(vals[2 * node], vals[2 * node + 1]);
        }

        public void update(int idx, int val) {
            update(1, 0, n - 1, idx, val);
        }

        private void update(int node, int l, int r, int idx, int val) {
            if (l == r){
                vals[node] = val % p == 0 ? val / p : 0;
                return;
            }
            int m = (l + r) / 2;
            if (idx <= m) update(2 * node, l, m, idx, val);
            else update(2 * node + 1, m + 1, r, idx, val);
            vals[node] = gcd(vals[2 * node], vals[2 * node + 1]);
        }

        public int query(int start, int end) {
            return query(1, 0, n - 1, start, end);
        }

        private int query(int node, int l, int r, int start, int end) {
            if (end < l || start > r ) return 0;
            if (start <= l && r <= end) return vals[node]; // means start..l..r..end
            int m = (l + r) / 2;
            int v1 = query(2 * node, l, m, start, end);
            int v2 = query(2 * node + 1, m + 1, r, start, end);
            return gcd(v1, v2);
        }

        public int get(int node) {
            if (node > 0 && node < vals.length) return vals[node];
            return 0;
        }

    }

    public int countGoodSubseq(int[] nums, int p, int[][] queries) {
        int n = nums.length;
        GcdTree tree = new GcdTree(nums, n, p);
        int pcnt = 0;
        for(int x: nums) {
            if (x % p == 0) ++pcnt;
        }
        int result = 0;
        for(int[] q: queries) {
            if (nums[q[0]] % p == 0) 
                --pcnt;
            tree.update(q[0], q[1]);
            nums[q[0]] = q[1];
            if (nums[q[0]] % p == 0)
                ++pcnt;
            if (pcnt > 0 && tree.get(1) == 1) {
                if (pcnt < n) {
                    ++result;
                } else {
                    for (int i = 0; i < n; i++) {
                        int v1 = tree.query(0, i - 1);
                        int v2 = tree.query(i + 1, n - 1);
                        if (gcd(v1, v2) == 1) {
                            ++result;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

}
