package ar.uba.fi.comedor.dto;

public record ErrorResponse(
    String message,
    String code,
    java.time.LocalDateTime timestamp
) {

}
