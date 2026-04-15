package ru.kafpin.kafedraais.models;

public class Teacher {
    private int id;
    private String fullName;
    private String position;
    private String academicDegree;
    private String scientificInterests;
    private String consultationSchedule;
    private byte[] photo;
    private String email;
    private Integer sectionId;

    public Teacher() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getAcademicDegree() { return academicDegree; }
    public void setAcademicDegree(String academicDegree) { this.academicDegree = academicDegree; }

    public String getScientificInterests() { return scientificInterests; }
    public void setScientificInterests(String scientificInterests) { this.scientificInterests = scientificInterests; }

    public String getConsultationSchedule() { return consultationSchedule; }
    public void setConsultationSchedule(String consultationSchedule) { this.consultationSchedule = consultationSchedule; }

    public byte[] getPhoto() { return photo; }
    public void setPhoto(byte[] photo) { this.photo = photo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getSectionId() { return sectionId; }
    public void setSectionId(Integer sectionId) { this.sectionId = sectionId; }

    @Override
    public String toString() {
        return fullName + " (" + position + ")";
    }
}
