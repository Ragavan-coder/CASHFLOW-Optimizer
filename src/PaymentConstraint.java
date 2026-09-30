public class PaymentConstraint {
    private String person;
    private long maxPerTransaction;
    private long dailyLimit;
    private Currency currency;

    public PaymentConstraint(String person, long maxPerTransaction, long dailyLimit, Currency currency) {
        this.person = person;
        this.maxPerTransaction = maxPerTransaction;
        this.dailyLimit = dailyLimit;
        this.currency = currency;
    }

    public String getPerson() { return person; }
    public long getMaxPerTransaction() { return maxPerTransaction; }
    public long getDailyLimit() { return dailyLimit; }
    public Currency getCurrency() { return currency; }
}
