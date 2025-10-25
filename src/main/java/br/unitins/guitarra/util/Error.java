package br.unitins.guitarra.util;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
public String type;                  // ex.: "https://api.seusistema.com/errors/validation-error"
    public String title;             // "Erro de validação"
    public Integer status;           // 400
    public String detail;            // mensagem específica
    public String instance;          // path/URI da requisição
    public OffsetDateTime timestamp; // extra
    public String traceId;           // extra
    public List<FieldError> errors;  // extra (erros de campo)

    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class FieldError {
        public final String field;
        public final String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}