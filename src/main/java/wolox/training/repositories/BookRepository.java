package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

/**
 * This class do any crud operation over Book entity
 *
 * @author carolina.marulanda@wolox.co
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Permits find any exist book by its title
     *
     * @param title: Title of book object
     *
     * @return {@link Optional} Book's list
     */
    Optional<Book> findByTitle(String title);


    /**
     * Permits find any exist book by its isbn
     *
     * @param isbn: Title of book object
     *
     * @return {@link Optional} Book's list
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Find all books which do match with publisher, genre and year
     */
    @Query("select b from Book b "
            + "where (:publisher IS NULL OR b.publisher = :publisher) "
            + "and (:year IS NULL OR b.year = :year) "
            + "and (:genre IS NULL OR b.genre = :genre)")
    List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre,
            @Param("year") String year);


}
