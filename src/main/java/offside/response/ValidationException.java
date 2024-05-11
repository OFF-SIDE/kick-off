package offside.response;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {
    public BindingResult bindingResult;
    public ValidationException(BindingResult bindingResult){
        super(bindingResult.getFieldError().getDefaultMessage());
        this.bindingResult = bindingResult;
    }
}
