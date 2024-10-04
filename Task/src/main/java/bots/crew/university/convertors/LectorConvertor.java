package bots.crew.university.convertors;

import bots.crew.university.bom.Degree;
import bots.crew.university.bom.Lector;
import bots.crew.university.dto.LectorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectorConvertor {

    public void toDTO(Lector source, LectorDTO destination) {
        destination.setId(source.getId());
        destination.setDegree(source.getDegree().name());
        destination.setFullName(source.getFullName());
        destination.setSalary(source.getSalary());
    }

    public void fromDTO(LectorDTO source, Lector destination) {
        destination.setId(source.getId());
        destination.setDegree(Degree.valueOf(source.getDegree()));
        destination.setFullName(source.getFullName());
        destination.setSalary(source.getSalary());
    }
}
