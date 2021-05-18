package wolox.training.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.models.Book;
import wolox.training.models.UserBook;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;

    private UserBook user;

    private List<Book> listBooks;

    private Book book1;

    @BeforeEach
    public void setup() {
        List<Book> listBooks = new ArrayList<>();
        book1 = new Book("comedian", "author", "image", "comedyTest", "subtitle", "norma", "2020", "pages",
                "isbn");
        book1.setId(1);
        Book book2 = new Book("comedian", "author", "image", "dramaTest", "subtitle", "norma", "2020", "pages",
                "isbn");
        book2.setId(2);
        listBooks.add(book1);
        listBooks.add(book2);
        user = new UserBook("caroTest", "carolina", LocalDate.of(1990, 03, 03), listBooks);

    }

    @Test
    public void whenAddUser_ThenCreatedOk() throws Exception {

        String userBody = "{\"username\":\"caroTest\",\"password\":\"123\",\"name\":\"carolina\",\"birthdate\":\"1990-03-03\"}";
        when(userRepository.save(user)).thenReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userBody))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    public void whenFindByName_ThenFoundOk() throws Exception {

        String userNamePath = "carolina";
        Optional<UserBook> optionalUser = Optional.of(user);

        when(userRepository.findByName(userNamePath)).thenReturn(optionalUser);

        mvc.perform(get("/users/name/{name}", "carolina"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("caroTest"));


    }

    @Test
    @WithMockUser
    public void whenDeleteUserById_ThenDeletedOk() throws Exception {

        user.setId(1);
        Optional<UserBook> optionalUser = Optional.of(user);

        when(userRepository.findById(new Long(1))).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(any(Long.class));

        mvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void whenUpdateUser_ThenUpdatedOk() throws Exception {

        user.setId(1);
        Optional<UserBook> optionalUser = Optional.of(user);
        String userBody = "{\"id\":\"1\",\"username\":\"caroUpdateTest\",\"name\":\"carolinaUpdate\",\"birthdate\":\"1990-03-03\"}";
        UserBook userUpdate = new UserBook("caroUpdateTest", "carolinaUpdate", LocalDate.of(1990, 03, 03), listBooks);

        when(userRepository.findById(new Long(1))).thenReturn(optionalUser);
        when(userRepository.save(user)).thenReturn(user);

        mvc.perform(put("/users/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userBody))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void whenAddUserBook_ThenAddUserBookOk() throws Exception {

        user.setId(1);
        Optional<UserBook> optionalUser = Optional.of(user);
        Optional<Book> optionalBook = Optional.of(book1);

        when(userRepository.findById(new Long(1))).thenReturn(optionalUser);
        when(bookRepository.findById(new Long(1))).thenReturn(optionalBook);
        when(userRepository.save(user)).thenReturn(user);

        mvc.perform(put("/users/{userId}/books/{bookId}", 1, 1))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void whenDeleteUserBook_ThenDeletedUserBookOk() throws Exception {

        user.setId(1);
        Optional<UserBook> optionalUser = Optional.of(user);
        Optional<Book> optionalBook = Optional.of(book1);

        when(userRepository.findById(new Long(1))).thenReturn(optionalUser);
        when(bookRepository.findById(new Long(1))).thenReturn(optionalBook);
        when(userRepository.save(user)).thenReturn(user);

        mvc.perform(delete("/users/{userId}/books/{bookId}", 1, 1))
                .andExpect(status().isOk());


    }

    @Test
    @WithMockUser
    public void whenDeleteUserBook_ThenErrorBookNotFound() throws Exception {

        user.setId(1);
        Optional<UserBook> optionalUser = Optional.of(user);
        Optional<Book> optionalBook = Optional.of(book1);

        when(userRepository.findById(new Long(1))).thenReturn(optionalUser);
        when(bookRepository.findById(new Long(1))).thenReturn(optionalBook);
        when(userRepository.save(user)).thenReturn(user);

        MvcResult result = mvc.perform(delete("/users/{userId}/books/{bookId}", 1, 3))
                .andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

}
