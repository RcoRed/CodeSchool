package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(generator = "enrollment_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "enrollment_generator", sequenceName = "enrollment_sequence", allocationSize = 1)
    @Column(name = "id_enrollment")
    private long id;
    @Column (name = "start_date")
    private LocalDate enrollmentDate;
    @Column (name = "dropout_date")
    private LocalDate dropoutDate;
    @Column (name = "is_student")
    private Student student;
    @ManyToOne
    @JoinColumn (name = "id_course_edition")
    private CourseEdition courseEdition;
    @Column (name = "course_evaluation")
    private String courseEvaluation;
    @Column (name = "course_vote")
    private int courseVote;
    @Column (name = "student_evaluation")
    private String studentEvaluation;
    @Column (name = "student_vote")
    private double studentVote;
    @Column (name = "has_paid")
    private boolean hasPaid;

    public Enrollment() {

    }

    public Enrollment(long id, LocalDate enrollmentDate, LocalDate dropoutDate, Student student, CourseEdition courseEdition, String courseEvaluation, int courseVote, String studentEvaluation, double studentVote, boolean hasPaid) {
        this.id = id;
        this.enrollmentDate = enrollmentDate;
        this.dropoutDate = dropoutDate;
        this.student = student;
        this.courseEdition = courseEdition;
        this.courseEvaluation = courseEvaluation;
        this.courseVote = courseVote;
        this.studentEvaluation = studentEvaluation;
        this.studentVote = studentVote;
        this.hasPaid = hasPaid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public LocalDate getDropoutDate() {
        return dropoutDate;
    }

    public Student getStudent() {
        return student;
    }

    public CourseEdition getCourseEdition() {
        return courseEdition;
    }

    public String getCourseEvaluation() {
        return courseEvaluation;
    }

    public int getCourseVote() {
        return courseVote;
    }

    public String getStudentEvaluation() {
        return studentEvaluation;
    }

    public double getStudentVote() {
        return studentVote;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }
}

