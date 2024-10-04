package bots.crew.university.convertors;

import bots.crew.university.bom.Department;
import bots.crew.university.bom.Lector;
import bots.crew.university.dto.DepartmentDTO;
import bots.crew.university.dto.LectorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentConvertor {

    private final LectorConvertor lectorConvertor = new LectorConvertor();

    public void toDTO(Department source, DepartmentDTO destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        LectorDTO lectorDTO = new LectorDTO();
        lectorConvertor.toDTO(source.getHeadOfDepartment(), lectorDTO);
        destination.setHeadOfDepartment(lectorDTO);
    }

    public void fromDTO(DepartmentDTO source, Department destination) {
        destination.setId(source.getId());
        destination.setName(source.getName());
        Lector lector = new Lector();
        lectorConvertor.fromDTO(source.getHeadOfDepartment(), lector);
        destination.setHeadOfDepartment(lector);
    }
}
