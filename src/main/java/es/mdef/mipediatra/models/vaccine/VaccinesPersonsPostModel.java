package es.mdef.mipediatra.models.vaccine;

import es.mdef.mipediatra.models.PostModel;
import es.mdef.mipediatralib.entities.File;
import es.mdef.mipediatralib.entities.Person;
import es.mdef.mipediatralib.entities.Vaccine;
import org.springframework.hateoas.server.core.Relation;

import java.time.ZonedDateTime;

@Relation(itemRelation = "vaccinesPersons")
public class VaccinesPersonsPostModel extends PostModel {
    private Vaccine vaccine;
    private Person person;
    private ZonedDateTime date;
    private ZonedDateTime expectedDate;
    private File photo;
    private String reaction;
    private boolean hasBeenAdministered;

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(ZonedDateTime expectedDate) {
        this.expectedDate = expectedDate;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public boolean isHasBeenAdministered() {
        return hasBeenAdministered;
    }

    public void setHasBeenAdministered(boolean hasBeenAdministered) {
        this.hasBeenAdministered = hasBeenAdministered;
    }
}
