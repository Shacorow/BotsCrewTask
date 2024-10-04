package bots.crew.university.bom;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Department extends AbstractObject {

    public String name;

    public String headOfDepartment;

    public List<Lector> lectors = new ArrayList<>();
}
