import java.sql.*;
import java.util.HashMap;

public class LibraryManager {
    // the library manager stores the books that can be borrowed or remove books
    // it also allow searching, sorting and filtering

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
                if (this.books.get(key).getTitle().equalsIgnoreCase(title)) {
                    return this.books.get(key).toString();
                }
            }
        }
        return "Sorry, this book is currently not available in Suki's library.";
    }


    public String add(String isbn, Book book) {
        if (books.keySet().contains(isbn)) {
            return "The book is already in the library.";
        } else {
            this.books.put(isbn, book);
            try (Connection con = DriverManager.getConnection(url, username, password)){
                int authorId = getOrCreateID(con, book.getAuthor(), "authors");
                int publisherId = getOrCreateID(con, book.getPublisher(), "publishers");
                String sql = "INSERT INTO books(title, author_id, publisher_id, " +
                        "published_year, summary, isbn) VALUES (?, ?, ?, ?, ?, ?);";
                try (PreparedStatement st = con.prepareStatement(sql)) {
                    st.setString(1, book.getTitle());
                    st.setInt(2, authorId);
                    st.setInt(3, publisherId);
                    st.setInt(4, book.getYear());
                    st.setString(5, book.getSummary());
                    st.setString(6, book.getISBN());
                    st.executeUpdate();
                    System.out.println("Book Inserted Successfully!");

                }
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
    public void reachDatabase(String sql) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            st.execute(sql);
            System.out.println("Connection Successful!");
        } catch(SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    System.err.println("Failed to close connection.");
                    e.printStackTrace();
                }
            }
        }
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
