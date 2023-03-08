package org.generation.italy.codeSchool.model.entities;

public class Classroom {
    private long id;
    private String name;
    private int maxCapacity;
    private boolean isVirtual;
    private boolean isComputerized;
    private boolean hasProjector;
    private RemotePlatform platform;

    public Classroom(long id, String name, int maxCapacity, boolean isVirtual, boolean isComputerized, boolean hasProjector, RemotePlatform platform) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.isVirtual = isVirtual;
        this.isComputerized = isComputerized;
        this.hasProjector = hasProjector;
        this.platform = platform;
    }
}
