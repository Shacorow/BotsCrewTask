package bots.crew.university.connectors;

import bots.crew.university.dto.DepartmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentConnector extends JpaRepository<DepartmentDTO, Long> {

    DepartmentDTO getByName(String name);

    @Query("SELECT d.name FROM DEPARTMENT d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<String> searchByAllStringFields(@Param("searchTerm") String searchTerm);

}
