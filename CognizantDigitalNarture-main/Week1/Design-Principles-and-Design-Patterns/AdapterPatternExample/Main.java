public class Main {
    public static void main(String[] args) {

        PaymentProcessor payment =
                new PayPalAdapter(new PayPalGateway());

        payment.processPayment(500);
    }
}