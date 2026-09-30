import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.InetSocketAddress;
import java.io.File;

public class WebServer {
    public static void startServer(CashFlowGraph graph, CurrencyManager currencyManager) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/api", new ApiHandler(graph, currencyManager));
            server.createContext("/", new StaticFileHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Server started at http://localhost:8080");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            File file = new File("web" + path);
            if (file.exists() && !file.isDirectory()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                if (path.endsWith(".html")) {
                    exchange.getResponseHeaders().add("Content-Type", "text/html");
                } else if (path.endsWith(".css")) {
                    exchange.getResponseHeaders().add("Content-Type", "text/css");
                } else if (path.endsWith(".js")) {
                    exchange.getResponseHeaders().add("Content-Type", "application/javascript");
                }
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(bytes);
                }
            } else {
                String response = "404 (Not Found)\n";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(response.getBytes());
                }
            }
        }
    }
}
