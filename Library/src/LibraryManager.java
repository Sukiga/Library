import java.sql.*;
import java.util.HashMap;

public class LibraryManager {
    // the library manager stores the books that can be borrowed or remove books
    // it also allow searching, sorting and filtering

    private Connection con;
    private static final String url = "jdbc:postgresql://localhost:5432/librarydatabase";
    private static final String username = "sukimak";
    private static final String password = "1234";

    private HashMap<String, Book> books; // the key is ISBN
    // TODO: connect librarydatabase with hashmaps, so that everytime app runs,
    //       maps get the existing books, authors and publishers from librarydatabase


    public LibraryManager() {
        books = new HashMap<>();
    }

    public String searchByTitle(String title) {
        if (!(this.books.isEmpty())) {
            for (String key: this.books.keySet()) {
                if (this.books.get(key).getTitle().contains(title)) {
                    return this.books.get(key).toString();
                }
            }
        }
        return "Sorry, this book is currently not available in Suki's library.";
    }


    public String add(String isbn, Book book) {
        if (books.containsKey(isbn)) {
            return "The book is already in the library.";
        } else {
            this.books.put(isbn, book);
            con = getConnection();
            try {
                PreparedStatement sql = con.prepareStatement("INSERT INTO " +
                        "books(title, author_id, publisher_id, published_year, " +
                        "summary, isbn) VALUES (?, ?, ?, ?, ?, ?);");
                int authorId = getOrCreateID(con, book.getAuthor(), "authors");
                int publisherId = getOrCreateID(con, book.getPublisher(), "publishers");
                sql.setString(1, book.getTitle());
                sql.setInt(2, authorId);
                sql.setInt(3, publisherId);
                sql.setInt(4, book.getYear());
                sql.setString(5, book.getSummary());
                sql.setString(6, book.getISBN());
                sql.executeUpdate();
                System.out.println("Book Inserted Successfully!");
            } catch (SQLException e) {
                System.err.println("Error Inserting a book");
                e.printStackTrace();
            }

            return "The book has successfully added in the library!";
        }
    }

    // if it's false, something is wrong
    // Purpose: remove any book with the same isbn
    public boolean remove(String isbn, Book book) {
        this.books.remove(isbn);
        return !(books.containsKey(isbn));
    }

    public boolean remove(String title) {
        if (books.isEmpty()) {
            return false;
        }
        for (String key: books.keySet()) {
            if (books.get(key).getTitle().equals(title)) {
                books.remove(key);
                return true;
            }
        }
        return false;
    }

    // print all the books
    public void printBooks() {
        if (!(books.isEmpty())) {
            for (String isbn: this.books.keySet()) {
                System.out.println(this.books.get(isbn));
            }
        } else {
            System.out.println("There's no book in Suki's library now.");
        }
    }

    //SQL
    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connected Successfully!");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    /*
    *
    *
    *
    */
    public int getOrCreateID(Connection con, String name, String table) throws SQLException{
        // name could be an author name or a publisher name
        String findSql = "SELECT id FROM " + table + " WHERE name = ?;";
        try(PreparedStatement findSt = con.prepareStatement(findSql)) {
            findSt.setString(1, name);
            ResultSet rs = findSt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        String createSql = "INSERT INTO " + table + "(name) VALUES (?) RETURNING id;";
        try (PreparedStatement createSt = con.prepareStatement(createSql)) {
            createSt.setString(1, name);
            ResultSet rs = createSt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException(name + " insertion failed.");
            }
        }

    }

}
