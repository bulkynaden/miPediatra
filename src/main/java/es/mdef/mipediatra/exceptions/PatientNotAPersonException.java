package es.mdef.mipediatra.exceptions;

public class PatientNotAPersonException extends RuntimeException {
    public PatientNotAPersonException(Long id) {
        super("El paciente con id: " + id + " seleccionado no es una persona");
    }
}