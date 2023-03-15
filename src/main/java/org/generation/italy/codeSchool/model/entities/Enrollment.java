package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(generator = "course_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "enrollment_generator", sequenceName = "enrollment_sequence", allocationSize = 1)
    @Column(name = "id_enrollment")
    private long id;

    @Column(name = "start_date")
    private LocalDate enrollmentDate;

    @Column(name = "dropout_date")
    private LocalDate dropoutDate;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id_course_edition")
    private CourseEdition courseEdition;

    @Column(name = "course_evaluation")
    private String courseEvaluation;

    @Column(name = "course_vote")
    private int courseVote;

    @Column(name = "student_evaluation")
    private String studentEvaluation;

    @Column(name = "student_vote")
    private double studentVote;

    @Column(name = "has_paid")
    private boolean hasPaid;

    public Enrollment() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDate getDropoutDate() {
        return dropoutDate;
    }

    public void setDropoutDate(LocalDate dropoutDate) {
        this.dropoutDate = dropoutDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CourseEdition getCourseEdition() {
        return courseEdition;
    }

    public void setCourseEdition(CourseEdition courseEdition) {
        this.courseEdition = courseEdition;
    }

    public String getCourseEvaluation() {
        return courseEvaluation;
    }

    public void setCourseEvaluation(String courseEvaluation) {
        this.courseEvaluation = courseEvaluation;
    }

    public int getCourseVote() {
        return courseVote;
    }

    public void setCourseVote(int courseVote) {
        this.courseVote = courseVote;
    }

    public String getStudentEvaluation() {
        return studentEvaluation;
    }

    public void setStudentEvaluation(String studentEvaluation) {
        this.studentEvaluation = studentEvaluation;
    }

    public double getStudentVote() {
        return studentVote;
    }

    public void setStudentVote(double studentVote) {
        this.studentVote = studentVote;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }
}

