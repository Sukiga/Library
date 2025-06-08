public class Author {

    // an Author has a name and a bio

    private String name;
    private String bio;

    public Author(String authorName, String authorBio) {
        this.name = authorName;
        this.bio = authorBio;
    }

    /*
     * Getters method: used for demonstrating book information through GUI
     *                 and sorting and searching
     * 
     */

    public String getName() {
        return this.name;
    } 

    public String getBio() {
        return this.bio;
    }
    /*
     * Getter methods end
     */

    /*
     * Update methods: used for updating old, wrong, empty informations
     */

    public void updateName(String newName) {
        this.name = newName;
    } 

    public void updateBio(String bio) {
        this.bio = bio;
    }
    /*
     * Update methods end
     */

}
