package es.mdef.mipediatra.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public <U> EntityNotFoundException(U id, String entityName) {
        super("No se ha encontrado " + entityName + " " + id);
    }
}
