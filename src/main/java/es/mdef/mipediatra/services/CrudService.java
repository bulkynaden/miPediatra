package es.mdef.mipediatra.services;

import es.mdef.mipediatra.exceptions.EntityNotFoundException;
import es.mdef.mipediatra.models.ListModel;
import es.mdef.mipediatra.models.Model;
import es.mdef.mipediatra.models.PostModel;
import es.mdef.mipediatra.models.PutModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;

import java.util.Objects;

public interface CrudService<T, S extends Model, U extends PostModel, V extends PutModel, W extends ListModel> {
    default S read(Long id) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, getEntityName()));
        return getAssembler().toModel(entity);
    }

    default CollectionModel<W> readAll() {
        return getAssembler().toCollectionModel(getRepository().findAll());
    }

    default S create(U model) {
        T entity = getRepository().save(getAssembler().toEntity(model));
        return getAssembler().toModel(entity);
    }

    default S update(Long id, V model) {
        T entity = getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException(id, getEntityName()));

        entity = getAssembler().update(entity, model);

        getRepository().save(Objects.requireNonNull(entity));
        return getAssembler().toModel(entity);
    }

    default void delete(Long id) {
        getRepository().deleteById(id);
    }

    String getEntityName();

    JpaRepository<T, Long> getRepository();

    AssemblerService<T, S, U, V, W> getAssembler();
}


