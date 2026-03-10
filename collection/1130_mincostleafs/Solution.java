class Solution {

    public int mctFromLeafValues(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        stack.push(Integer.MAX_VALUE);
        int result = 0;
        for (int x: arr) {
            // Keep stack monotonicaly decreasing
            while (stack.peek() <= x) {
                int mid = stack.pop(); // small value
                result += mid * Math.min(stack.peek(), x);
            }
            stack.push(x);
        }
        while (stack.size() > 2) {
                result += stack.pop() * stack.peek();
            }
        return result;
    }
}
