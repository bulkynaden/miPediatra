package es.mdef.mipediatra.models.patient;

import es.mdef.mipediatra.models.Model;
import es.mdef.mipediatra.models.file.FileModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "patient")
public class PatientModel extends Model {
    private String comments;
    private Model photo;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Model getPhoto() {
        return photo;
    }

    public void setPhoto(FileModel photo) {
        this.photo = photo;
    }
}
