package bots.crew.university.connectors;

import bots.crew.university.dto.LectorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorConnector extends JpaRepository<LectorDTO, Long> {

    List<LectorDTO> getByDepartmentsName(String name);
    int countByDepartmentsName(String name);

    @Query("SELECT l.fullName FROM LECTOR l WHERE LOWER(l.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) or LOWER(l.degree) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<String> searchByAllStringFields(@Param("searchTerm") String searchTerm);

}
