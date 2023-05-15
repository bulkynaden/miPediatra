package es.mdef.mipediatra.models.vaccine;

import es.mdef.mipediatra.models.ListModel;
import es.mdef.mipediatra.models.file.FileModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.ZonedDateTime;

@Relation(collectionRelation = "vaccinesPersons")

public class VaccinesPersonsListModel extends ListModel {
    private String name;
    private ZonedDateTime date;
    private ZonedDateTime expectedDate;
    private FileModel photo;
    private String reaction;
    private boolean hasBeenAdministered;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public FileModel getPhoto() {
        return photo;
    }

    public void setPhoto(FileModel photo) {
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
