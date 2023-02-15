package org.generation.italy.codeSchool.model;

public class Course {
    private long id;
    private String title;
    private String description;
    private String program;
    private double duration;

    public Course(){

    }
    public Course(long id, String title, String description, String program, double duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.program = program;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getProgram() {
        return program;
    }

    public double getDuration() {
        return duration;
    }

    //override del metodo madre Object toString() e lo facciamo meglio
    @Override
    public String toString() {
//        return "Course{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", program='" + program + '\'' +
//                ", duration=" + duration +
//                '}';
//        ritorna la stessa cosa, anzi è fatta meglio
        return String.format("Course{id=%d, title=%s, description=%s, program=%s, duration=%f}",
                id,title,description,program,duration);
    }
}
