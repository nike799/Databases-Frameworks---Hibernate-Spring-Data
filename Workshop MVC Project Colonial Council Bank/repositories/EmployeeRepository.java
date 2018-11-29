package app.ccb.repositories;

import app.ccb.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query("select e from app.ccb.domain.entities.Employee e where concat(e.firstName,' ', e.lastName) like :fullname")
    Optional<Employee> findByFullName(@Param("fullname") String fullName);
    @Query("select e from app.ccb.domain.entities.Employee e where e.clients.size > 0 order by e.clients.size desc, e.id asc")
    Optional<List<Employee>> findAllByClientsIsNotNullOrderByClientsDescIdAsc();
}
