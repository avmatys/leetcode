class Solution {
    public int subarrayBitwiseORs(int[] arr) {
        Set<Integer> result = new HashSet<>();
        Set<Integer> current = new HashSet<>();
        for(int x: arr) {
            Set<Integer> next = new HashSet<>();
            next.add(x);
            for (int y: current) {
                next.add(x | y);
            }
            current = next;
            result.addAll(current);
        }
        return result.size();
    }
}
