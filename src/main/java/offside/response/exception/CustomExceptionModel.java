package offside.response.exception;

public record CustomExceptionModel(
    Integer errorCode,
    Integer httpStatus,
    String message
) {
}
