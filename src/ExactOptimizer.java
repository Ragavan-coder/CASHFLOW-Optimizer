import java.util.*;

public class ExactOptimizer {
    private static int minTransactions = Integer.MAX_VALUE;
    private static List<Transaction> bestPlan = new ArrayList<>();

    public static List<Transaction> optimize(Map<String, Long> balances, Currency baseCurrency) {
        List<String> people = new ArrayList<>();
        List<Long> balanceList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : balances.entrySet()) {
            if (entry.getValue() != 0) {
                people.add(entry.getKey());
                balanceList.add(entry.getValue());
            }
        }
        
        minTransactions = Integer.MAX_VALUE;
        bestPlan = new ArrayList<>();
        
        if (people.isEmpty()) return bestPlan;
        
        backtrack(balanceList, people, 0, 0, new ArrayList<>(), baseCurrency);
        
        return bestPlan;
    }

    private static void backtrack(List<Long> balances, List<String> people, int index, int currentTransactions, List<Transaction> currentPlan, Currency currency) {
        if (currentTransactions >= minTransactions) return;

        while (index < balances.size() && balances.get(index) == 0) {
            index++;
        }

        if (index == balances.size()) {
            if (currentTransactions < minTransactions) {
                minTransactions = currentTransactions;
                bestPlan = new ArrayList<>(currentPlan);
            }
            return;
        }

        long currentBalance = balances.get(index);
        
        for (int partnerIndex = index + 1; partnerIndex < balances.size(); partnerIndex++) {
            long nextBalance = balances.get(partnerIndex);
            
            if (currentBalance * nextBalance < 0) { 
                long settlementAmount = Math.min(Math.abs(currentBalance), Math.abs(nextBalance));
                
                String from = currentBalance < 0 ? people.get(index) : people.get(partnerIndex);
                String to = currentBalance > 0 ? people.get(index) : people.get(partnerIndex);
                
                Transaction transaction = new Transaction(from, to, settlementAmount, currency);
                currentPlan.add(transaction);
                
                balances.set(index, currentBalance + (currentBalance < 0 ? settlementAmount : -settlementAmount));
                balances.set(partnerIndex, nextBalance + (nextBalance < 0 ? settlementAmount : -settlementAmount));
                
                backtrack(balances, people, index, currentTransactions + 1, currentPlan, currency);
                
                balances.set(index, currentBalance);
                balances.set(partnerIndex, nextBalance);
                currentPlan.remove(currentPlan.size() - 1);
            }
        }
    }
}
