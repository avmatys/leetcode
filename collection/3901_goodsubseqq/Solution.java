class Solution {

    private Map<Integer, Integer> primeFrequency = new HashMap<>();
    private Map<Integer, Integer> primeByLenFreq = new HashMap<>();
    private int[] primes;

    private int[] factors(int n) {
        int[] result = new int[n + 1];
        for (int i = 0; i <= n; i++) result[i] = i;
        for (int i = 2; i * i <= n; i++) {
            if (result[i] == i) {
                for (int j = i * i; j <= n; j += i) {
                    result[j] = i; // Means that this number can be divided by i
                }
            }
        }
        return result;
    }

    private void updateFreq(int x, int delta) {
        while(x > 1) {
            int prime = primes[x];
            int oldCount = primeFrequency.getOrDefault(prime, 0);
            // Remove old count of prime
            if (oldCount > 0) {
                primeByLenFreq.put(oldCount, primeByLenFreq.get(oldCount) - 1);
            }
            int newCount = oldCount + delta;
            primeFrequency.put(prime, newCount);
            // Add new count of prime
            if (newCount > 0) {
                primeByLenFreq.put(newCount, primeByLenFreq.getOrDefault(newCount, 0) + 1);
            }
            // Divide until possible
            while (x % prime == 0) x /= prime;
        }
    }

    private boolean canDrop(int[] nums, int p) {
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            int g = 0;
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                g = gcd(nums[j], g);
            }
            if (g == p) return true;
        }
        return false;
    }
    
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public int countGoodSubseq(int[] nums, int p, int[][] queries) {
        int n = nums.length;
        this.primes = factors(50001);
        int pcnt = 0;
        for (int x: nums) {
            if (x % p == 0) {
                ++pcnt;
                updateFreq(x / p, +1);
            }
        }
        int result = 0;
        for (int[] q: queries) {
            int qidx = q[0];
            if (nums[qidx] % p == 0) {
                --pcnt;
                updateFreq(nums[qidx] / p, -1); // Remove old number
            }
            nums[qidx] = q[1];
            if (nums[qidx] % p == 0) {
                ++pcnt;
                updateFreq(nums[qidx] / p, +1); // Add new number
            }
            // Here we should identify if we can fulfil the requirements
            if (pcnt == 0 || primeByLenFreq.getOrDefault(pcnt, 0) > 0) 
                continue;
            if (pcnt < n) result++;
            else if (pcnt == n) result += n > 6 || canDrop(nums, p) ? 1: 0;
        }
        return result;
    }

}
