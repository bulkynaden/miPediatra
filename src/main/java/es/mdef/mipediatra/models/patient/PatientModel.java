package es.mdef.mipediatra.models.patient;


import es.mdef.mipediatra.models.file.FileModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "patient")
public class PatientModel extends RepresentationModel<PatientModel> {
    private String comments;
    private FileModel photo;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public FileModel getPhoto() {
        return photo;
    }

    public void setPhoto(FileModel photo) {
        this.photo = photo;
    }
}
