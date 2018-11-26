package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.ReducedBook;
import bookshopsystemapp.domain.entities.enums.AgeRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
    List<Book> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);

    @Query("SELECT b FROM bookshopsystemapp.domain.entities.Book b  WHERE  b.editionType = 2  AND b.copies < :copies")
    List<Book> findAllByCopiesLessThan(@Param(value = "copies") Integer copies);

    List<Book> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal lowPrice, BigDecimal highPrice);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate start, LocalDate end);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContains(String part);

    List<Book> findAllByAuthorLastNameStartsWith(String part);

    @Query(value = "SELECT COUNT(*) FROM books b WHERE CHARACTER_LENGTH(b.title) > :num", nativeQuery = true)
    Integer findAByTitleIsAfter(@Param(value = "num") Integer number);

    List<Book> findAllBy();

    @Query(value = "SELECT new bookshopsystemapp.domain.entities.ReducedBook (b.title,b.editionType,b.ageRestriction,b.price) " +
            " FROM bookshopsystemapp.domain.entities.Book b WHERE b.title = :title")
    ReducedBook getProjectionForBook(@Param(value = "title") String title);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE bookshopsystemapp.domain.entities.Book AS b SET b.copies = b.copies + :numValue WHERE b.releaseDate > :dateValue")
    void updateCopiesByGivenValue(@Param(value = "numValue") int numValue, @Param(value = "dateValue") LocalDate date);

    @Query(value = "SELECT SUM(b.copies) FROM bookshopsystemapp.domain.entities.Book b WHERE b.releaseDate > :dateValue")
    int getSumOfCopiesAfterGivenDate(@Param(value = "dateValue") LocalDate date);

    @Modifying
    @Query("DELETE FROM bookshopsystemapp.domain.entities.Book AS b WHERE b.copies < :givenCopies")
    void removeAllByCopiesIsLessThan(@Param(value = "givenCopies") int numberOfCopies);

    @Procedure(name = "usp_get_count_of_books_of_given_author")
    int countBooksByAuthor_FirstNameAndAuthor_LastName(@Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName);
	
    // Problem 14
    @Query(value = "call books_by_author(:firstName, :lastName)", nativeQuery = true)
    Object[] storedProcedure(@Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName);
}
