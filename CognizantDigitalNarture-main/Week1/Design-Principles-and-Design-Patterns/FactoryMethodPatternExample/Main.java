public class Main {
    public static void main(String[] args) {

        DocumentFactory factory = new WordFactory();

        Document doc = factory.createDocument();
        doc.open();
    }
}