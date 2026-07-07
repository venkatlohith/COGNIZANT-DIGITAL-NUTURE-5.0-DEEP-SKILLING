public class MobileApp implements Observer {

    public void update(double price) {
        System.out.println("Mobile App: Price Updated = " + price);
    }
}