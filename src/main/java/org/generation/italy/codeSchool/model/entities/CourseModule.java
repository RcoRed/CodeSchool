package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "course_module")
public class CourseModule {
    @Id
    @GeneratedValue(generator = "course_module_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "course_module_generator", sequenceName = "course_module_sequence", allocationSize = 1)
    @Column(name = "id_course_module")
    private long id;
    @Column (name = "title")
    private String title;
    @Column (name = "cm_content")
    private String contents;
    @ManyToOne
    @JoinColumn (name = "id_course")
    private Course course;
    //@Column (name = "duration") <- non serve perché hanno lo stesso nome sia in Java che nel DB
    private double duration;
    @Enumerated (EnumType.STRING) // qui indichiamo ad Hibernate che vogliamo che venga salvato il valore della stringa dell'enumerazione e non il valore "numerico"
    private Level level;
}
