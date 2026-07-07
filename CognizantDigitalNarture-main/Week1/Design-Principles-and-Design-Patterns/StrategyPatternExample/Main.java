public class Main {
    public static void main(String[] args) {

        PaymentContext payment =
                new PaymentContext(new CreditCardPayment());

        payment.executePayment(1000);
    }
}