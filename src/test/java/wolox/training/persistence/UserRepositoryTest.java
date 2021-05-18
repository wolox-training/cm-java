package wolox.training.persistence;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.UserBook;
import wolox.training.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    /*@Test
    public void whenFindByName_thenReturnUser() {
        // given
        UserBook user = new UserBook("caroTest", "carolina", LocalDate.of(1990, 03, 03), null);
        entityManager.persist(user);
        entityManager.flush();

        // when
        Optional<UserBook> found = userRepository.findByName(user.getName());

        // then
        assertThat(found.get().getName()).isEqualTo(user.getName());
    }*/

    @Test
    public void whenAddUserNullField_thenReturnDBError() {
        // given
        UserBook user = new UserBook();
        user.setUsername("usernameTest");

        // when
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user);
        });

        // then
        assertThat(e.equals(DataIntegrityViolationException.class));
    }


}
