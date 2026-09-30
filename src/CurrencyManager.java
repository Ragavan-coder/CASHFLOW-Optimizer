import java.util.Map;
import java.util.HashMap;

public class CurrencyManager {
    private Map<String, Double> rates = new HashMap<>();
    
    public void setExchangeRate(Currency from, Currency to, double rate) {
        rates.put(from.name() + "_" + to.name(), rate);
        rates.put(to.name() + "_" + from.name(), 1.0 / rate);
    }
    
    public double getRate(Currency from, Currency to) {
        if (from == to) return 1.0;
        String key = from.name() + "_" + to.name();
        if (rates.containsKey(key)) {
            return rates.get(key);
        }
        throw new IllegalArgumentException("Exchange rate not found for " + from + " to " + to);
    }
    
    public long convert(long amount, Currency from, Currency to) {
        if (from == to) return amount;
        double rate = getRate(from, to);
        return Math.round(amount * rate);
    }
}
