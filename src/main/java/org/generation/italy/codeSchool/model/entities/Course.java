package org.generation.italy.codeSchool.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Course implements Serializable, Comparable<Course> {
    private long id;
    private String title;
    private String description;
    private String program;
    private double duration;
    //private static final long serialVersionUID = 1;
    private boolean isActive;
    private LocalDate createAt;
    public Course(){
        this.createAt=LocalDate.now();
    }
    public Course(long id, String title, String description, String program, double duration, LocalDate createAt) {
        this(id, title, description, program, duration, true, createAt);
    }
    public Course(long id, String title, String description, String program, double duration, boolean isActive ,LocalDate createAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.program = program;
        this.duration = duration;
        this.isActive=isActive;
        this.createAt=createAt;
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
    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {isActive = active;}
    public LocalDate getCreateAt() {return createAt;}

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


    @Override
    public int compareTo(Course o) { //ordinamento naturale della classe
        /*if(this.createAt.isBefore(o.createAt)){  //prima vecchie e poi più recenti
            return -1;
        }else if (this.createAt.isAfter(o.createAt)){
            return 1;
        }else {
            return 0;
        }*/
        return this.createAt.compareTo(o.createAt);  //se positivo va dal più vecchio al più nuovo
    }
}
