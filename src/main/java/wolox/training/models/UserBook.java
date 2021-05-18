package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * This class permits to manage user´s books
 */

@Entity
@ApiModel(description = "Users, who are book's owner")
public class UserBook {


    /** Identifier user's books */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /** Username user's books */
    @Column(name = "USERNAME", nullable = false)
    @ApiModelProperty(notes = "Username, that could be any name the user wants for identifier", required = true)
    private String username;
    /** First name user's books */
    @Column(name = "NAME", nullable = false)
    private String name;
    /** Date of birthday user's books */
    @Column(name = "BIRTHDATE", nullable = false)
    private LocalDate birthdate;
    /** List user's books */
    @ManyToMany
    private List<Book> books = new ArrayList<>();

    /**
     * Empty constructor
     */
    public UserBook() {
    }

    /**
     * Constructor with parameter of entity User
     *
     * @param username:   user's name
     * @param name:user's name
     * @param birthdate:  Date of birthday
     * @param books:      List user's books
     */
    public UserBook(String username, String name, LocalDate birthdate, List<Book> books) {
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
        this.books = books;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Preconditions.checkNotNull(username, "username cant be null");
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name, "name cant be null");

        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate, "birthdate cant be null");
        Preconditions.checkArgument(birthdate.isBefore(LocalDate.now()), "Birthday should be before");
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        Preconditions.checkArgument(!books.isEmpty(), "must not be null");
        this.books = books;
    }

    public List<Book> addBookList(Book book) {
        this.books.add(book);
        return this.books;
    }

    public List<Book> deleteBookList(Book book) {
        this.books.remove(book);
        return this.books;
    }

    /**
     * Shows user object on string
     *
     * @return {@link String} object shape in string
     */
    @Override
    public String toString() {
        return "User [username=" + username + ", name=" + name + ", birthday=" + birthdate + ", books=" + books
                .toString() + "]";

    }
}
