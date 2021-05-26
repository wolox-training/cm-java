package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT u FROM UserBook u "
            + "WHERE ((cast(:dateIni as LocalDate) IS NULL) OR u.birthdate >= :dateIni) "
            + "AND ((cast(:dateEnd as LocalDate) IS NULL) OR u.birthdate <= :dateEnd) "
            + "AND (:charsName IS NULL OR UPPER(u.name) LIKE CONCAT('%',UPPER(:charsName),'%'))")
    List<UserBook> findByBirthdateBetweenAndNameIgnoreCaseContaining(@Param("dateIni") LocalDate dateIni,
            @Param("dateEnd") LocalDate dateEnd, @Param("charsName") String charsName);

}
