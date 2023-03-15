package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "edition_module")
public class EditionModule {
    @Id
    @GeneratedValue(generator = "edition_module_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "edition_module_generator", sequenceName = "edition_module_sequence", allocationSize = 1)
    @Column(name = "id_edition_module")   //nome colonna lato DB
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_course_module")
    private CourseModule courseModule;
    @ManyToOne
    @JoinColumn(name = "id_teacher")
    private Teacher teacher;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "id_course_edition")
    private CourseEdition edition;



    public EditionModule(long id, CourseModule courseModule, Teacher teacher, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.courseModule = courseModule;
        this.teacher = teacher;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public CourseModule getCourseModule() {
        return courseModule;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setId(long id) {
        this.id = id;
    }
    public CourseEdition getEdition() {
        return edition;
    }
    public void setEdition(CourseEdition edition) {
        this.edition = edition;
    }
}
