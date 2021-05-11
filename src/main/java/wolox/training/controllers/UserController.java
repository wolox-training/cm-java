package wolox.training.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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


    /**
     * This method returns a user {@link UserBook}, which match with username received as parameter
     *
     * @param username user's username
     *
     * @return {@link UserBook} book's user
     */
    @GetMapping("/username/{userUsername}")
    public UserBook findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Not found user by username"));
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
        return userRepository.findByName(name).orElseThrow(() -> new UserNotFoundException("Not found user by name"));
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
                .orElseThrow(() -> new UserNotFoundException("Not found user id"));
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
                .orElseThrow(() -> new UserNotFoundException("Not found user id"));
        Book book = bookRepository.findById(Long.parseLong(bookId))
                .orElseThrow(() -> new BookNotFoundException("Not found book id"));
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
    public void addBookListUser(@PathVariable String userId, @PathVariable String bookId) {

        UserBook user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UserNotFoundException("Not found user id"));
        Book book = bookRepository.findById(Long.parseLong(bookId))
                .orElseThrow(() -> new BookNotFoundException("Not found book id"));
        List<Book> books = user.addBookList(book);
        user.setBooks(books);
        userRepository.save(user);
    }

    /**
     * This method update a user received by parameter
     *
     * @param userBook: User object with update parameters
     * @param id:       User's identify number
     *
     * @return {@link UserBook} book updated
     */
    @PutMapping("/{id}")
    public UserBook updateBook(@RequestBody UserBook userBook, @PathVariable Long id) {
        if (userBook.getId() != id) {
            throw new UserNotFoundException("Not found user id");
        }
        userRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Not found user id"));
        return userRepository.save(userBook);
    }


}