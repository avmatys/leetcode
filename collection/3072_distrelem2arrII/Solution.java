class Solution {

    private static class BitTree {
        int[] vals;
        int n;
        
        BitTree(int n) {
            this.vals = new int[n];
            this.n = n;
        }

        public void add(int idx, int delta) {
            for(;idx < n; idx |= (idx + 1)) 
                vals[idx] += delta;
        }

        public int count(int idx) {
            int result = 0;
            for(;idx >= 0; idx = (idx & (idx + 1)) - 1)
                result += vals[idx];
            return result;
        }
    }
    
    public int[] resultArray(int[] nums) {
        int n = nums.length;
        int[] copy = Arrays.copyOf(nums, n);
        Arrays.sort(copy);
        Map<Integer,Integer> ranks = new HashMap<>();
        int rank = 1;
        for (int i = 0; i < n; i++) {
            while (i + 1 < n && copy[i] == copy[i + 1]) ++i;
            ranks.put(copy[i], rank++);
        }
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
        BitTree t1 = new BitTree(n + 1);
        BitTree t2 = new BitTree(n + 1);
        arr1.add(nums[0]);
        t1.add(ranks.get(nums[0]), 1);
        arr2.add(nums[1]); 
        t2.add(ranks.get(nums[1]), 1);
        for (int i = 2; i < n; i++) {
            int r = ranks.get(nums[i]);
            int cnt1 = arr1.size() - t1.count(r);
            int cnt2 = arr2.size() - t2.count(r);
            if (cnt1 > cnt2) {
                arr1.add(nums[i]);
                t1.add(r, 1);
            } else if (cnt2 > cnt1) {
                arr2.add(nums[i]);
                t2.add(r, 1);
            } else {
                if (arr1.size() <= arr2.size()) {
                    arr1.add(nums[i]);
                    t1.add(r, 1);
                } else {
                    arr2.add(nums[i]);
                    t2.add(r, 1);
                }
            }
        }
        int[] result = new int[n];
        int idx = 0;
        for(int x: arr1) result[idx++] = x;
        for(int x: arr2) result[idx++] = x;
        return result;
    }
}
