import java.util.*;

public class RouteOptimizer {
    
    public static class Edge {
        public String to;
        public long fee;

        public Edge(String to, long fee) {
            this.to = to;
            this.fee = fee;
        }
    }
    
    private Map<String, List<Edge>> graph = new HashMap<>();

    public void addAccountRoute(String from, String to, long fee) {
        graph.computeIfAbsent(from, key -> new ArrayList<>()).add(new Edge(to, fee));
    }
    
    public long findMinFee(String start, String end) {
        Map<String, Long> distances = new HashMap<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(edge -> edge.fee));
        
        for (String node : graph.keySet()) {
            distances.put(node, Long.MAX_VALUE);
        }
        distances.put(start, 0L);
        
        priorityQueue.add(new Edge(start, 0));
        
        while (!priorityQueue.isEmpty()) {
            Edge current = priorityQueue.poll();
            String u = current.to;
            
            if (u.equals(end)) return current.fee;
            
            if (current.fee > distances.getOrDefault(u, Long.MAX_VALUE)) continue;
            
            if (graph.containsKey(u)) {
                for (Edge neighbor : graph.get(u)) {
                    long newDist = distances.get(u) + neighbor.fee;
                    if (newDist < distances.getOrDefault(neighbor.to, Long.MAX_VALUE)) {
                        distances.put(neighbor.to, newDist);
                        priorityQueue.add(new Edge(neighbor.to, newDist));
                    }
                }
            }
        }
        
        return distances.getOrDefault(end, -1L);
    }
}
