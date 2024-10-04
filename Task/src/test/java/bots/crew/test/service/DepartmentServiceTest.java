package bots.crew.test.service;

import bots.crew.test.UniversityUnitTest;
import bots.crew.university.bom.Degree;
import bots.crew.university.bom.Department;
import bots.crew.university.bom.Lector;
import bots.crew.university.bom.StatisticDepartment;
import bots.crew.university.connectors.DepartmentConnector;
import bots.crew.university.convertors.DepartmentConvertor;
import bots.crew.university.dto.DepartmentDTO;
import bots.crew.university.dto.LectorDTO;
import bots.crew.university.services.DepartmentService;
import bots.crew.university.services.LectorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest extends UniversityUnitTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentConnector departmentConnector;
    @Mock
    private LectorService lectorService;

    @Spy
    private DepartmentConvertor departmentConvertor;

    @Test
    public void shouldCreateDepartment() {
        Department department = new Department();
        department.setName("Mathematics");
        department.setHeadOfDepartment("John Smith");
        DepartmentDTO departmentDTO = createDepartment(1L, "Mathematics", "John Smith");

        when(departmentConnector.save(any())).thenReturn(departmentDTO);
        Department result = departmentService.create(department);
        assertEquals(1L, result.getId());
        assertEquals(department.getName(), result.getName());
        assertEquals(department.getHeadOfDepartment(), result.getHeadOfDepartment());
    }

    @Test
    public void shouldGetHeadOfDepartment() {
        DepartmentDTO departmentDTO = createDepartment(1L, "Mathematics", "John Smith");

        when(departmentConnector.getByName("Mathematics")).thenReturn(departmentDTO);
        String result = departmentService.getHeadOfDepartment("Mathematics");

        assertEquals("John Smith", result);
    }

    @Test
    public void shouldGetStatistic() {
        Lector lectorFirst = createLector(1L, "John Smith", Degree.PROFESSOR, 4000);
        Lector lectorSecond = createLector(2L, "Jane Smith", Degree.ASSISTANT, 1000);
        List<Lector> lectorDTOList = List.of(lectorFirst, lectorSecond);

        when(lectorService.findByDepartmentName("Mathematics")).thenReturn(lectorDTOList);
        StatisticDepartment result = departmentService.getStatistic("Mathematics");
        assertEquals(1, result.assistans);
        assertEquals(1, result.professors);
        assertEquals(0, result.associateProfessors);
    }

    @Test
    public void shouldGetAverageSalary() {
        Lector lectorFirst = createLector(1L, "John Smith", Degree.PROFESSOR, 4000);
        Lector lectorSecond = createLector(2L, "Jane Smith", Degree.ASSISTANT, 1000);
        Lector lectorThird = createLector(3L, "Jordan Smith", Degree.ASSOCIATE_PROFESSOR, 2362);
        List<Lector> lectorDTOList = List.of(lectorFirst, lectorSecond, lectorThird);
        when(lectorService.findByDepartmentName("Mathematics")).thenReturn(lectorDTOList);
        double result = departmentService.getAverageSalary("Mathematics");
        assertEquals(2454, result);
    }

    @Test
    public void shouldGetGlobalSearch() {
        when(departmentConnector.searchByAllStringFields("jo")).thenReturn(List.of("John Smith", "Jane Smith"));
        List<String> results = departmentService.globalSearch("jo");
        assertEquals(2, results.size());
    }


    public Lector createLector(Long id, String fullName, Degree degree, int salary) {
        Lector lector = new Lector();
        lector.setId(id);
        lector.setFullName(fullName);
        lector.setDegree(degree);
        lector.setSalary(salary);
        return lector;
    }

    public DepartmentDTO createDepartment(Long id, String name, String headOfDepartment) {
        DepartmentDTO department = new DepartmentDTO();
        department.setHeadOfDepartment(headOfDepartment);
        department.setName(name);
        department.setId(id);
        return department;
    }
}
