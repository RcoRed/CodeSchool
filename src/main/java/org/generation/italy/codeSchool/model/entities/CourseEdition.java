package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;
import java.util.List;

public class CourseEdition {
    private long id;
    private Course course;
    private LocalDate startedAt;
    private double cost;
    private Classroom assignedClassRoom;
    private List<EditionModule> editionModules;

    public CourseEdition(long id, Course course, LocalDate startedAt, double cost){
        this.id=id;
        this.course=course;
        this.startedAt=startedAt;
        this.cost=cost;
    }

    public CourseEdition(long id, Course course, LocalDate startedAt, double cost, Classroom assignedClassRoom){
        this.id=id;
        this.course=course;
        this.startedAt=startedAt;
        this.cost=cost;
        this.assignedClassRoom =assignedClassRoom;
    }


    public CourseEdition(long id, Course course, LocalDate startedAt, double cost, List<EditionModule> editionModules){
        this.id=id;
        this.course=course;
        this.startedAt=startedAt;
        this.cost=cost;
        this.editionModules = editionModules;
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


}
