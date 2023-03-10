package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class EditionModule {
    private long id;
    private CourseModule courseModule;
    private Teacher teacher;
    private LocalDate startDate;
    private LocalDate endDate;

    public EditionModule() {
    }

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

    public void setId(long id) {
        this.id = id;
    }

    public CourseModule getCourseModule() {
        return courseModule;
    }

    public void setCourseModule(CourseModule courseModule) {
        this.courseModule = courseModule;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
