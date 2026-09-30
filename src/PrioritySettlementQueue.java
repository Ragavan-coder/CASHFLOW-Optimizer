import java.util.*;

public class PrioritySettlementQueue {

    public static void prioritize(List<Transaction> transactions) {
        transactions.sort((t1, t2) -> {
            if (t1.getDueDate() != null && t2.getDueDate() != null) {
                return t1.getDueDate().compareTo(t2.getDueDate());
            }
            if (t1.getDueDate() != null) return -1;
            if (t2.getDueDate() != null) return 1;
            return Long.compare(t2.getAmount(), t1.getAmount());
        });
    }
}
