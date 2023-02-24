package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class CourseEdition {
    private long id;
    private Course course;

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

    public ClassRoom getAssignedClassRoom() {
        return assignedClassRoom;
    }

    public void setAssignedClassRoom(ClassRoom assignedClassRoom) {
        this.assignedClassRoom = assignedClassRoom;
    }
    public boolean startedInRange(LocalDate start, LocalDate end){
        return !(getStartedAt().isBefore(start) || getStartedAt().isBefore(end));
    }
    private LocalDate startedAt;
    private double cost;
    private ClassRoom assignedClassRoom;
}
