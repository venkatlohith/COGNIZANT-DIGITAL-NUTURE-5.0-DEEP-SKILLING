public class Main {
    public static void main(String[] args) {

        Computer pc = new Computer.Builder()
                .setCpu("i7")
                .setRam(16)
                .setStorage(512)
                .build();

        pc.display();
    }
}