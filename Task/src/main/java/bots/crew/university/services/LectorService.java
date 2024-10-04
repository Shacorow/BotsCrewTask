package bots.crew.university.services;

import bots.crew.university.bom.Lector;
import bots.crew.university.connectors.LectorConnector;
import bots.crew.university.convertors.LectorConvertor;
import bots.crew.university.dto.LectorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectorService {

    private final LectorConnector lectorConnector;

    private final LectorConvertor lectorConvertor;

    public Lector create(Lector lector) {
        LectorDTO lectorDTO = new LectorDTO();
        lectorConvertor.toDTO(lector, lectorDTO);
        LectorDTO lectorDTOSaved = lectorConnector.save(lectorDTO);
        lectorConvertor.fromDTO(lectorDTOSaved, lector);
        return lector;
    }

    public List<Lector> findByDepartmentName(String name) {
        List<LectorDTO> lectorDTOList = lectorConnector.getByDepartmentsName(name);
        List<Lector> lectors = new ArrayList<>();
        for (LectorDTO lectorDTO : lectorDTOList) {
            Lector lector = new Lector();
            lectorConvertor.fromDTO(lectorDTO, lector);
            lectors.add(lector);
        }
        return lectors;
    }

    public int getCountLectorInDepartment(String name) {
        return lectorConnector.countByDepartmentsName(name);
    }

    public List<String> globalSearch(String searchTerm) {
        return lectorConnector.searchByAllStringFields(searchTerm);
    }
}
