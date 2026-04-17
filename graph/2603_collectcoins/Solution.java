class Solution {
    public int collectTheCoins(int[] coins, int[][] edges) {
        int n = coins.length;
        int[] degree = new int[n];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) 
            graph.add(new ArrayList<>());
        for (int[] e: edges) {
            int u = e[0], v = e[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
            degree[u]++;
            degree[v]++;
        }
        Queue<Integer> q = new LinkedList<>();
        // Remove all nodes without coins 
        for(int u = 0; u < n; u++) {
            if (degree[u] == 1 && coins[u] == 0) q.add(u);
        }
        while(!q.isEmpty()) {
            int u = q.poll();
            if (degree[u] == 0) continue;
            degree[u] = 0; // no way here can appear smth with ne 1
            for (int v: graph.get(u)) {
                degree[v]--; // here we decrease the parent degree
                if (coins[v] == 0 && degree[v] == 1) 
                    q.add(v);
            }
        }  
        // Remove all leaves 2 times 
        for(int i = 0; i < 2; i++) {
            for (int u = 0; u < n; u++) {
                if (degree[u] == 1) q.add(u);
            }
            while(!q.isEmpty()) {
                int u = q.poll();
                if (degree[u] == 0) continue;
                degree[u] = 0; // No way here can be smth with ne 1
                for (int v: graph.get(u)) {
                    if (degree[v] > 0) degree[v]--;
                }
            }
        }
        int result = 0;
        for (int[] e: edges) {
            int u = e[0], v = e[1];
            result += degree[u] > 0 && degree[v] > 0 ? 1 : 0;
        }
        return 2 * result;
    }
}
