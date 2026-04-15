package ru.kafpin.kafedraais.models;

public class ResearchArea {
    private int id;
    private String areaName;
    private String description;

    public ResearchArea() {}

    public ResearchArea(int id, String areaName, String description) {
        this.id = id;
        this.areaName = areaName;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return areaName;
    }
}
