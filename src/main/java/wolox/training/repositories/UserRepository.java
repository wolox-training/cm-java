package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
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
     *
     * @param username: Name user's book
     *
     * @return {@link Optional} an object user
     */
    Optional<UserBook> findByUsername(String username);

    /**
     * Finds a user by name.
     *
     * @param name: Name of user's book
     *
     * @return {@link Optional} an object user
     */
    Optional<UserBook> findByName(String name);

    /**
     * Find all users with birthday between two dates name sequence defined
     */
    List<UserBook> findByBirthdateBetweenAndNameIgnoreCaseContaining(LocalDate dateIni, LocalDate dateEnd,
            String charsName);

}
