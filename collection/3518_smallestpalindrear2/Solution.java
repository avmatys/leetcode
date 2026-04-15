class Solution {

    private static int maxk = 1000001;

    private int multinomial(int[] freq) {
        int total = 0;
        for (int x: freq) total += x;
        long result = 1L;
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                result = result * binom(total, freq[i]); 
                if (result >= maxk) return maxk;
                total -= freq[i];
            }
        }
        return (int) result;
    }

    private int binom(int n, int k) {
        if (k > n) return 0;
        if (k > n - k) k = n - k;
        long result = 1L;
        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) / i;
            if (result >= maxk) return maxk;
        }
        return (int) result;
    }
  
    public String smallestPalindrome(String s, int k) {
        char[] chs = s.toCharArray();
        int[] freq = new int[26];
        for (int i = 0; i < chs.length; i++) {
            freq[chs[i] - 'a']++;
        }
        String middle = "";
        // Divide freq for the first half only
        int total = 0;
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] % 2 == 1) middle = String.valueOf((char)(i + 'a'));
            freq[i] /= 2;
            total += freq[i];
        }   
        // Try to build a sequence if possible    
        if (multinomial(freq) < k) return ""; // Not able to build a K th permutation
        // Here go char by char
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < total; i++) {
            for (int j = 0; j < 26; j++) {
                if (freq[j] > 0) {
                    freq[j]--; 
                    int curr = multinomial(freq);
                    if (curr >= k) {
                        sb.append((char)(j + 'a'));
                        break;
                    }
                    k -= curr;
                    freq[j]++;
                }
            }
        } 
        return sb.toString() + middle + sb.reverse().toString();
    }
}
