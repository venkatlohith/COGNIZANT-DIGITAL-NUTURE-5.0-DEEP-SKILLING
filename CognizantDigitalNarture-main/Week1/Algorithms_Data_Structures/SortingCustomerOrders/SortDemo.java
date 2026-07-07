class Order {

    int orderId;
    String customerName;
    double totalPrice;

    Order(int orderId,
          String customerName,
          double totalPrice) {

        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }
}

public class SortDemo {

    static void bubbleSort(Order[] orders) {

        int n = orders.length;

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (orders[j].totalPrice >
                        orders[j + 1].totalPrice) {

                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    static int partition(Order[] arr,
                         int low,
                         int high) {

        double pivot = arr[high].totalPrice;

        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (arr[j].totalPrice < pivot) {

                i++;

                Order temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Order temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    static void quickSort(Order[] arr,
                          int low,
                          int high) {

        if (low < high) {

            int pi = partition(arr,
                    low,
                    high);

            quickSort(arr,
                    low,
                    pi - 1);

            quickSort(arr,
                    pi + 1,
                    high);
        }
    }

    static void display(Order[] orders) {

        for (Order order : orders) {

            System.out.println(
                    order.orderId + " "
                            + order.customerName + " "
                            + order.totalPrice);
        }
    }

    public static void main(String[] args) {

        Order[] orders = {

                new Order(101,
                        "Saketh",
                        5000),

                new Order(102,
                        "Ram",
                        2000),

                new Order(103,
                        "Krishna",
                        8000)
        };

        System.out.println("Before Sorting:");
        display(orders);

        quickSort(orders,
                0,
                orders.length - 1);

        System.out.println("\nAfter Quick Sort:");
        display(orders);
    }
}