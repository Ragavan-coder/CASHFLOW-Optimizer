import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class ApiHandler implements HttpHandler {
    private CashFlowGraph graph;
    private CurrencyManager currencyManager;

    public ApiHandler(CashFlowGraph graph, CurrencyManager currencyManager) {
        this.graph = graph;
        this.currencyManager = currencyManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        String response = "";
        int statusCode = 200;

        try {
            if ("GET".equals(method)) {
                if ("/api/balances".equals(path)) {
                    response = handleGetBalances();
                } else if ("/api/debts".equals(path)) {
                    response = handleGetDebts();
                } else if ("/api/settlement".equals(path)) {
                    response = handleGetSettlement();
                } else {
                    statusCode = 404;
                    response = "{\"error\": \"Not Found\"}";
                }
            } else {
                statusCode = 405;
                response = "{\"error\": \"Method Not Allowed\"}";
            }
        } catch (Exception e) {
            statusCode = 500;
            response = "{\"error\": \"" + e.getMessage() + "\"}";
        }

        byte[] bytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(bytes);
        }
    }

    private String handleGetBalances() {
        Map<String, Long> balances = BalanceCalculator.calculateNetBalances(graph, currencyManager, Currency.INR);
        StringBuilder stringBuilder = new StringBuilder("{");
        boolean isFirst = true;
        for (Map.Entry<String, Long> entry : balances.entrySet()) {
            if (!isFirst) stringBuilder.append(",");
            stringBuilder.append("\"").append(entry.getKey()).append("\":").append(entry.getValue());
            isFirst = false;
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String handleGetDebts() {
        List<Debt> debts = graph.getAllDebts();
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int index = 0; index < debts.size(); index++) {
            Debt debt = debts.get(index);
            stringBuilder.append("{")
              .append("\"debtor\":\"").append(debt.getDebtor()).append("\",")
              .append("\"creditor\":\"").append(debt.getCreditor()).append("\",")
              .append("\"amount\":").append(debt.getAmount()).append(",")
              .append("\"currency\":\"").append(debt.getCurrency()).append("\"")
              .append("}");
            if (index < debts.size() - 1) stringBuilder.append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private String handleGetSettlement() {
        Map<String, Long> balances = BalanceCalculator.calculateNetBalances(graph, currencyManager, Currency.INR);
        List<Transaction> transactions = GreedyOptimizer.optimize(balances, Currency.INR);
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int index = 0; index < transactions.size(); index++) {
            Transaction transaction = transactions.get(index);
            stringBuilder.append("{")
              .append("\"from\":\"").append(transaction.getFrom()).append("\",")
              .append("\"to\":\"").append(transaction.getTo()).append("\",")
              .append("\"amount\":").append(transaction.getAmount()).append(",")
              .append("\"currency\":\"").append(transaction.getCurrency()).append("\"")
              .append("}");
            if (index < transactions.size() - 1) stringBuilder.append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
