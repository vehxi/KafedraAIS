package ru.kafpin.kafedraais.models;

public class Course {
    private int id;
    private String courseName;
    private String description;
    private Integer semester;
    private Integer teacherId;

    public Course() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public Integer getTeacherId() { return teacherId; }
    public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }

    @Override
    public String toString() {
        return courseName + " (" + semester + " семестр)";
    }
}
