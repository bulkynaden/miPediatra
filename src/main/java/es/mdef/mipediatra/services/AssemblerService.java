package es.mdef.mipediatra.services;

import es.mdef.mipediatra.models.ListModel;
import es.mdef.mipediatra.models.Model;
import es.mdef.mipediatra.models.PostModel;
import es.mdef.mipediatra.models.PutModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AssemblerService<T, S extends Model, U extends PostModel, V extends PutModel, W extends ListModel> {
    private final Supplier<Link> selfLinkSupplier;

    public AssemblerService(Supplier<Link> selfLinkSupplier) {
        this.selfLinkSupplier = selfLinkSupplier;
    }

    public abstract S toModel(T t);

    public abstract W toListModel(T t);

    public abstract T toEntity(U model);

    public abstract T toEntity(V model);

    public abstract T update(T entity, V model);

    public CollectionModel<W> toCollectionModel(List<T> entities) {
        List<W> modelList = entities.stream()
                .map(this::toListModel)
                .collect(Collectors.toList());
        return CollectionModel.of(modelList, selfLinkSupplier.get());
    }
}
