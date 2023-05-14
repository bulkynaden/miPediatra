package es.mdef.mipediatra.models.autonomous_community;

import es.mdef.mipediatra.models.PutModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "autonomous_community")
public class AutonomousCommunityPutModel extends PutModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
