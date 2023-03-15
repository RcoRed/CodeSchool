package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "course_edition")
public class CourseEdition {
    @Id
    @GeneratedValue(generator = "course_edition_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "course_edition_generator", sequenceName = "course_edition_sequence", allocationSize = 1)
    @Column(name = "id_course_edition")   //nome colonna lato DB
    private long id;
    @ManyToOne(fetch = FetchType.EAGER) //FetchType.LAZY dice non ti disturbare a caricare, EAGER il contrario
    @JoinColumn(name = "id_course")
    private Course course;
    @Column(name = "started_at")
    private LocalDate startedAt;
    @Column(name = "price")
    private double cost;
    @ManyToOne      //di default é eager
    @JoinColumn(name = "id_classroom")
    private Classroom assignedClassRoom;
    @OneToMany(mappedBy = "edition")
    private List<EditionModule> modules;

    public CourseEdition(long id, Course course, LocalDate startedAt, double cost){
        this(id, course, startedAt, cost,null, null);
    }

    public CourseEdition(long id, Course course, LocalDate startedAt, double cost, Classroom assignedClassRoom) {
        this(id, course, startedAt, cost, assignedClassRoom, null);
    }

    public CourseEdition(long id, Course course, LocalDate startedAt, double cost, Classroom assignedClassRoom,
                         List<EditionModule> modules) {
        this.id=id;
        this.course=course;
        this.startedAt=startedAt;
        this.cost=cost;
        this.assignedClassRoom = assignedClassRoom;
        this.modules = modules;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setAssignedClassRoom(Classroom assignedClassRoom) {
        this.assignedClassRoom = assignedClassRoom;
    }

    public long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public double getCost() {
        return cost;
    }

    public Classroom getAssignedClassRoom() {
        return assignedClassRoom;
    }
    public boolean isStartedInRange(LocalDate start, LocalDate end){
        return !(getStartedAt().isBefore(start) || getStartedAt().isAfter(end));
    }

    public int addModule (EditionModule e ) {
        this.modules.add(e);
        return modules.size();
    }

}
