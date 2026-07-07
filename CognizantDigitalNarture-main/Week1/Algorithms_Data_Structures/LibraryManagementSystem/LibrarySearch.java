class Book {

    int bookId;
    String title;
    String author;

    Book(int bookId,
         String title,
         String author) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }
}

public class LibrarySearch {

    static int linearSearch(
            Book[] books,
            String title) {

        for (int i = 0; i < books.length; i++) {

            if (books[i].title
                    .equalsIgnoreCase(title))

                return i;
        }

        return -1;
    }

    static int binarySearch(
            Book[] books,
            String title) {

        int low = 0;
        int high = books.length - 1;

        while (low <= high) {

            int mid =
                    (low + high) / 2;

            int result =
                    books[mid].title
                            .compareToIgnoreCase(title);

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

        Book[] books = {

                new Book(
                        1,
                        "Java",
                        "James"),

                new Book(
                        2,
                        "Python",
                        "Guido"),

                new Book(
                        3,
                        "Spring",
                        "Rod")
        };

        System.out.println(
                "Linear Search: "
                        + linearSearch(
                        books,
                        "Python"));

        System.out.println(
                "Binary Search: "
                        + binarySearch(
                        books,
                        "Python"));
    }
}