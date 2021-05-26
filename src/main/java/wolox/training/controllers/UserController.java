package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.common.Constants;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.UserBook;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;


/**
 * This class permits create, update, deleted and list over User object
 *
 * @author carolina.marulanda@wolox.co
 */
@RestController
@RequestMapping("/users")
@Api
public class UserController {

    /**
     * Injects UserRepository repository for user's operations
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Injects BookRepository repository for book's operations
     */
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * This method returns a user {@link UserBook}, which match with username received as parameter
     *
     * @param username user's username
     *
     * @return {@link UserBook} book's user
     */
    @GetMapping("/username/{username}")
    public UserBook findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_USER));
    }

    /**
     * This method returns a username {@link UserBook}, which is logged right now
     *
     * @return {@link UserBook} book's user
     */
    @GetMapping("/username")
    public Object getLoggedUser(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    /**
     * This method returns a user {@link UserBook}, which match with name received as parameter
     *
     * @param name user's name
     *
     * @return {@link UserBook} book's user
     */
    @GetMapping("/name/{name}")
    public UserBook findByName(@PathVariable String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_USER));
    }


    @GetMapping("/")
    public Page<UserBook> findByBirthdayAndCharsName(@RequestParam(required = false) String dateIni,
            @RequestParam(required = false) String dateEnd, @RequestParam(required = false) String charsName) {
        Pageable sortedByName = PageRequest.of(0, 10, Sort.by(Constants.SORT_USER));
        if (dateIni != null && dateEnd != null) {
            return userRepository
                    .findByBirthdateBetweenAndNameIgnoreCaseContaining(LocalDate.parse(dateIni),
                            LocalDate.parse(dateEnd), charsName, sortedByName);
        } else {
            return userRepository
                    .findByBirthdateBetweenAndNameIgnoreCaseContaining(null,
                            null, charsName, sortedByName);
        }
    }

    /**
     * This method creates a user object
     *
     * @param user: user's parameters
     *
     * @return {@link Book} user created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserBook create(@RequestBody UserBook user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * This method deleted a user, which match with id received by parameter
     *
     * @param id: User's identify number
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_USER));
        userRepository.deleteById(id);
    }

    /**
     * This method deleted a book from user's list books, which match with id received by parameter
     *
     * @param userId: User's identify number
     * @param bookId: Book's identify number
     */
    @DeleteMapping("/{userId}/books/{bookId}")
    public void deleteBookListUser(@PathVariable String userId, @PathVariable String bookId) {

        UserBook user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UserNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_USER));
        Book book = bookRepository.findById(Long.parseLong(bookId))
                .orElseThrow(() -> new BookNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_BOOK));
        List<Book> books = user.deleteBookList(book);
        user.setBooks(books);
        userRepository.save(user);
    }

    /**
     * This method add a book from user's list books, which match with id received by parameter
     *
     * @param userId: User's identify number
     * @param bookId: Book's identify number
     */
    @PutMapping("/{userId}/books/{bookId}")
    @ApiOperation(value = "Add a book to user's book collection")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found user id"),
            @ApiResponse(code = 404, message = "Not found book id")

    })
    public void addBookListUser(@ApiParam(value = "Id user book owner", required = true) @PathVariable String userId,
            @ApiParam(value = "Id book to add", required = true) @PathVariable String bookId) {

        UserBook user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UserNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_USER));
        Book book = bookRepository.findById(Long.parseLong(bookId))
                .orElseThrow(() -> new BookNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_BOOK));
        List<Book> books = user.addBookList(book);
        user.setBooks(books);
        userRepository.save(user);
    }

    /**
     * This method update a user received by parameter, password isn't updated
     *
     * @param userBook: User object with update parameters
     * @param id:       User's identify number
     *
     * @return {@link UserBook} user updated without password
     */
    @PutMapping("/{id}")
    public UserBook updateUserBook(@RequestBody UserBook userBook, @PathVariable Long id) {

        UserBook userFind = userRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_USER));
        userBook.setId(id);
        userBook.setPassword(userFind.getPassword());
        return userRepository.save(userBook);
    }

    /**
     * Update only user's password
     *
     * @return {@link UserBook} user only password updated
     */
    @PatchMapping("/{username}")
    public UserBook updatePasswordUser(@PathVariable String username) {
        UserBook userFind = userRepository.findByUsername(username).
                orElseThrow(() -> new UserNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_USER));
        userFind.setPassword(passwordEncoder.encode(userFind.getPassword()));
        return userRepository.save(userFind);
    }


}
