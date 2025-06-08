import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    // Cart stores books to be borrowed
    // Customer put books they want to borrow in Carts

    ArrayList<Book> bookCart;

    public Cart() {
        bookCart = new ArrayList<>();
    }

    public void putABook(String isbn, HashMap<String, Book> books) {
        bookCart.add(books.get(isbn));
    }
}
