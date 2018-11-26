package cardealer.repository;

import cardealer.domain.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part,Long> {

}
