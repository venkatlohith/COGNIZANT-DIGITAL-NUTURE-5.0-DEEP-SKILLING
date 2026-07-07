class Product {

    int productId;
    String productName;
    String category;

    Product(int productId,
            String productName,
            String category) {

        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }
}

public class SearchDemo {

    static int linearSearch(Product[] products,
                            String key) {

        for (int i = 0; i < products.length; i++) {

            if (products[i].productName
                    .equalsIgnoreCase(key)) {

                return i;
            }
        }

        return -1;
    }

    static int binarySearch(Product[] products,
                            String key) {

        int low = 0;
        int high = products.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            int result =
                    products[mid].productName
                            .compareToIgnoreCase(key);

            if (result == 0)
                return mid;

            else if (result < 0)
                low = mid + 1;

            else
                high = mid - 1;
        }

        return -1;
    }

    public static void main(String[] args) {

        Product[] products = {

                new Product(1,
                        "Keyboard",
                        "Electronics"),

                new Product(2,
                        "Laptop",
                        "Electronics"),

                new Product(3,
                        "Mouse",
                        "Electronics")
        };

        int linearResult =
                linearSearch(products, "Laptop");

        int binaryResult =
                binarySearch(products, "Laptop");

        System.out.println(
                "Linear Search Index: "
                        + linearResult);

        System.out.println(
                "Binary Search Index: "
                        + binaryResult);
    }
}