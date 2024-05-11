package offside.response.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<CustomExceptionModel> handleCustomException(CustomException exception) {
        final var errorType = this.getErrorType(exception.getHttpStatus());
        return new ResponseEntity(exception.getModel(), errorType);
    }
    
    public HttpStatus getErrorType(Integer httpStatus){
        switch (httpStatus){
            case 400:{
                return HttpStatus.BAD_REQUEST;
            }
            case 401:{
                return HttpStatus.UNAUTHORIZED;
            }
            case 403:{
                return HttpStatus.FORBIDDEN;
            }
            case 404:{
                return HttpStatus.NOT_FOUND;
            }
            case 406:{
                return HttpStatus.NOT_ACCEPTABLE;
            }
            default:{
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
