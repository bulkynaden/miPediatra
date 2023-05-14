package es.mdef.mipediatra.models.patient;

import es.mdef.mipediatra.constants.PatientType;
import es.mdef.mipediatra.models.PutModel;
import es.mdef.mipediatralib.constants.BloodType;
import es.mdef.mipediatralib.constants.Gender;
import es.mdef.mipediatralib.entities.AutonomousCommunity;
import es.mdef.mipediatralib.entities.File;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(itemRelation = "patient")
public class PatientPutModel extends PutModel {
    private String comments;
    private File photo;
    private PatientType type;
    private String name;
    private String lastName;
    private Gender gender;
    private double birthWeight;
    private AutonomousCommunity autonomousCommunity;
    private BloodType bloodType;
    private LocalDateTime birthdate;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public PatientType getType() {
        return type;
    }

    public void setType(PatientType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getBirthWeight() {
        return birthWeight;
    }

    public void setBirthWeight(double birthWeight) {
        this.birthWeight = birthWeight;
    }

    public AutonomousCommunity getAutonomousCommunity() {
        return autonomousCommunity;
    }

    public void setAutonomousCommunity(AutonomousCommunity autonomousCommunity) {
        this.autonomousCommunity = autonomousCommunity;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "PatientPutModel{" +
                "comments='" + comments + '\'' +
                ", photo=" + photo +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", birthWeight=" + birthWeight +
                ", autonomousCommunity=" + autonomousCommunity +
                ", bloodType=" + bloodType +
                ", birthdate=" + birthdate +
                '}';
    }
}