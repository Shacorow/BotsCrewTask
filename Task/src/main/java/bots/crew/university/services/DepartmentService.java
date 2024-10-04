package bots.crew.university.services;

import bots.crew.university.bom.Degree;
import bots.crew.university.bom.Department;
import bots.crew.university.bom.Lector;
import bots.crew.university.bom.StatisticDepartment;
import bots.crew.university.connectors.DepartmentConnector;
import bots.crew.university.convertors.DepartmentConvertor;
import bots.crew.university.dto.DepartmentDTO;
import bots.crew.university.dto.LectorDTO;
import bots.crew.university.exception.DepartmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentConnector departmentConnector;

    private final DepartmentConvertor departmentConvertor;

    private final LectorService lectorService;

    public Department create(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentConvertor.toDTO(department, departmentDTO);
        departmentDTO = departmentConnector.save(departmentDTO);
        departmentConvertor.fromDTO(departmentDTO, department);
        return department;
    }

    public String getHeadOfDepartment(String name) {
        DepartmentDTO departmentDTO = departmentConnector.getByName(name);
        if (departmentDTO == null) {
            throw new DepartmentNotFoundException(name);
        }
        Department department = new Department();
        departmentConvertor.fromDTO(departmentDTO, department);
        return department.getHeadOfDepartment().getFullName();
    }

    public StatisticDepartment getStatistic(String name) {
        List<Lector> lectors = lectorService.findByDepartmentName(name);
        StatisticDepartment statisticDepartment = new StatisticDepartment();
        for (Lector lector : lectors) {
            if (lector.getDegree().equals(Degree.ASSISTANT)) {
                statisticDepartment.assistans++;
            } else if (lector.getDegree().equals(Degree.PROFESSOR)) {
                statisticDepartment.professors++;
            } else {
                statisticDepartment.associateProfessors++;
            }
        }
        return statisticDepartment;
    }

    public double getAverageSalary(String name) {
        List<Lector> lectors = lectorService.findByDepartmentName(name);
        DepartmentDTO departmentDTO = departmentConnector.getByName(name);
        if(departmentDTO == null){
            departmentDTO = new DepartmentDTO();
        }
        LectorDTO headOfDepartment = departmentDTO.getHeadOfDepartment();
        double sum = 0;
        List<Long> salaryWrited = new ArrayList<>();
        for (Lector lector : lectors) {
            sum += lector.getSalary();
            salaryWrited.add(lector.getId());
        }
        if (headOfDepartment != null && salaryWrited.contains(headOfDepartment.getId())) {
            return sum / lectors.size();
        } else {
            sum += headOfDepartment.getSalary();
            return sum / lectors.size() + 1;
        }
    }

    public List<String> globalSearch(String searchTerm) {
        return departmentConnector.searchByAllStringFields(searchTerm);
    }
}
