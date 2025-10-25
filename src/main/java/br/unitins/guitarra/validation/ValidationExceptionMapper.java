package br.unitins.guitarra.validation;

import java.time.OffsetDateTime;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unitins.guitarra.util.Error;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Context
    UriInfo uri;

    @ConfigProperty(name = "error.base-url")
    String baseUrl;

    @Override
    public Response toResponse(ValidationException exception) {
        var error = new Error();
        error.type = baseUrl + "/errors/validation-error";
        error.title = "Erro de validação";
        error.status = Response.Status.BAD_REQUEST.getStatusCode();
        error.detail = exception.getMessage();
        error.instance = (uri != null ? uri.getRequestUri().getPath() : null);
        error.timestamp = OffsetDateTime.now();
        error.errors = exception.getFieldErrors();

        return Response.status(error.status).type("application/problem+json").entity(error).build();
    
    
    // @Override
    // public Response toResponse(ValidationException exception) {

    //     ValidationError validationError = new ValidationError("400", "Erro de Validação");
    //     validationError.addFieldError(exception.getFieldName(), exception.getMessage());

    //     return Response.status(Response.Status.BAD_REQUEST).entity(validationError).build();

    }
}