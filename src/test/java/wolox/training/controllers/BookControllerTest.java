package wolox.training.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {


    TestRestTemplate restTemplateTest;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookRepository bookRepository;
    private Book book;

    @BeforeEach
    public void setup() {
        book = new Book("comedian", "author", "image", "comedyTest", "subtitle", "norma", "2020", "pages", "isbn");
    }

    @Test
    public void whenAddBook_ThenCreatedOk() throws Exception {

        String bookBody = "{\"genre\":\"drama\",\"author\":\"author\",\"image\":\"image\",\"title\":\"book3\",\"subtitle\":\"subtitle\",\"publisher\":\"publisher\",\"year\":\"2020\",\"pages\":\"pages\",\"isbn\":\"isbn\"}";
        when(bookRepository.save(book)).thenReturn(book);

        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookBody))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    public void whenFindByTitle_ThenFoundOk() throws Exception {

        String bookPath = "comedyTest";
        Optional<Book> optionalBook = Optional.of(book);

        when(bookRepository.findByTitle(bookPath)).thenReturn(optionalBook);

        mvc.perform(get("/books/title/{bookTitle}", "comedyTest"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("comedyTest"));

    }

    @Test
    @WithMockUser
    public void whenDeleteById_ThenDeletedOk() throws Exception {

        book.setId(1);
        Optional<Book> optionalBook = Optional.of(book);

        when(bookRepository.findById(new Long(1))).thenReturn(optionalBook);
        doNothing().when(bookRepository).deleteById(any(Long.class));

        mvc.perform(delete("/books/{id}", 1))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void whenUpdateBook_ThenUpdatedOk() throws Exception {

        book.setId(1);
        Optional<Book> optionalBook = Optional.of(book);
        String bookBody = "{\"id\":\"1\",\"genre\":\"drama\",\"author\":\"author\",\"image\":\"image\",\"title\":\"book3\",\"subtitle\":\"subtitle\",\"publisher\":\"publisher\",\"year\":\"2020\",\"pages\":\"pages\",\"isbn\":\"isbn\"}";

        when(bookRepository.findById(new Long(1))).thenReturn(optionalBook);
        when(bookRepository.save(book)).thenReturn(book);

        mvc.perform(put("/books/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookBody))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void whenUpdateBook_ThenErrorUpdated() throws Exception {

        book.setId(1);
        Optional<Book> optionalBook = Optional.of(book);
        String bookBody = "{\"id\":\"2\",\"genre\":\"drama\",\"author\":\"author\",\"image\":\"image\",\"title\":\"book3\",\"subtitle\":\"subtitle\",\"publisher\":\"publisher\",\"year\":\"2020\",\"pages\":\"pages\",\"isbn\":\"isbn\"}";

        when(bookRepository.findById(new Long(1))).thenReturn(optionalBook);
        when(bookRepository.save(book)).thenReturn(book);

        MvcResult result = mvc.perform(put("/books/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookBody)).andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

    }

}
