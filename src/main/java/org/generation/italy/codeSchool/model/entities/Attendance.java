package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(generator = "attendance_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "attendance_generator", sequenceName = "attendance_sequence", allocationSize = 1)
    @Column(name = "id_attendance")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_lesson")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "id_person")
    private Student student;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
    private String comment;

    public Attendance(){}

    public Attendance(long id, Lesson lesson, Student student, LocalDate startDate, LocalDate endDate, String comment) {
        this.id = id;
        this.lesson = lesson;
        this.student = student;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}