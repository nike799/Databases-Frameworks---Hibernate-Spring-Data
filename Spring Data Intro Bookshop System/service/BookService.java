package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<Book> getAllBookTittlesReleasedAfter();
    List<Book> getAllBookTittlesReleasedBefore();
    List<Book> getBooksOfGeorgePowell();


}
