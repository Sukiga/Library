public class Book {

    // a Book has a title, author, publisher, year, and summary

    private String title;
    private String author;
    private String publisher;
    private int year;
    private String summary;
    private String isbn;


    // constructor
    public Book(String title, String author, String publisher, int year, String summary, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.summary = summary;
        this.isbn = isbn;
    }

    /*
     * Getters method: used for demonstrating book information through GUI
     *                 and sorting and searching
     * 
     */

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public int getYear() {
        return this.year;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getISBN() {
        return this.isbn;
    }

    /*
     * Getter methods end
     */


    /*
     * Update methods: used for updating old, wrong, empty informations
     */

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }
    
    public void updateAuthor(String newAuthor) {
        this.author = newAuthor;
    }

    public void updatePublisher(String newPublisher) {
        this.publisher = newPublisher;
    }

    public void updateYear(int newYear) {
        this.year = newYear;
    }

    public void updateSummary(String newSummary) {
        this.summary = newSummary;
    }

    public void updateISBN(String newISBN) {
        this.isbn = newISBN;
    }
        
     
    /*
     * Update methods end
     */
    

    public String toString() {
        return this.title + " (" + this.year + ") by " + this.author; 
    }


}
