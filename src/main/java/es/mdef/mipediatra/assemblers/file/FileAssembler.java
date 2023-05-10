package es.mdef.mipediatra.assemblers.file;

import entities.File;
import es.mdef.mipediatra.controllers.FileController;
import es.mdef.mipediatra.models.file.FileModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FileAssembler {
    public FileModel toModel(File entity) {
        FileModel model = new FileModel();
        model.setName(entity.getName());
        model.setSize(entity.getSize());
        model.setData(entity.getData());
        model.add(linkTo(methodOn(FileController.class).getById(entity.getId())).withSelfRel());
        return model;
    }
}
