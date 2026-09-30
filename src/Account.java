public class Account {
    private String id;
    private String ownerName;
    private Currency currency;

    public Account(String id, String ownerName, Currency currency) {
        this.id = id;
        this.ownerName = ownerName;
        this.currency = currency;
    }

    public String getId() { return id; }
    public String getOwnerName() { return ownerName; }
    public Currency getCurrency() { return currency; }
}
