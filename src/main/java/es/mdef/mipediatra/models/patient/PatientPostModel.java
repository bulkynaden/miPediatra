package es.mdef.mipediatra.models.patient;

import constants.BloodType;
import constants.Gender;
import entities.AutonomousCommunity;
import entities.File;
import es.mdef.mipediatra.constants.PatientType;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(itemRelation = "patient")
public class PatientPostModel extends RepresentationModel<PatientPostModel> {
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
        return "PatientPostModel{" +
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