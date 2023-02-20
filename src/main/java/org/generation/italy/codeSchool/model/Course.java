package org.generation.italy.codeSchool.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Course implements Serializable {
    private long id;
    private String title;
    private String description;
    private String program;
    private double duration;
    //private static final long serialVersionUID = 1;
    private boolean isActive;
    private LocalDate createdAt;


    public Course(){
        this.createdAt = LocalDate.now();
    }
    public Course(long id, String title, String description, String program, double duration, LocalDate createdAt) {
        this(id,title,description,program,duration,true,createdAt);

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

    public String getDescription() {
        return description;
    }

    public String getProgram() {
        return program;
    }

    public double getDuration() {
        return duration;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
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
        return String.format("Course{id=%d, title=%s, description=%s, program=%s, duration=%f}",
                id,title,description,program,duration);
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
