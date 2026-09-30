import java.util.*;

public class SettlementEngine {
    private CashFlowGraph graph;
    private CurrencyManager currencyManager;
    private FeeCalculator feeCalculator;
    private List<PaymentConstraint> constraints;

    public SettlementEngine(CashFlowGraph graph, CurrencyManager currencyManager, FeeCalculator feeCalculator, List<PaymentConstraint> constraints) {
        this.graph = graph;
        this.currencyManager = currencyManager;
        this.feeCalculator = feeCalculator;
        this.constraints = constraints;
    }

    public Settlement generateSettlement(Currency targetCurrency, boolean exact) {
        Map<String, Long> balances = BalanceCalculator.calculateNetBalances(graph, currencyManager, targetCurrency);
        
        List<Transaction> transactions;
        if (exact && balances.size() <= 10) {
            transactions = ExactOptimizer.optimize(balances, targetCurrency);
            for (Transaction transaction : transactions) {
                transaction.setFee(feeCalculator.calculateFee(transaction.getAmount()));
            }
        } else {
            transactions = MinCostOptimizer.optimize(balances, targetCurrency, feeCalculator);
        }
        
        transactions = ConstraintValidator.applyConstraints(transactions, constraints);
        PrioritySettlementQueue.prioritize(transactions);
        
        long totalAmount = 0;
        long totalFees = 0;
        for (Transaction transaction : transactions) {
            totalAmount += transaction.getAmount();
            totalFees += transaction.getFee();
        }
        
        return new Settlement(transactions, totalAmount, totalFees);
    }
}
