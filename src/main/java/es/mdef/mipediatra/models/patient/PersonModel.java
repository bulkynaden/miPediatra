package es.mdef.mipediatra.models.patient;

import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityModel;
import es.mdef.mipediatralib.constants.BloodType;
import es.mdef.mipediatralib.constants.Gender;
import org.springframework.hateoas.server.core.Relation;

import java.time.ZonedDateTime;

@Relation(itemRelation = "person")
public class PersonModel extends PatientModel {
    private String name;
    private String lastName;
    private Gender gender;
    private double birthWeight;
    private BloodType bloodType;
    private ZonedDateTime birthdate;
    private AutonomousCommunityModel autonomousCommunity;

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

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public ZonedDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(ZonedDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public AutonomousCommunityModel getAutonomousCommunity() {
        return autonomousCommunity;
    }

    public void setAutonomousCommunity(AutonomousCommunityModel autonomousCommunity) {
        this.autonomousCommunity = autonomousCommunity;
    }
}
