package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;
import java.util.Comparator;

public class CourseEdition {
    private long id;
    private Course course;
    private LocalDate startedAt;
    private double cost;
    private Classroom assignedClassroom;

    public CourseEdition(long id, Course course, double cost) {
        this.id = id;
        this.course = course;
        this.startedAt = LocalDate.now();
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Classroom getAssignedClassroom() {
        return assignedClassroom;
    }

    public void setAssignedClassroom(Classroom assignedClassroom) {
        this.assignedClassroom = assignedClassroom;
    }

    public boolean startedInRange(LocalDate start, LocalDate end){
        return !(getStartedAt().isBefore(start) || getStartedAt().isAfter(end));
    }
}
