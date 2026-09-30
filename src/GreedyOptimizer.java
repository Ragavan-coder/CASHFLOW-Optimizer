import java.util.*;

public class GreedyOptimizer {

    static class PersonBalance implements Comparable<PersonBalance> {
        String name;
        long balance;

        public PersonBalance(String name, long balance) {
            this.name = name;
            this.balance = balance;
        }
        
        @Override
        public int compareTo(PersonBalance other) {
            return Long.compare(Math.abs(other.balance), Math.abs(this.balance));
        }
    }

    public static List<Transaction> optimize(Map<String, Long> balances, Currency baseCurrency) {
        List<Transaction> transactions = new ArrayList<>();
        
        MinHeap<PersonBalance> debtors = new MinHeap<>();
        MinHeap<PersonBalance> creditors = new MinHeap<>();

        for (Map.Entry<String, Long> entry : balances.entrySet()) {
            if (entry.getValue() < 0) {
                debtors.insert(new PersonBalance(entry.getKey(), entry.getValue()));
            } else if (entry.getValue() > 0) {
                creditors.insert(new PersonBalance(entry.getKey(), entry.getValue()));
            }
        }

        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            PersonBalance debtor = debtors.extractMin();
            PersonBalance creditor = creditors.extractMin();
            
            long settlementAmount = Math.min(Math.abs(debtor.balance), creditor.balance);
            
            Transaction transaction = new Transaction(debtor.name, creditor.name, settlementAmount, baseCurrency);
            transactions.add(transaction);
            
            debtor.balance += settlementAmount;
            creditor.balance -= settlementAmount;
            
            if (debtor.balance < 0) debtors.insert(debtor);
            if (creditor.balance > 0) creditors.insert(creditor);
        }
        
        return transactions;
    }
}
