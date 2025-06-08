import java.util.Scanner;

public class LibraryTextUI {

    private LibraryManager lib;


    public LibraryTextUI() {
        lib = new LibraryManager();
        start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Suki's Library: ");
        System.out.println("You may...");
        System.out.println("a -> browse the books");
        System.out.println("b -> search for a book");
        System.out.println("c -> add a book");
        System.out.println("d -> remove a book");
        System.out.println("q -> quit");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("a")) {
                lib.printBooks();
            } else if (input.equals("b")) {
                System.out.print("Search: ");
                String searchInput = scanner.nextLine();
                System.out.println(lib.searchByTitle(searchInput));
            } else if (input.equals("c")) {
                System.out.print("Title: ");
                String title = scanner.nextLine();
                System.out.print("Author: ");
                String author = scanner.nextLine();
                System.out.print("Publisher: ");
                String publisher = scanner.nextLine();
                System.out.print("Year: ");
                int year = Integer.valueOf(scanner.nextLine());
                System.out.print("Summary: ");
                String summary = scanner.nextLine();
                System.out.print("ISBN: ");
                String isbn = scanner.nextLine();
                Book book = new Book(title, author, publisher, year, summary, isbn);
                System.out.println(lib.add(isbn, book));
            } else if (input.equals("d")) {

            } else if (input.equals("q")) {
                break;
            }
        }
    }


}
