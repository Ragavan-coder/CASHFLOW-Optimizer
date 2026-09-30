import java.time.LocalDate;

public class Transaction {
    private String from;
    private String to;
    private long amount;
    private Currency currency;
    private long fee;
    private LocalDate dueDate;

    public Transaction(String from, String to, long amount, Currency currency) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.currency = currency;
        this.fee = 0;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public long getAmount() { return amount; }
    public Currency getCurrency() { return currency; }
    
    public long getFee() { return fee; }
    public void setFee(long fee) { this.fee = fee; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public long getTotalCost() { return amount + fee; }
    
    @Override
    public String toString() {
        return from + " -> " + to + " : " + amount + " " + currency + " (Fee: " + fee + ")";
    }
}
