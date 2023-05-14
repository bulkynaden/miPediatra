package es.mdef.mipediatra.models.vaccine;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "vaccinesDetails")
public class VaccineDetailsListModel extends RepresentationModel<VaccineDetailsListModel> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}