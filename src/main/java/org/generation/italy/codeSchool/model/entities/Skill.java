package org.generation.italy.codeSchool.model.entities;

public class Skill {
    private long id;
    private String name;
    private String description;

    public Skill(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Skill(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
