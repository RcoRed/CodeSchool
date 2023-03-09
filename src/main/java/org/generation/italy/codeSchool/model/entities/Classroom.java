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

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public boolean isComputerized() {
        return isComputerized;
    }

    public boolean isHasProjector() {
        return hasProjector;
    }

    public RemotePlatform getPlatform() {
        return platform;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setVirtual(boolean virtual) {
        isVirtual = virtual;
    }

    public void setComputerized(boolean computerized) {
        isComputerized = computerized;
    }

    public void setHasProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    public void setPlatform(RemotePlatform platform) {
        this.platform = platform;
    }
}
