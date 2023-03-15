package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "course")    //annotazione di runtime,rimane nel bytecode
public class Course implements Serializable {
    @Id
    @GeneratedValue(generator = "course_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "course_generator", sequenceName = "course_sequence", allocationSize = 1)
    @Column(name = "id_course")   //nome colonna lato DB
    private long id;
    private String title;
    private String description;
    private String program;
    private double duration;
    //private static final long serialVersionUID = 1;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @OneToMany (mappedBy = "course", //specifica che la mappatura da utilizzare si trova nell'attributo corrispondente nell'altra classe
    fetch = FetchType.EAGER)
    private List<CourseEdition> editions;

    public Course() {
        this.createdAt = LocalDate.now();
    }
    public Course(long id, String title, String description, String program, double duration, LocalDate createdAt) {
        this(id, title, description, program, duration, true, createdAt);
    }

    public Course(long id, String title, String description, String program, double duration,boolean isActive, LocalDate createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.program = program;
        this.duration = duration;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public List<CourseEdition> getEditions() {
        return editions;
    }

    public void setEditions(List<CourseEdition> editions) {
        this.editions = editions;
    }

    public String getDescription() {
        return description;
    }

    public String getProgram() {
        return program;
    }

    public double getDuration() {
        return duration;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return isActive;
    }


    public void setActive(boolean active) {
        isActive = active;
    }
    public boolean deactivate(){
        boolean wasActive = isActive;
        isActive = false;
        return isActive!= wasActive;
    }

    //override del metodo madre Object toString() e lo facciamo meglio
    @Override
    public String toString() {
//        return "Course{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", program='" + program + '\'' +
//                ", duration=" + duration +
//                '}';
//        ritorna la stessa cosa, anzi è fatta meglio
        return String.format("Course{id=%d, title=%s, description=%s, program=%s, duration=%f, isActive = %b, createdAt = %s}",
                id,title,description,program,duration, isActive, createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Course course = (Course) o;     //DownCast
        return getId() == course.getId() && Double.compare(course.getDuration(), getDuration()) == 0 && getTitle().equals(course.getTitle()) && Objects.equals(getDescription(), course.getDescription()) && Objects.equals(getProgram(), course.getProgram());
    }
    //!! equals e hashCode devono stare sempre insieme (sono bff)
    @Override
    public int hashCode() {
        return Objects.hash(getId());       //modifica l'hashCode in base all'equals
    }
}
