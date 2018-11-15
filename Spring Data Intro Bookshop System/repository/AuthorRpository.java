package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRpository extends JpaRepository<Author,Integer> {

}
