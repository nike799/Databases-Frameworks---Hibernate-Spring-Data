package bookshopsystemapp.service;


import java.io.IOException;
import java.util.List;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> selectBooksTittlesByAgeRestriction(String ageRestriction);

    List<String> selectAllTitleOfGoldenEditionBooksLessThan5000Copies();

    List<String> selectAllBooksTittlesByPriceIsLessThanOrPriceGreaterThan();

    List<String> selectAllBooksByReleaseDateNotIn(int year);

    List<String> selectAllBooksByReleaseDateBefore(String date);

    List<String> selectAllBooksTittlesByContainsString(String part);

    List<String> selectAllBooksByAuthorsLastNameStartsWith(String part);

    Integer countBooksByLengthOfTittleGreaterThanGivenNumber(Integer number);

    List<String> selectAuthorsWithSumOfCopiesOrderedDesc();

    String getReducedBookInfo(String title);

    Integer getUpdatedCopiesForGivenCriteria(String date, int copiesForUpdate);

    String removeBooksUnderGivenCountOfCopies(int number);

    String getNumberOfBooksTheGivenAuthorHasWritten(String firsName,String lastName);

}
