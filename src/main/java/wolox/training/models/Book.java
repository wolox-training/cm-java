package wolox.training.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Permits generate and manage entity Book object
 */
@Entity
public class Book {

    /**
     * Identifier for book entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**Identifier for book entity */
    @Column
    private String genre;
    /**Author for book entity */
    @Column(nullable = false)
    private String author;
    /**iIage for book entity */
    @Column(nullable = false)
    private String image;
    /**Title for book entity */
    @Column(nullable = false)
    private String title;
    /**Subtitle for book entity */
    @Column(nullable = false)
    private String subtitle;
    /**Publisher for book entity */
    @Column(nullable = false)
    private String publisher;
    /**Year publish for book entity */
    @Column(nullable = false)
    private String year;
    /**Number pages for book entity */
    @Column(nullable = false)
    private String pages;
    /**Number ISBN for book entity */
    @Column(nullable = false)
    private String isbn;

    public Book(String genre, String author, String image, String title, String subtitle, String publisher, String year, String pages, String isbn) {
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

    public Book(){}

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    @Override
    public String toString() {
        return "Book [id=" + id + ", genre=" + genre + ", author=" + author + ", image=" + image + ", title=" + title + ", subtitle=" + subtitle + ", publisher=" + publisher + ", year" + year + ", pages=" + pages + ", isbn=" + isbn + "]";

    }
}
