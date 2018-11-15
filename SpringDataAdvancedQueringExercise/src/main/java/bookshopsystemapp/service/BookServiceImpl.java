package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.Category;
import bookshopsystemapp.domain.entities.ReducedBook;
import bookshopsystemapp.domain.entities.enums.AgeRestriction;
import bookshopsystemapp.domain.entities.enums.EditionType;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static java.lang.String BOOKS_FILE_PATH =
            "C:\\Users\\Nike\\IdeaProjects\\SpringDataAdvancedQueringExercise\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> selectBooksTittlesByAgeRestriction(String ageRestriction) {
        return bookRepository.findAllByAgeRestrictionEquals(AgeRestriction.valueOf(ageRestriction)).
                stream().
                map(Book::getTitle).collect(Collectors.toList());

    }

    @Override
    public List<String> selectAllTitleOfGoldenEditionBooksLessThan5000Copies() {
        return bookRepository.findAllByCopiesLessThan(5000).stream().
                map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> selectAllBooksTittlesByPriceIsLessThanOrPriceGreaterThan() {
        return bookRepository.findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40)).
                stream().
                map(book -> java.lang.String.format("%s - $%.2f", book.getTitle(), book.getPrice())).
                collect(Collectors.toList());
    }

    @Override
    public List<String> selectAllBooksByReleaseDateNotIn(int year) {

        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(start, end).
                stream().
                map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> selectAllBooksByReleaseDateBefore(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1 = LocalDate.parse(date, dateTimeFormatter);
        return bookRepository.findAllByReleaseDateBefore(date1).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> selectAllBooksTittlesByContainsString(String part) {
        return bookRepository.findAllByTitleContains(part).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> selectAllBooksByAuthorsLastNameStartsWith(String part) {
        return bookRepository.findAllByAuthorLastNameStartsWith(part).
                stream().map(book -> java.lang.String.format("%s (%s %s)",
                book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName())).
                collect(Collectors.toList());
    }

    @Override
    public Integer countBooksByLengthOfTittleGreaterThanGivenNumber(Integer number) {
        return bookRepository.findAByTitleIsAfter(number);

    }

    @Override
    public List<String> selectAuthorsWithSumOfCopiesOrderedDesc() {
        Map<Author, Integer> authors = new LinkedHashMap<>();
        for (Book book : bookRepository.findAllBy()) {
            authors.putIfAbsent(book.getAuthor(), sumOfCopies(bookRepository.findAllBy(), book.getAuthor()));
        }
        return authors.entrySet().stream().
                sorted((kv1, kv2) -> kv2.getValue().
                        compareTo(kv1.getValue())).
                map(kv -> java.lang.String.format("%s %s - %d",
                        kv.getKey().getFirstName(),
                        kv.getKey().getLastName(),
                        kv.getValue())).
                collect(Collectors.toList());
    }

    @Override
    public String getReducedBookInfo(String title) {
        ReducedBook reducedBook = bookRepository.getProjectionForBook(title);
        return String.format("%s %s %s %.2f",
                reducedBook.getTitle(),
                reducedBook.getEditionType().name(),
                reducedBook.getAgeRestriction().name(),
                reducedBook.getPrice());

    }

    @Override
    public Integer getUpdatedCopiesForGivenCriteria(String date, int copiesForUpdate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate formattedDate = LocalDate.parse(date, dateTimeFormatter);
        int sumCopiesForUpdate = bookRepository.getSumOfCopiesAfterGivenDate(formattedDate);
        bookRepository.updateCopiesByGivenValue(copiesForUpdate, formattedDate);
        int sumCopiesAfterUpdate = bookRepository.getSumOfCopiesAfterGivenDate(formattedDate);
        return sumCopiesAfterUpdate - sumCopiesForUpdate;
    }

    @Override
    public String removeBooksUnderGivenCountOfCopies(int number) {
        int countBooksBeforeRemoving = bookRepository.findAllBy().size();
        bookRepository.removeAllByCopiesIsLessThan(number);
        int countBooksAfterRemoving = bookRepository.findAllBy().size();

        return String.format("%d books were deleted",countBooksBeforeRemoving-countBooksAfterRemoving);
    }

    @Override
    public String getNumberOfBooksTheGivenAuthorHasWritten(String firsName, String lastName) {
        int countBooks = bookRepository.countBooksByAuthor_FirstNameAndAuthor_LastName(firsName,lastName);
        return String.format("%s %s has written %d books",firsName,lastName,countBooks);
    }

    private int sumOfCopies(List<Book> books, Author author) {
        return books.stream().filter(book -> book.getAuthor().equals(author)).mapToInt(Book::getCopies).sum();
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }
}
