import java.util.List;

public class Settlement {
    private List<Transaction> transactions;
    private long totalAmountTransferred;
    private long totalFees;

    public Settlement(List<Transaction> transactions, long totalAmountTransferred, long totalFees) {
        this.transactions = transactions;
        this.totalAmountTransferred = totalAmountTransferred;
        this.totalFees = totalFees;
    }

    public List<Transaction> getTransactions() { return transactions; }
    public long getTotalAmountTransferred() { return totalAmountTransferred; }
    public long getTotalFees() { return totalFees; }
    public long getTotalCost() { return totalAmountTransferred + totalFees; }
}
