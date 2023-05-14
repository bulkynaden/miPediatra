package es.mdef.mipediatra.models.vaccine;

import es.mdef.mipediatra.models.Model;
import es.mdef.mipediatra.models.file.FileModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Relation(itemRelation = "vaccinesPersons")
public class VaccinesPersonsModel extends Model {
    private String name;
    private LocalDate date;
    private LocalDate expectedDate;
    private FileModel photo;
    private String reaction;
    private boolean hasBeenAdministered;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
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
