package wolox.training.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class permits manage user´s books
 */

@Entity
public class UserBook {


    /**Username user's books */
    @Id
    @Column(name="ID")
    private int id;
    @Column(name="USERNAME",nullable = false)
    private String username;
    /**First name user's books */
    @Column(name="NAME",nullable = false)
    private String name;
    /**Date of birthday user's books */
    @Column(name="BIRTHDATE",nullable = false)
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

    public int getId() {
        return id;
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

    /**
     * Shows user object on string
     * @return {@link String} object shape in string
     */
    @Override
    public String toString() {
        return "User [username=" + username + ", name=" + name + ", birthday=" + birthdate + ", books=" + books.toString() + "]";

    }
}
