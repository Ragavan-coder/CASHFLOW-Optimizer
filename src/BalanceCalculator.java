import java.util.Map;
import java.util.HashMap;

public class BalanceCalculator {
    public static Map<String, Long> calculateNetBalances(CashFlowGraph graph, CurrencyManager currencyManager, Currency targetCurrency) {
        Map<String, Long> balances = new HashMap<>();
        
        for (Debt debt : graph.getAllDebts()) {
            long amount = debt.getAmount();
            
            if (debt.getCurrency() != targetCurrency) {
                amount = currencyManager.convert(amount, debt.getCurrency(), targetCurrency);
            }
            
            String debtor = debt.getDebtor();
            String creditor = debt.getCreditor();
            
            balances.put(debtor, balances.getOrDefault(debtor, 0L) - amount);
            balances.put(creditor, balances.getOrDefault(creditor, 0L) + amount);
        }
        
        balances.values().removeIf(val -> val == 0);
        return balances;
    }
}
