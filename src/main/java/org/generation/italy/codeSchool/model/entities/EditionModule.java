package org.generation.italy.codeSchool.model.entities;

import java.time.LocalDate;

public class EditionModule {
    private long id;
    private CourseModule courseModule;
    private Teacher teacher;
    private LocalDate startDate;
    private LocalDate endDate;

    public EditionModule(long id, CourseModule courseModule, Teacher teacher, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.courseModule = courseModule;
        this.teacher = teacher;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
