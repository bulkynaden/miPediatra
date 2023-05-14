package es.mdef.mipediatra.models.vaccine;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "vaccineDetails")
public class VaccineDetailsModel extends RepresentationModel<VaccineDetailsModel> {
    private String name;
    private String shortName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}