package org.generation.italy.codeSchool.model.entities;

public class Competence {
    private long id;
    private Skill skill;
    private Person person;
    private Level level;

    public Competence(long id, Skill skill, Person person, Level level) {
        this.id = id;
        this.skill = skill;
        this.person = person;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public Skill getSkill() {
        return skill;
    }

    public Person getPerson() {
        return person;
    }

    public Level getLevel() {
        return level;
    }

    public void setId(long id) {
        this.id = id;
    }
}
