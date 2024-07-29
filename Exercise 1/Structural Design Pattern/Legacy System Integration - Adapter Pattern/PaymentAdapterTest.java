public class PaymentAdapterTest {
    public static void main(String[] args) {
        // Create an instance of the legacy payment processor
        LegacyPaymentProcessor legacyProcessor = new LegacyPaymentProcessor();

        // Create an adapter for the new interface
        PaymentProcessor adapter = new PaymentAdapter(legacyProcessor);

        // Process a payment using the adapter
        adapter.processPayment(100.00);
    }
}
