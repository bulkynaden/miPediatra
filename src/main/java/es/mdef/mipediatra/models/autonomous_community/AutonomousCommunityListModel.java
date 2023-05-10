package es.mdef.mipediatra.models.autonomous_community;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "autonomous_communities")
public class AutonomousCommunityListModel extends RepresentationModel<AutonomousCommunityListModel> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
