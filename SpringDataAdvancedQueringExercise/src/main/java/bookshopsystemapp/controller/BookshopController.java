package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import bookshopsystemapp.util.InputReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final InputReader reader;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService,
                              BookService bookService, InputReader reader) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.reader = reader;
    }


    @Override
    public void run(String... strings) throws Exception {
//        this.authorService.seedAuthors();
//        this.categoryService.seedCategories();
//        this.bookService.seedBooks();
        System.out.println("Enter the number of Exercise:");
        int number = Integer.parseInt(reader.readLine());
        switch (number) {
            case 1:
                System.out.println("Enter the age restriction type:");
                String ageRestriction = reader.readLine().toUpperCase();
                bookService.selectBooksTittlesByAgeRestriction(ageRestriction).forEach(System.out::println);
                break;
            case 2:
                bookService.selectAllTitleOfGoldenEditionBooksLessThan5000Copies().forEach(System.out::println);
                break;
            case 3:
                bookService.selectAllBooksTittlesByPriceIsLessThanOrPriceGreaterThan().forEach(System.out::println);
                break;
            case 4:
                System.out.println("Enter year:");
                int year = Integer.parseInt(reader.readLine());
                bookService.selectAllBooksByReleaseDateNotIn(year).forEach(System.out::println);
                break;
            case 5:
                System.out.println("Enter date:");
                String date = reader.readLine();
                bookService.selectAllBooksByReleaseDateBefore(date).forEach(System.out::println);
                break;
            case 6:
                System.out.println("Enter string:");
                String str = reader.readLine();
                authorService.selectAllAuthorsByFirstNameEndsWith(str).forEach(System.out::println);
                break;
            case 7:
                System.out.println("Enter string:");
                str = reader.readLine();
                bookService.selectAllBooksTittlesByContainsString(str).forEach(System.out::println);
                break;
            case 8:
                System.out.println("Enter string:");
                str = reader.readLine();
                bookService.selectAllBooksByAuthorsLastNameStartsWith(str).forEach(System.out::println);
                break;
            case 9:
                System.out.println("Enter number:");
                Integer numberOfBooks = Integer.parseInt(reader.readLine());
                System.out.println(bookService.countBooksByLengthOfTittleGreaterThanGivenNumber(numberOfBooks));
                break;
            case 10:
                bookService.selectAuthorsWithSumOfCopiesOrderedDesc().forEach(System.out::println);
                break;
            case 11:
                System.out.println("Enter title:");
                String title = reader.readLine();
                System.out.println(bookService.getReducedBookInfo(title));
                break;
            case 12:
                System.out.println("Enter date:");
                String date1 = reader.readLine();
                System.out.println("Enter amount of copies for update:");
                int copiesForUpdate = Integer.parseInt(reader.readLine());
                System.out.println(bookService.getUpdatedCopiesForGivenCriteria(date1, copiesForUpdate));
                break;
            case 13:
                System.out.println("Enter the number of copies:");
                int numberOfCopies = Integer.parseInt(reader.readLine());
                System.out.println(bookService.removeBooksUnderGivenCountOfCopies(numberOfCopies));
                break;
            case 14:
                System.out.println("Enter author's name:");
                String[] authorName = reader.readLine().split("\\s+");
                System.out.println(bookService.getNumberOfBooksTheGivenAuthorHasWritten(authorName[0],authorName[1]));
                break;
        }
    }
}
