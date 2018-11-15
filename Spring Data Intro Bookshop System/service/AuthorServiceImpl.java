package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.repository.AuthorRpository;
import bookshopsystemapp.repository.BookRepositotry;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final static String AUTHORS_FILE_PATH =
            "C:\\Users\\Nike\\IdeaProjects\\SpringDataIntroBookshopSystem\\src\\main\\resources\\files\\authors.txt";
    private final AuthorRpository authorRpository;
    private final BookRepositotry bookRepositotry;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRpository authorRpository, BookRepositotry bookRepositotry, FileUtil fileUtil) {
        this.authorRpository = authorRpository;
        this.bookRepositotry = bookRepositotry;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRpository.count() != 0) {
            return;
        }
        String[] authorFileContent = fileUtil.getFileContent(AUTHORS_FILE_PATH);
        for (String line : authorFileContent) {
            String[] lineParams = line.split("\\s+");
            Author author = new Author(lineParams[0], lineParams[1]);
            this.authorRpository.saveAndFlush(author);
        }
    }

    @Override
    public Map<Author, List<Book>> getAuthorsByCountOfBooks() {
        Map<Author, List<Book>> authors = new LinkedHashMap<>();
        this.bookRepositotry.findAll().stream().filter(book -> book.getAuthor() != null).forEach(book -> {
            authors.putIfAbsent(book.getAuthor(), new ArrayList<>());
            authors.get(book.getAuthor()).add(book);
        });
        return authors;
    }
}
