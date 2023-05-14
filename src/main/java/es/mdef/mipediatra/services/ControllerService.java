package es.mdef.mipediatra.services;

import es.mdef.mipediatra.models.ListModel;
import es.mdef.mipediatra.models.Model;
import es.mdef.mipediatra.models.PostModel;
import es.mdef.mipediatra.models.PutModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ControllerService<T, S extends Model, U extends PostModel, V extends PutModel, W extends ListModel> {

    @GetMapping("{id}")
    default ResponseEntity<S> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getService().read(id));
    }

    @GetMapping
    default ResponseEntity<CollectionModel<W>> getAll() {
        return ResponseEntity.ok(getService().readAll());
    }

    @PostMapping
    default ResponseEntity<S> create(@RequestBody U model) {
        return ResponseEntity.ok(getService().create(model));
    }

    @PutMapping("{id}")
    default ResponseEntity<S> update(@PathVariable Long id, @RequestBody V model) {
        return ResponseEntity.ok(getService().update(id, model));
    }

    @DeleteMapping("{id}")
    default ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }

    CrudService<T, S, U, V, W> getService();
}
