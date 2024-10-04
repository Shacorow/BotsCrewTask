package bots.crew.test.service;

import bots.crew.test.UniversityUnitTest;
import bots.crew.university.bom.Degree;
import bots.crew.university.bom.Lector;
import bots.crew.university.connectors.LectorConnector;
import bots.crew.university.convertors.LectorConvertor;
import bots.crew.university.dto.LectorDTO;
import bots.crew.university.services.LectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LectorServiceTest extends UniversityUnitTest {

    @InjectMocks
    private LectorService lectorService;

    @Mock
    private LectorConnector lectorConnector;

    @Spy
    private LectorConvertor lectorConvertor;


    @Test
    public void shouldCreateLector() {
        Lector lector = new Lector();
        lector.setFullName("John Smith");
        lector.setDegree(Degree.PROFESSOR);
        lector.setSalary(3000);
        LectorDTO lectorDTO = createLector(1L, "John Smith", Degree.PROFESSOR.name(), 4000);


        when(lectorConnector.save(any())).thenReturn(lectorDTO);
        Lector result = lectorService.create(lector);
        assertEquals(1L, result.getId());
        assertEquals(lector.getFullName(), result.getFullName());
        assertEquals(lector.getDegree(), result.getDegree());
        assertEquals(lector.getSalary(), result.getSalary());
    }

    @Test
    public void shouldFindByDepartmentName() {
        LectorDTO lectorDTOFirst = createLector(1L, "John Smith", Degree.PROFESSOR.name(), 4000);
        LectorDTO lectorDTOSecond = createLector(2L, "Jane Smith", Degree.ASSISTANT.name(), 1000);
        List<LectorDTO> lectorDTOList = List.of(lectorDTOFirst, lectorDTOSecond);


        when(lectorConnector.getByDepartmentsName("Mathematics")).thenReturn(lectorDTOList);
        List<Lector> results = lectorService.findByDepartmentName("Mathematics");
        assertNotNull(results);
        int count = 0;
        for (LectorDTO lector : lectorDTOList) {
            for (Lector result : results) {
                if (result.getId().equals(lector.getId())
                        && result.getSalary() == lector.getSalary()
                        && result.getFullName().equals(lector.getFullName()) &&
                        lector.getDegree().equals(result.getDegree().name())) {
                    count++;
                }
            }
        }
        assertEquals(lectorDTOList.size(), count);
    }

    @Test
    public void shouldGetCountLectorInDepartment() {
        when(lectorConnector.countByDepartmentsName("Mathematics")).thenReturn(2);
        int results = lectorService.getCountLectorInDepartment("Mathematics");
        assertEquals(2, results);
    }

    @Test
    public void shouldGetGlobalSearch() {
        when(lectorConnector.searchByAllStringFields("jo")).thenReturn(List.of("John Smith", "Jane Smith"));
        List<String> results = lectorService.globalSearch("jo");
        assertEquals(2, results.size());
    }

    public LectorDTO createLector(Long id, String fullName, String degree, int salary) {
        LectorDTO lector = new LectorDTO();
        lector.setId(id);
        lector.setFullName(fullName);
        lector.setDegree(degree);
        lector.setSalary(salary);
        return lector;
    }
}
