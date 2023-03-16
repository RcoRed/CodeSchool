package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(generator = "lesson_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "lesson_generator", sequenceName = "lesson_sequence", allocationSize = 1)
    @Column(name = "id_lesson")
    private long id;
    private String title;
    private String content;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "id_classroom")
    private Classroom classRoom;
    @ManyToOne
    @JoinColumn(name = "id_teacher")
    private Teacher teacher;

    public Lesson(){}

    public Lesson(long id, String title, String content, LocalDate startDate, LocalDate endDate,
                  Classroom classRoom, Teacher teacher) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.classRoom = classRoom;
        this.teacher = teacher;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Classroom getClassRoom() {
        return classRoom;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setId(long id) {
        this.id = id;
    }
}
