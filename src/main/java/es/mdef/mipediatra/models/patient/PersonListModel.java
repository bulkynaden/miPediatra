package es.mdef.mipediatra.models.patient;

import es.mdef.mipediatralib.constants.Gender;
import org.springframework.hateoas.server.core.Relation;

import java.time.ZonedDateTime;

@Relation(collectionRelation = "persons")
public class PersonListModel extends PatientListModel {
    private String name;
    private String lastName;
    private Gender gender;
    private ZonedDateTime birthdate;

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

    public ZonedDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(ZonedDateTime birthdate) {
        this.birthdate = birthdate;
    }
}
