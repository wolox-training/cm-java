package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.UserBook;

/**
 * This class do any crud operation over User entity
 *
 * @author carolina.marulanda@wolox.co
 */
@Repository
public interface UserRepository extends JpaRepository<UserBook, Long> {

    /**
     * Permits find any exist user by its username
     * @param username: Name user's book
     * @return {@link Optional} User
     */
    Optional<UserBook> findByUsername(String username);

    /**
     * Finds a book by id.
     * @param name: Name of user's book
     * @return {@link Optional} an object book
     */
    Optional<UserBook> findByName(String name);
}
