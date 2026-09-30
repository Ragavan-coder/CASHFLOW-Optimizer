public class FeeCalculator {
    public enum FeeModel {
        FIXED, PERCENTAGE, TIERED
    }
    
    private FeeModel model;
    private long fixedFee;
    private double percentage;
    
    public FeeCalculator(FeeModel model, long fixedFee, double percentage) {
        this.model = model;
        this.fixedFee = fixedFee;
        this.percentage = percentage;
    }
    
    public long calculateFee(long amount) {
        switch (model) {
            case FIXED:
                return fixedFee;
            case PERCENTAGE:
                return Math.round(amount * percentage);
            case TIERED:
                if (amount <= 100000) return 500;
                if (amount <= 500000) return 1500;
                return 3000;
            default:
                return 0;
        }
    }
}
