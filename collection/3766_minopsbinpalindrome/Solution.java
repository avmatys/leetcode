class Solution {

    private static String bin(int n) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append((char)(n % 2 + '0'));
            n /= 2;
        } while(n > 0);
        return sb.reverse().toString();
    }

    private static boolean isBinPalindrome(int x) {
        String sbin = bin(x);
        int l = 0;
        int r = sbin.length() - 1;
        while (l < r) {
            if (sbin.charAt(l) != sbin.charAt(r)) 
                return false;
            l++;
            r--;
        }
        return true;
    }

    private static int[] generateBinPalindromes(int n) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            if ((i == 0 || i % 2 == 1) && isBinPalindrome(i)) 
                result.add(i);
        }
        return result.stream().mapToInt(i->i).toArray();
    }

    private static int binSearch(int[] palindromes, int x) {
        int l = 0;
        int r = palindromes.length - 1;
        while (l < r) {
            int m = (l + r) / 2;
            if (palindromes[m] < x) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    public int[] minOperations(int[] nums) {
        int[] bp = generateBinPalindromes(5000);
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int idx = binSearch(bp, nums[i]);
            if (bp[idx] != nums[i]) {
                result[i] = Math.abs(bp[idx] - nums[i]);
                if (idx > 0) {
                    result[i] = Math.min(result[i], Math.abs(nums[i] - bp[idx - 1]));
                }
            }
        }
        return result;
    }
}
