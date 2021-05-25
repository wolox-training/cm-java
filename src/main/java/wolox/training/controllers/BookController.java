package wolox.training.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.common.Constants;
import wolox.training.exceptions.BookInternalException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.service.OpenLibraryService;

/**
 * This class permits create, update, deleted and list over Book object
 *
 * @author carolina.marulanda@wolox.co
 */
@RestController
@RequestMapping("/books")
@Api

public class BookController {

    @Autowired
    OpenLibraryService openLibraryService;
    /**
     * Injects Book repository for book's operations
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * This method returns greeting string.
     *
     * @param name:Name received as parameter from url
     * @param model:    Model  that set name to the view template
     *
     * @return greeting with name passed by parameter
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    /**
     * This method returns book's {@link List}, which match with title received as parameter
     *
     * @param bookTitle book's title
     *
     * @return {@link Optional} book's list
     */
    @GetMapping("/title/{bookTitle}")
    public Optional<Book> findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/")
    public Page<Book> findByAnyParam(@RequestParam(required = false) String publisher,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String subtitle,
            @RequestParam(required = false) String pages,
            @RequestParam(required = false) String isbn) {

        Pageable sortedByTitle = PageRequest.of(0, 10, Sort.by(Constants.SORT_BOOK));
        return bookRepository
                .getAll(publisher, genre, year, author, image, title, subtitle, pages, isbn, sortedByTitle);
    }

    /**
     * This method creates a book object
     *
     * @param book: parameters
     *
     * @return {@link Book} book created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * This method deleted a book, which match with id received by parameter
     *
     * @param id: Book's identify number
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(
                        () -> new BookNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_BOOK));
        bookRepository.deleteById(id);
    }

    /**
     * This method update a book received by parameter
     *
     * @param book: Book object with update parameters
     * @param id:   Book's identify number
     *
     * @return {@link Book} book updated
     */
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {

        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_BOOK));
        book.setId(id);
        return bookRepository.save(book);
    }

    @GetMapping("/isbn/{isbn}")
    public Optional<Book> findBookByIsbn(@PathVariable String isbn) {
        try {
            return openLibraryService.findBookByIsbn(isbn);
        } catch (JsonProcessingException e) {
            throw new BookInternalException(e.getMessage());

        }

    }

}
