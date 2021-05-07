package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * @param title: Title of book object
     * @return {@link Optional} Book's list
     */
    Optional<Book> findByTitle(String title);

    /**
     * Finds a book by id.
     * @param id book's identification
     * @return {@link Book} an object book
     */
    Book findBookById(Long id);
}
