class Solution {

    private int binSearch(int[][] pairs, int val, int r) {
        int l = 0;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (pairs[m][0] <= val) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return r;
    }

    public int maxCapacity(int[] costs, int[] capacity, int budget) {
        int n = costs.length;
        int[][] pairs = new int[n][2];
        for (int i = 0; i < n; i++) {
            pairs[i] = new int[] {costs[i], capacity[i]};
        }
        Arrays.sort(pairs, (a, b) -> Integer.compare(a[0], b[0]));
        int result = 0;
        // Choose 1 item
        for(int i = 0; i < n; i++) {
            if (pairs[i][0] < budget && pairs[i][1] > result) {
                result = pairs[i][1];
            }
        }
        // Chose 2 items
        int[] pref = new int[n];
        if (pairs[0][0] < budget) {
            pref[0] = pairs[0][1];
        }
        for (int i = 1; i < n; i++) {
            pref[i] = pref[i-1];
            if (pairs[i][0] < budget && pairs[i][1] > pref[i]) {
                pref[i] = pairs[i][1];
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (pairs[0][0] + pairs[i][0] >= budget) continue;
            // Here we can try to find the best value till j-th element
            int j = binSearch(pairs,  budget - pairs[i][0] - 1, i) - 1;
            if (j < 0) continue;
            result = Math.max(result, pairs[i][1] + pref[j]);
        }

        return result;

    }
}
