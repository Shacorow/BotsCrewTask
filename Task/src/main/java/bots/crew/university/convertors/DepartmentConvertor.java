package bots.crew.university.convertors;

import bots.crew.university.bom.Department;
import bots.crew.university.dto.DepartmentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentConvertor {
    
    public void toDTO(Department source, DepartmentDTO destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        destination.setHeadOfDepartment(source.getHeadOfDepartment());
    }

    public void fromDTO(DepartmentDTO source, Department destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        destination.setHeadOfDepartment(source.getHeadOfDepartment());
    }
}
