public class Main {
    public static void main(String[] args) {

        StockMarket market = new StockMarket();

        market.registerObserver(new MobileApp());
        market.registerObserver(new WebApp());

        market.setPrice(500);
    }
}