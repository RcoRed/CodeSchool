package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(generator = "attendance_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "attendance_generator", sequenceName = "attendance_sequence", allocationSize = 1)
    @Column(name = "id_attendance")   //nome colonna lato DB
    private long id;
    private Lesson lesson;
    private Student student;
    private LocalDate startDate;
    private LocalDate endDate;

    private String comment;
}
