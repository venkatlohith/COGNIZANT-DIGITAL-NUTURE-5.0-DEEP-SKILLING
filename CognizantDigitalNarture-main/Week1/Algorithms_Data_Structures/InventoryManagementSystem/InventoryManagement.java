import java.util.HashMap;

class Product {
    int productId;
    String productName;
    int quantity;
    double price;

    Product(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String toString() {
        return productId + " " + productName + " " + quantity + " " + price;
    }
}

public class InventoryManagement {

    HashMap<Integer, Product> inventory = new HashMap<>();

    void addProduct(Product p) {
        inventory.put(p.productId, p);
    }

    void updateProduct(int id, int quantity, double price) {

        if (inventory.containsKey(id)) {

            Product p = inventory.get(id);

            p.quantity = quantity;
            p.price = price;
        }
    }

    void deleteProduct(int id) {
        inventory.remove(id);
    }

    void displayProducts() {

        for (Product p : inventory.values()) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) {

        InventoryManagement ims = new InventoryManagement();

        ims.addProduct(new Product(101, "Laptop", 10, 50000));
        ims.addProduct(new Product(102, "Mouse", 20, 1000));

        System.out.println("Products:");
        ims.displayProducts();

        ims.updateProduct(101, 15, 55000);

        ims.deleteProduct(102);

        System.out.println("\nAfter Update/Delete:");
        ims.displayProducts();
    }
}