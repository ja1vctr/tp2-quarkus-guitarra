package br.unitins.guitarra.validation;

import java.util.List;
import br.unitins.guitarra.util.Error;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final List<Error.FieldError> fieldErrors;

    public ValidationException(String msg, List<Error.FieldError> errors) {
        super(msg);
        this.fieldErrors = (errors != null) ? List.copyOf(errors) : List.of();
    }

    public static ValidationException of(String field, String message) {
        return new ValidationException("Dados inválidos", List.of(new Error.FieldError(field, message)));
    }

    public List<Error.FieldError> getFieldErrors() {
        return fieldErrors;
    }

}