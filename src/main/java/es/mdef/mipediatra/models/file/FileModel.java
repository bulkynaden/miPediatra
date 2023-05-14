package es.mdef.mipediatra.models.file;

import es.mdef.mipediatra.models.Model;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "file")
public class FileModel extends Model {
    private String name;
    private Long size;
    private byte[] data;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
