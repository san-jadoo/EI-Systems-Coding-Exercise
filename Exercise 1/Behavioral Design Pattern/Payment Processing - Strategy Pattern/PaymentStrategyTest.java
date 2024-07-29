public class PaymentStrategyTest {
    public static void main(String[] args) {
        PaymentStrategy creditCardPayment = new CreditCardPayment("1234-5678-9876-5432");
        PaymentStrategy paypalPayment = new PayPalPayment("user@example.com");

        PaymentContext context = new PaymentContext(creditCardPayment);
        context.executePayment(100.00);

        context = new PaymentContext(paypalPayment);
        context.executePayment(50.00);
    }
}
