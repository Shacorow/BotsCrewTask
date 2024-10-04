package bots.crew.university.bom;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Lector extends AbstractObject{

    public Degree degree;

    public String fullName;

    public double salary;

    public List<Department> departments = new ArrayList<>();
}
