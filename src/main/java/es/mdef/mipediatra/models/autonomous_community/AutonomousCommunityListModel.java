package es.mdef.mipediatra.models.autonomous_community;

import es.mdef.mipediatra.models.ListModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "autonomous_communities")
public class AutonomousCommunityListModel extends ListModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
