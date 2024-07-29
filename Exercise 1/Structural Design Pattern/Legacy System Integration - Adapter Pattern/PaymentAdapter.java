public class PaymentAdapter implements PaymentProcessor {
    private LegacyPaymentProcessor legacyPaymentProcessor;

    public PaymentAdapter(LegacyPaymentProcessor legacyPaymentProcessor) {
        this.legacyPaymentProcessor = legacyPaymentProcessor;
    }

    @Override
    public void processPayment(double amount) {
        // Adapt to the legacy system's method signature
        legacyPaymentProcessor.oldPaymentMethod(amount, "USD");
    }
}
