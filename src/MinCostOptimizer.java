import java.util.*;

public class MinCostOptimizer {

    public static List<Transaction> optimize(Map<String, Long> balances, Currency baseCurrency, FeeCalculator feeCalculator) {
        List<Transaction> transactions = GreedyOptimizer.optimize(balances, baseCurrency);
        
        for (Transaction transaction : transactions) {
            transaction.setFee(feeCalculator.calculateFee(transaction.getAmount()));
        }
        
        return transactions;
    }
}
