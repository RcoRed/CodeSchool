package org.generation.italy.codeSchool.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "classroom")
public class Classroom {
    @Id
    @GeneratedValue(generator = "classroom_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "classroom_generator", sequenceName = "classroom_sequence", allocationSize = 1)
    @Column(name = "id_category")
    private long id;

    @Column(name = "class_name")
    private String name;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @Column(name = "is_virtual")
    private boolean isVirtual;

    @Column(name = "is_computerized")
    private boolean isComputerized;

    @Column(name = "has_projector")
    private boolean hasProjector;

    @ManyToOne
    @JoinColumn(name = "id_remote_platform")
    private RemotePlatform platform;

    public Classroom(){}

    public Classroom(long id, String name, int maxCapacity, boolean isVirtual, boolean isComputerized, boolean hasProjector, RemotePlatform platform) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.isVirtual = isVirtual;
        this.isComputerized = isComputerized;
        this.hasProjector = hasProjector;
        this.platform = platform;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean virtual) {
        isVirtual = virtual;
    }

    public boolean isComputerized() {
        return isComputerized;
    }

    public void setComputerized(boolean computerized) {
        isComputerized = computerized;
    }

    public boolean isHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    public RemotePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(RemotePlatform platform) {
        this.platform = platform;
    }
}
