package wolox.training.persistence;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenFindByName_thenReturnBook() {
        // given
        Book book = new Book("comedian", "author", "image", "comedyTest", "subtitle", "norma", "2020", "pages", "isbn");
        entityManager.persist(book);
        entityManager.flush();

        // when
        Optional<Book> found = bookRepository.findByTitle(book.getTitle());

        // then
        assertThat(found.get().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    public void whenAddBookNullField_thenReturnDBError() {
        // given
        Book book = new Book();
        book.setImage("jsgj");

        // when
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> {
            bookRepository.save(book);
        });

        // then
        assertThat(e.equals(DataIntegrityViolationException.class));
    }


}
