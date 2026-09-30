import java.util.*;

public class ConstraintValidator {

    public static List<Transaction> applyConstraints(List<Transaction> transactions, List<PaymentConstraint> constraints) {
        List<Transaction> result = new ArrayList<>();
        Map<String, Long> dailyTransferred = new HashMap<>();
        
        for (Transaction transaction : transactions) {
            PaymentConstraint limitConstraint = findConstraint(transaction.getFrom(), constraints);
            
            if (limitConstraint != null) {
                long amountLeft = transaction.getAmount();
                while (amountLeft > 0) {
                    long transferAmount = Math.min(amountLeft, limitConstraint.getMaxPerTransaction());
                    
                    long transferredToday = dailyTransferred.getOrDefault(transaction.getFrom(), 0L);
                    if (transferredToday + transferAmount > limitConstraint.getDailyLimit()) {
                        transferAmount = limitConstraint.getDailyLimit() - transferredToday;
                        if (transferAmount <= 0) break;
                    }
                    
                    Transaction splitTransaction = new Transaction(transaction.getFrom(), transaction.getTo(), transferAmount, transaction.getCurrency());
                    result.add(splitTransaction);
                    amountLeft -= transferAmount;
                    dailyTransferred.put(transaction.getFrom(), transferredToday + transferAmount);
                }
            } else {
                result.add(transaction);
            }
        }
        
        return result;
    }

    private static PaymentConstraint findConstraint(String person, List<PaymentConstraint> constraints) {
        for (PaymentConstraint paymentConstraint : constraints) {
            if (paymentConstraint.getPerson().equals(person)) {
                return paymentConstraint;
            }
        }
        return null;
    }
}
