package es.mdef.mipediatra.models.autonomous_community;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "autonomous_community")
public class AutonomousCommunityModel extends RepresentationModel<AutonomousCommunityModel> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
