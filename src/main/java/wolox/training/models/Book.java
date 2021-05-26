package wolox.training.models;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NonNull;

/**
 * Permits generate and manage entity Book object
 */
@Data
@Entity
public class Book {

    /**
     * Identifier for book entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /** Genre for book entity */
    @NonNull
    @Column(name = "GENRE", nullable = false)
    private String genre;
    /** Author for book entity */
    @NonNull
    @Column(name = "AUTHOR", nullable = false)
    private String author;
    /** Image for book entity */
    @NonNull
    @Column(name = "IMAGE", nullable = false)
    private String image;
    /** Title for book entity */
    @NonNull
    @Column(name = "TITLE", nullable = false)
    private String title;
    /** Subtitle for book entity */
    @NonNull
    @Column(name = "SUBTITLE", nullable = false)
    private String subtitle;
    /** Publisher for book entity */
    @NonNull
    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;
    /** Year publish for book entity */
    @NonNull
    @Column(name = "YEAR", nullable = false)
    private String year;
    /** Number pages for book entity */
    @NonNull
    @Column(name = "PAGES", nullable = false)
    private String pages;
    /** Number ISBN for book entity */
    @NonNull
    @Column(name = "ISBN", nullable = false)
    private String isbn;

    public Book(String genre, String author, String image, String title, String subtitle, String publisher, String year,
            String pages, String isbn) {
        super();
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }

    public Book() {
    }

    public void setYear(String year) {

        Preconditions.checkNotNull(year, "year cant be null");
        Preconditions.checkArgument(!year.isEmpty(), "year cant be empty");
        Preconditions.checkArgument(Long.parseLong(year) > 0, "year should be higher than Zero");
        Preconditions
                .checkArgument(Long.parseLong(year) < LocalDate.now().getYear(), "year should be less than this year");
        this.year = year;
    }

}
