import java.util.*;

public class Main {
    private static CashFlowGraph graph = new CashFlowGraph();
    private static CurrencyManager currencyManager = new CurrencyManager();

    public static void main(String[] args) {
        System.out.println("Starting web server...");
        
        graph.addDebt(new Debt("Hima", "Arun", 5000, Currency.INR));
        graph.addDebt(new Debt("Arun", "Priya", 3000, Currency.INR));
        graph.addDebt(new Debt("Priya", "Hima", 2000, Currency.INR));
        graph.addDebt(new Debt("Hima", "Kiran", 4000, Currency.INR));
        
        WebServer.startServer(graph, currencyManager);
    }
}
