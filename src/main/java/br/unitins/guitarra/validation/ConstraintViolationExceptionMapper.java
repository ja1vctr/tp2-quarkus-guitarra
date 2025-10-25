package br.unitins.guitarra.validation;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import br.unitins.guitarra.util.Error;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>{
  @Context
    UriInfo uriInfo;

  @ConfigProperty(name = "error.base-url")
  String baseUrl;

  @Override
  public Response toResponse(ConstraintViolationException e) {
    List<Error.FieldError> fieldErrors = e.getConstraintViolations()
            .stream()
            .map(this::toFieldError)
            .collect(Collectors.toList());

    Error error = new Error();
    error.type = baseUrl + "/errors/validation-error";
    error.title = "Erro de validação";
    error.status = Response.Status.BAD_REQUEST.getStatusCode();
    error.detail = "Um ou mais campos não passaram na validação.";
    error.instance = (uriInfo != null ? uriInfo.getRequestUri().getPath() : null);
    error.timestamp = OffsetDateTime.now();
    error.errors = fieldErrors;

    return Response.status(error.status)
            .type("application/problem+json")
            .entity(error)
            .build();
  }

  private Error.FieldError toFieldError(ConstraintViolation<?> v) {
        String field = lastProperty(v.getPropertyPath());
        String message = v.getMessage();
        return new Error.FieldError(field, message);
    }

    private String lastProperty(Path path) {
        String last = null;
        for (Path.Node node : path) {
            ElementKind kind = node.getKind();
            if (kind == ElementKind.PROPERTY || kind == ElementKind.CONTAINER_ELEMENT) {
                last = node.getName();
            }
        }
        return (last != null) ? last : "unknown";
    }
}
