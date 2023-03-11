package org.generation.italy.codeSchool.model.entities;

public class CourseModule {
    private long id;
    private String title;
    private String contents;
    private Course course;
    private double duration;
    private Level level;

    public CourseModule(long id, String title, String contents, Course course, double duration, Level level) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.course = course;
        this.duration = duration;
        this.level = level;
    }
}
