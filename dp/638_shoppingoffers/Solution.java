class Solution {

    private Map<List<Integer>, Integer> memo;
    private List<Integer> price;
    private List<List<Integer>> special;

    private int solve(List<Integer> needs) {
        // Check if we have already calculated the same case
        if (memo.containsKey(needs)) {
            return memo.get(needs);
        }
        // Base case without special 
        int total = 0;
        for (int i = 0; i < needs.size(); i++) {
            total += price.get(i) * needs.get(i);
        }
        // Try to use each special and calc the price
        for (List<Integer> s: special) {
            boolean isOk = true;
            List<Integer> newNeeds = new ArrayList<>();
            for(int i = 0; i < needs.size() && isOk; i++) {
                if (s.get(i) > needs.get(i)) {
                    isOk = false;
                    break;
                } else {
                    newNeeds.add(needs.get(i) - s.get(i));
                }
            }
            if (!isOk) continue;
            total = Math.min(total, s.get(s.size() - 1) + solve(newNeeds));
        }
        memo.put(needs, total);
        return total;
    }

    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        this.memo = new HashMap<>(); 
        this.price = price;
        this.special = special;
        return this.solve(needs);   
    }
}
