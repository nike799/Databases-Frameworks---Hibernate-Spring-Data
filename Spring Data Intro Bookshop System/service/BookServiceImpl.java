package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.Category;
import bookshopsystemapp.domain.entities.enums.EditionType;
import bookshopsystemapp.repository.AuthorRpository;
import bookshopsystemapp.repository.BookRepositotry;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final static String BOOK_FILE_PATH =
            "C:\\Users\\Nike\\IdeaProjects\\SpringDataIntroBookshopSystem\\src\\main\\resources\\files\\books.txt";
    private final CategoryRepository categoryRepository;
    private final AuthorRpository authorRpository;
    private final FileUtil fileUtil;
    private final BookRepositotry bookRepositotry;

    @Autowired
    public BookServiceImpl(CategoryRepository categoryRepository,
                           AuthorRpository authorRpository, FileUtil fileUtil, BookRepositotry bookRepositotry) {
        this.categoryRepository = categoryRepository;
        this.authorRpository = authorRpository;
        this.fileUtil = fileUtil;
        this.bookRepositotry = bookRepositotry;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepositotry.count() != 0) {
            return;
        }
        String[] bookFileContent = this.fileUtil.getFileContent(BOOK_FILE_PATH);
        for (String line : bookFileContent) {
            String[] lineParams = line.split("\\s+");
            Author author = getrandomAuthor();
            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            LocalDate releasedDate = getParsedDate(lineParams[1]);
            Integer copies = Integer.parseInt(lineParams[2]);
            BigDecimal price = new BigDecimal(lineParams[3]);
            String tittle = Arrays.stream(lineParams).skip(5).collect(Collectors.joining(" "));

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releasedDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setTittle(tittle);
            book.setCategories(getRandomCategory(this.categoryRepository));
            System.out.println();
            this.bookRepositotry.saveAndFlush(book);

        }
    }

    @Override
    public List<Book> getBooksOfGeorgePowell() {
        return this.bookRepositotry.findAllByAuthor_FirstNameAndAuthor_LastName("George","Powell");
    }

    @Override
    public List<Book> getAllBookTittlesReleasedBefore() {
        return this.bookRepositotry.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));
    }

    @Override
    public List<Book> getAllBookTittlesReleasedAfter() {
        return this.bookRepositotry.findAllByReleaseDateIsAfter(LocalDate.parse("2000-12-31"));
    }

    private LocalDate getParsedDate(String lineParam) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String date = lineParam;
        return LocalDate.parse(date, formatter);
    }

    private Author getrandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(Math.toIntExact(this.authorRpository.count() - 1));
        return authorRpository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategory(CategoryRepository categoryRepository) {
        Random random = new Random(5);
        Set<Category> categories = new HashSet<>();
        Set<Category> checkCategories = new HashSet<>(this.categoryRepository.findAll());
        for (int i = 1; i < categoryRepository.count() - 1; i++) {
            //  int randomId = random.nextInt(Math.toIntExact(categoryRepository.count()-2))+1;
            Category category = categoryRepository.findById(i).get();

            categories.add(category);
        }
        return categories;
    }

}
