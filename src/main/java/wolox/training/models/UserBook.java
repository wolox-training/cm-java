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
import lombok.Data;
import lombok.NonNull;

/**
 * This class permits to manage user´s books
 */

@Data
@Entity
@ApiModel(description = "Users, who are book's owner")
public class UserBook {


    /** Identifier user's books */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /** Username user's books */
    @NonNull
    @Column(name = "USERNAME", nullable = false)
    @ApiModelProperty(notes = "Username, that could be any name the user wants for identifier", required = true)
    private String username;
    @Column(name = "PASSWORD", nullable = true)
    private String password;
    /** First name user's books */
    @NonNull
    @Column(name = "NAME", nullable = false)
    private String name;
    /** Date of birthday user's books */
    @NonNull
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
}
