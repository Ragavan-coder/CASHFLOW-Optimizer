import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class CashFlowGraph {
    private Map<String, List<Debt>> adjacencyList = new HashMap<>();

    public void addDebt(Debt debt) {
        adjacencyList.computeIfAbsent(debt.getDebtor(), k -> new ArrayList<>()).add(debt);
    }
    
    public Map<String, List<Debt>> getAdjacencyList() {
        return adjacencyList;
    }
    
    public void clear() {
        adjacencyList.clear();
    }
    
    public List<Debt> getAllDebts() {
        List<Debt> allDebts = new ArrayList<>();
        for (List<Debt> debts : adjacencyList.values()) {
            allDebts.addAll(debts);
        }
        return allDebts;
    }
}
