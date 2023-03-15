package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "course_module")
public class CourseModule {
    @Id
    @GeneratedValue(generator = "course_module_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "course_module_generator", sequenceName = "course_module_sequence", allocationSize = 1)
    @Column(name= "id_course_module")
    private long id;
    private String title;
    @Column(name = "cm_content")
    private String contents;
    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;
    private double duration;
    @Enumerated(EnumType.STRING)
    private Level level;
    public CourseModule(){}

    public CourseModule(long id, String title, String contents, Course course, double duration, Level level) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.course = course;
        this.duration = duration;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Course getCourse() {
        return course;
    }

    public double getDuration() {
        return duration;
    }

    public Level getLevel() {
        return level;
    }

    public void setId(long id) {
        this.id = id;
    }
}
