package bookshopsystemapp.controller;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import bookshopsystemapp.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


@Controller
public class BookShopController implements CommandLineRunner {
    private final BufferedReader reader;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final ConsoleReader consoleReader;

    @Autowired
    public BookShopController(AuthorService authorService,
                              CategoryService categoryService, BookService bookService, ConsoleReader consoleReader) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.consoleReader = consoleReader;
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();

        System.out.println("Enter the number of query:".toUpperCase());
        int queryNumber = Integer.parseInt(consoleReader.readLine());
        switch (queryNumber) {
            case 1:
                this.bookService.getAllBookTittlesReleasedAfter().forEach(book -> System.out.println(book.getTittle()));
                break;
            case 2:
                Set<Author> authors = new HashSet<>();
                this.bookService.getAllBookTittlesReleasedBefore().forEach(book -> authors.add(book.getAuthor()));
                authors.forEach(author -> System.out.printf("%s %s\n", author.getFirstName(), author.getLastName()));
                break;
            case 3:
                this.authorService.getAuthorsByCountOfBooks().entrySet().stream().sorted((kv1, kv2) ->
                        Integer.compare(kv2.getValue().size(), kv1.getValue().size())
                ).
                        forEach((kv) ->
                                System.out.printf("%s %s %d\n",
                                        kv.getKey().getFirstName(),
                                        kv.getKey().getLastName(),
                                        kv.getValue().size())
                        );
                break;
            case 4:
                // title, release date and copies.
                this.bookService.getBooksOfGeorgePowell().stream().
                        sorted((b1, b2) -> {
                            if (b2.getReleaseDate().compareTo(b1.getReleaseDate()) != 0) {
                                return b2.getReleaseDate().compareTo(b1.getReleaseDate());
                            } else {
                                return b1.getTittle().compareTo(b2.getTittle());
                            }
                        }).forEach(book -> System.out.printf("Book's tittle: %s,  Release Date: %s,  Copies: %d\n",
                        book.getTittle(),
                        book.getReleaseDate(),
                        book.getCopies()));

                break;
        }
    }
}
