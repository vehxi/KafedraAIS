package ru.kafpin.kafedraais.models;

import java.sql.Timestamp;

public class Material {
    private int id;
    private String materialName;
    private byte[] fileData;
    private String fileExtension;
    private Timestamp uploadDate;
    private Integer courseId;

    public Material() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaterialName() { return materialName; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }

    public byte[] getFileData() { return fileData; }
    public void setFileData(byte[] fileData) { this.fileData = fileData; }

    public String getFileExtension() { return fileExtension; }
    public void setFileExtension(String fileExtension) { this.fileExtension = fileExtension; }

    public Timestamp getUploadDate() { return uploadDate; }
    public void setUploadDate(Timestamp uploadDate) { this.uploadDate = uploadDate; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    @Override
    public String toString() {
        return materialName + " (" + fileExtension + ")";
    }
}
