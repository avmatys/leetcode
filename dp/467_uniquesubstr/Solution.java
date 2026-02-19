class Solution {
    public int findSubstringInWraproundString(String s) {
        int dp[] = new int[26];
        int n = s.length();
        int curLen = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (s.charAt(i) - s.charAt(i - 1) + 26) % 26 == 1) {
                curLen++;
            } else {
                curLen = 1;
            }
            int idx = s.charAt(i) - 'a';
            dp[idx] = Math.max(dp[idx], curLen);
        }
        int result = 0;
        for (int i = 0; i < 26; i++)
            result += dp[i];
        return result;
    }
}
