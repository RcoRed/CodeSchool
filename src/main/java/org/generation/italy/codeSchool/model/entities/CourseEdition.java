package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class CourseEdition {
    private long id;
    private Course course;
    private LocalDate startedAt;
    private double cost;
    private Classroom assignedClassRoom;

    public CourseEdition(long id, Course course, LocalDate startedAt, double cost){
        this.id=id;
        this.course=course;
        this.startedAt=startedAt;
        this.cost=cost;
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

}
