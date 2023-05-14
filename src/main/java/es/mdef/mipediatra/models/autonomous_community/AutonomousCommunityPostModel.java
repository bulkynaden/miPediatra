package es.mdef.mipediatra.models.autonomous_community;

import es.mdef.mipediatra.models.PostModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "autonomous_community")
public class AutonomousCommunityPostModel extends PostModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
