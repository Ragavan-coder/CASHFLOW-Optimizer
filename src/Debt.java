import java.time.LocalDate;

public class Debt {
    private String debtor;
    private String creditor;
    private long amount;
    private Currency currency;
    private LocalDate dueDate;
    private Priority priority;

    public enum Priority {
        LOW, NORMAL, HIGH, CRITICAL
    }

    public Debt(String debtor, String creditor, long amount, Currency currency) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
        this.currency = currency;
        this.priority = Priority.NORMAL;
    }

    public String getDebtor() { return debtor; }
    public String getCreditor() { return creditor; }
    public long getAmount() { return amount; }
    public Currency getCurrency() { return currency; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
}
