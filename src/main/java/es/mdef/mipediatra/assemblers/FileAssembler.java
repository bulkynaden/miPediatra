package es.mdef.mipediatra.assemblers;

import es.mdef.mipediatra.controllers.FileController;
import es.mdef.mipediatra.models.ListModel;
import es.mdef.mipediatra.models.PostModel;
import es.mdef.mipediatra.models.PutModel;
import es.mdef.mipediatra.models.file.FileModel;
import es.mdef.mipediatra.services.AssemblerService;
import es.mdef.mipediatralib.entities.File;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FileAssembler extends AssemblerService<File, FileModel, PostModel, PutModel, ListModel> {
    public FileAssembler() {
        super(() -> WebMvcLinkBuilder.linkTo(methodOn(FileController.class).getAll()).withSelfRel());
    }

    public FileModel toModel(File entity) {
        if (entity == null) {
            return null;
        }
        FileModel model = new FileModel();
        model.setName(entity.getName());
        model.setSize(entity.getSize());
        model.setData(entity.getData());
        model.setType(entity.getType());
        model.add(linkTo(methodOn(FileController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    @Override
    public ListModel toListModel(File file) {
        return null;
    }

    @Override
    public File toEntity(PostModel model) {
        return null;
    }

    @Override
    public File toEntity(PutModel model) {
        return null;
    }

    @Override
    public File update(File entity, PutModel model) {
        return null;
    }
}
