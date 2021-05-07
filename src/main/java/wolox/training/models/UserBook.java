package wolox.training.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * This class permits manage user´s books
 */
public class UserBook {

    /**Username user's books */
    private String username;
    /**First name user's books */
    private String name;
    /**Date of birthday user's books */
    private LocalDate birthdate;
    /**List user's books  */
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity=Book.class, orphanRemoval = true)
    private List<Book> books;

    /**
     * Empty constructor
     */
    public UserBook() {
    }

    /**
     * Constructor with parameter of entity User
     * @param username: user's name
     * @param name:user's name
     * @param birthdate: Date of birthday
     * @param books: List user's books
     */
    public UserBook(String username, String name, LocalDate birthdate, List<Book> books) {
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
        this.books = books;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBookList(Book book){
        this.books.add(book);
    }

    public void deleteBookList(Book book){
        this.books.remove(book);
    }
}
