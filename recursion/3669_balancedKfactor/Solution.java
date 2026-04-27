class Solution {

    private int diff = Integer.MAX_VALUE;
    private int[] result;
    
    private void solve(List<Integer> seq, int n, int start, int k) {
        if (k == 1) {
            int min = n, max = n;
            for (int x: seq) {
                min = Math.min(x, min);
                max = Math.max(x, max);
            }
            int cdiff = max - min;
            if (cdiff < diff) {
                diff = cdiff;
                int m = seq.size();
                result = new int[m + 1];
                for(int i = 0; i < m; i++) 
                    result[i] = seq.get(i);
                result[m] = n;
            }
            return;
        }
        for (int x = start; x <= n; x++) {
            if (n % x == 0) {
                seq.add(x);
                solve(seq, n / x, x, k - 1);
                seq.remove(seq.size() - 1);
            }
        }
    }
    
    public int[] minDifference(int n, int k) {
        solve(new ArrayList<>(), n, 1, k);
        return result;
    }
}
