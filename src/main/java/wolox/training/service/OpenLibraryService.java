package wolox.training.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.common.Constants;
import wolox.training.controllers.BookController;
import wolox.training.dto.BookInfoDTO;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@Service
public class OpenLibraryService {

    BookRepository bookRepository;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.api.uri}")
    private String uriExternalApi;

    private BookController bookController;

    @Autowired
    public OpenLibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> findBookByIsbn(String isbn) throws JsonProcessingException {

        Optional<Book> bookFind = bookRepository.findByIsbn(isbn);

        if (!bookFind.isPresent()) {
            Optional<BookInfoDTO> bookInfoDTO = Optional
                    .ofNullable(findBookInfo(isbn).orElseThrow(() -> new BookNotFoundException(
                            Constants.MESSAGE_ERROR_NOT_FOUND_BOOK)));

            bookFind = bookInfoDTOToBook(bookInfoDTO.get(), isbn);
            bookRepository.save(bookFind.get());
            return bookFind;

        }

        return bookFind;
    }

    public Optional<BookInfoDTO> findBookApiExternal(String isbn) throws JsonProcessingException {

        Optional<BookInfoDTO> bookInfoDTO = Optional
                .ofNullable(findBookInfo(isbn).orElseThrow(() -> new BookNotFoundException(
                        Constants.MESSAGE_ERROR_NOT_FOUND_BOOK)));

        return bookInfoDTO;

    }

    private Optional<BookInfoDTO> findBookInfo(String isbn) throws JsonProcessingException {

        String bookInfo;

        bookInfo = new RestTemplate()
                .getForObject(uriExternalApi + Constants.URL_EXTERNAL_API + isbn + Constants.URL_EXTERNAL_API_PARAM,
                        String.class);

        Map<String, BookInfoDTO> map = new ObjectMapper()
                .readValue(bookInfo, new TypeReference<HashMap<String, BookInfoDTO>>() {
                });
        BookInfoDTO bookInfoDTO = map.get(Constants.KEY_ISBN + isbn);

        return Optional.of(bookInfoDTO);

    }

    private Optional<Book> bookInfoDTOToBook(BookInfoDTO bookInfoDTO, String isbn) {
        Book book = new Book();
        book.setTitle(bookInfoDTO.getTitle());
        book.setSubtitle(bookInfoDTO.getSubtitle());
        book.setAuthor(bookInfoDTO.getAuthors().get(0).getName());
        book.setIsbn(isbn);
        book.setPublisher(bookInfoDTO.getPublishers().get(0).getName());
        book.setYear(bookInfoDTO.getPublish_date());
        book.setPages(String.valueOf(bookInfoDTO.getNumber_of_pages()));
        book.setImage("image");
        book.setGenre("genre");

        return Optional.of(book);


    }

}
