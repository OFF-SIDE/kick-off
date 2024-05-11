package offside.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
@Setter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private Integer errorCode;
    private Integer httpStatus;
    private String message;
    
    protected CustomException() {}
    
    /**
     * 사전 정의된 ExceptionTypes를 통해 Exception 발생시키기
     * @param customException
     */
    public CustomException(CustomExceptionTypes customException) {
        this.errorCode = customException.getErrorCode();
        this.httpStatus = customException.getHttpStatus();
        this.message = customException.getMessage();
    }
    
    /**
     * Validation 객체를 통해 Exception을 발생시키기
     * @param bindingResult
     */
    public CustomException(BindingResult bindingResult){
        this.errorCode = 0;
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
        this.message = bindingResult.getFieldError().getDefaultMessage();
    }
    
    public CustomExceptionModel getModel(){
        return new CustomExceptionModel(errorCode,httpStatus,message);
    }
}
