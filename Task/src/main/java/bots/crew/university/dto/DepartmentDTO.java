package bots.crew.university.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "DEPARTMENT")
public class DepartmentDTO {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_generator")
    @SequenceGenerator(name = "department_id_generator", sequenceName = "DEPARTMENT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HEAD_OF_DEPARTMENT")
    private String headOfDepartment;

    @ManyToMany(mappedBy = "departments")
    private List<LectorDTO> lectors = new ArrayList<>();
}
