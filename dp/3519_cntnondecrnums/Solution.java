class Solution {

    private static class CharList {
        private static final int INITIAL_SIZE = 10;
        private char[] vals;
        private int i, n;

        CharList() {
            n = INITIAL_SIZE;
            i = 0;
            vals = new char[n];
        }

        CharList(char[] arr) {
            n = arr.length;
            i = arr.length;
            vals = Arrays.copyOf(arr, n);
        }

        public void add(char c) {
            if (i == n) {
                n <<= 1;
                vals = Arrays.copyOf(vals, n);
            }
            vals[i++] = c;
        }

        public CharList reverse() {
            int l = 0, r = i - 1;
            while (l < r) {
                char tmp = vals[l];
                vals[l] = vals[r];
                vals[r] = tmp;
                l++; r--;
            }
            return this;
        }

        public char[] toArray() {
            return Arrays.copyOf(vals, i); 
        }

        public int size() {
            return i;
        }

    }

    private static class Entry {
        private int idx, prev;
        private boolean tight;

        public Entry(int idx, int prev, boolean tight) {
            this.idx = idx;
            this.prev = prev;
            this.tight = tight;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) return false;
            Entry that = (Entry) obj;
            return idx == that.idx && prev == that.prev && tight == that.tight;
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(idx);
            result = 31 * result + Integer.hashCode(prev);
            result = 31 * result + Boolean.hashCode(tight);
            return result;
        }
        
    }

    private char[] toBase(long x, int b) {
        CharList ch = new CharList();
        while(x > 0) {
            char c = (char)((x % b) + '0');
            ch.add(c);
            x /= b;
        }
        return ch.reverse().toArray();
    }

    private char[] toBase(String x, int b) {
        char[] curr = x.toCharArray();
        if (x.equals("0")) 
            return curr;
        CharList result = new CharList();
        while(curr.length > 0) {
            CharList next = new CharList();
            long remainder = 0L;
            for (char c: curr) {
                remainder = 10 * remainder + (c - '0');
                long digit = remainder / b;
                remainder = remainder % b;
                if (digit > 0 || next.size() > 0) {
                    next.add((char)(digit + '0'));
                }
            }
            curr = next.toArray();
            result.add((char)(remainder + '0'));
        }
        return result.reverse().toArray();
    }

    private Map<Entry, Integer> dp;
    private static int MOD = 1_000_000_007;
    private int base;

    private int solve(char[] digits, int idx, int prev, boolean tight) {
        if (idx == digits.length) 
            return prev == -1 ? 0 : 1;
        Entry key = new Entry(idx, prev, tight);
        if (dp.containsKey(key)) 
            return dp.get(key);
        long result = 0;
        if (prev == -1) // Not started yet 
            result = (result + solve(digits, idx + 1, prev, false)) % MOD;
        int l = prev == -1 ? 1 : prev;
        int r = tight ? digits[idx] - '0' : base - 1; 
        for (int d = l; d <= r; d++) 
            result = (result + solve(digits, idx + 1, d, tight && d == r)) % MOD;
        dp.put(key, (int) result);
        return (int)result;
    }

    private boolean isNonDecreasing(char[] digits) {
        char prev = '0';
        for (char c: digits) {
            if (c < prev) return false;
            prev = c;
        }
        return true;
    }

    public int countNumbers(String l, String r, int b) {
        char[] chsLeft = toBase(l, b);
        char[] chsRight = toBase(r, b);
        base = b;
        dp = new HashMap<>();
        int cntLeft = solve(chsLeft, 0, -1, true);
        dp = new HashMap<>();
        int cntRight = solve(chsRight, 0, -1, true);
        if (isNonDecreasing(chsLeft)) ++cntRight;
        return (cntRight + MOD - cntLeft) % MOD;
    }
}
