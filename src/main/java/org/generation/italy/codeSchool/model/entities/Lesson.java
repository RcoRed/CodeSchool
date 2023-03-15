package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(generator = "lesson_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "lesson_generator", sequenceName = "lesson_sequence", allocationSize = 1)
    @Column(name = "id_lesson")
    private long id;
    private String title;
    @Column (name = "start_date")
    private LocalDate startDate;
    @Column (name = "end_date")
    private LocalDate endDate;
    private String content;
    @ManyToOne
    @JoinColumn (name = "id_classroom")
    private Classroom classRoom;
    @ManyToOne
    @JoinColumn (name = "id_teacher")
    private Teacher teacher;

    public Lesson() {

    }

    public Lesson(long id, String title, LocalDate startDate, LocalDate endDate, String content, Classroom classRoom, Teacher teacher) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.classRoom = classRoom;
        this.teacher = teacher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getContent() {
        return content;
    }

    public Classroom getClassRoom() {
        return classRoom;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
