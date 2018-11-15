package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AuthorService {
    void seedAuthors() throws IOException;
    Map<Author,List<Book>> getAuthorsByCountOfBooks();
}
