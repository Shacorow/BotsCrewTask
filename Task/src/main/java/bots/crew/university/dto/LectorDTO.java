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
@Entity(name = "LECTOR")
public class LectorDTO {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lector_id_generator")
    @SequenceGenerator(name = "lector_id_generator", sequenceName = "LECTOR_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "DEGREE")
    private String degree;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "SALARY")
    private int salary;

    @ManyToMany
    @JoinTable(
            name = "DEPARTMENT_LECTOR",
            joinColumns = @JoinColumn(name = "LECTOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "DEPARTMENT_ID")
    )
    private List<DepartmentDTO> departments = new ArrayList<>();
}
