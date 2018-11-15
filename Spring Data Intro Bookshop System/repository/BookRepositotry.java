package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepositotry extends JpaRepository<Book,Integer> {
    List<Book> findAllByReleaseDateIsAfter(LocalDate localDate);
    List<Book> findAllByReleaseDateBefore(LocalDate localDate);
    List<Book> findAllByAuthor_FirstNameAndAuthor_LastName(String firstName,String lastName);

}