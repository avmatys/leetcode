class Solution {

    private List<Integer> subsets;
    private int minLen;
    private int maxLen;
    private long result;
    private long diff;
    private long N;

    private void generateSubsets(int used, int currLen, int prev) {
        if (currLen > maxLen) return; 
        if (minLen <= currLen) {
            subsets.add(used);
            return;
        }
        boolean hasOdd = ((used >> 11) & 1) > 0;
        for (int i = prev + 1; i < 10; i += 1) {
            if (((used >> i) & 1) > 0 || (i % 2 != 0 && hasOdd)) continue; // Skip used and more than one odd
            int nextUsed = used | (1 << i);
            if (i % 2 != 0) nextUsed |= (1 << 11); // Mark as odd
            generateSubsets(nextUsed, currLen + i, i);
        }
    }

    private void generatePalindrome(int[] freq, char[] number, int idx, int currLen) {
        // Convert seq of chars to the long number
        if (currLen == 0){
            long x = 0L;
            for (int i = 0; i < number.length; i++) 
                x = x * 10 + (int)(number[i] - '0');
            if (x > N && x - N < diff) {
                result = x;
                diff = x - N;
            }
            return;
        }
        // Backtracking
        for (int i = 1; i < 10; i++) {
            if (freq[i] == 0) continue;
            number[idx] = number[number.length - idx - 1] = (char) (i + '0');
            freq[i] -= 2;
            generatePalindrome(freq, number, idx + 1, currLen - 2);
            freq[i] += 2;
            number[idx] = number[number.length - idx - 1] = '0';
        }
    }

    private void generateNumbers() {
        // Iterate through each generated subset and get palindromes
        for (int x: subsets){
            int[] freq = new int[10];
            int totalLen = 0;
            int oddValue = 0;
            for (int i = 1; i < 10; i++) {
                if (((x >> i) & 1) == 0) continue;
                if (i % 2 != 0) oddValue = i;
                totalLen += i;
                freq[i] = i;
            }
            char[] number = new char[totalLen];
            // Place the odd value in the middle of the number
            int currLen = totalLen;
            if (oddValue != 0) {
                number[totalLen / 2] = (char)(oddValue + '0');
                freq[oddValue]--;
                currLen--;
            }
            generatePalindrome(freq, number, 0, currLen);
        }
    }

    public long specialPalindrome(long n) {
        subsets = new ArrayList<>();
        minLen = String.valueOf(n).length();
        maxLen = minLen + 1;
        result = Long.MAX_VALUE;
        diff = Long.MAX_VALUE;
        N = n;
        generateSubsets(0, 0, 0);
        generateNumbers();
        return result;
    }
}
