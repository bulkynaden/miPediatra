package es.mdef.mipediatra.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, String tipo) {
        super("No se ha encontrado " + tipo + " " + id);
    }
}
