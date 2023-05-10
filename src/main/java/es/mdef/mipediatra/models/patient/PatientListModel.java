package es.mdef.mipediatra.models.patient;

import entities.File;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "patients")
public class PatientListModel extends RepresentationModel<PatientListModel> {
    private String comments;
    private File photo;

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
}
