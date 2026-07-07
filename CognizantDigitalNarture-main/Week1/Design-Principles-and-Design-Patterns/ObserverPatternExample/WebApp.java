public class WebApp implements Observer {

    public void update(double price) {
        System.out.println("Web App: Price Updated = " + price);
    }
}