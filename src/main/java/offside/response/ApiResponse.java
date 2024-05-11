package offside.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ApiResponse<T> {
    
    private static final String SUCCESS_STATUS = "ok";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";
    
    public String status;
    public Integer code;
    public String message;
    public T data;
    
    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, 200, data, null);
    }
    public static <T> ApiResponse<T> createSuccess(T data, String message) {
        return new ApiResponse<>(SUCCESS_STATUS, 200, data, message);
    }
    
    public static ApiResponse<?> createSuccess() {
        return new ApiResponse<>(SUCCESS_STATUS,200, null, null);
    }
    public static ApiResponse<?> createSuccess(String message) {
        return new ApiResponse<>(SUCCESS_STATUS,200, null, message);
    }
    
    
    // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
    public static ApiResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(FAIL_STATUS, HttpStatus.BAD_REQUEST.value(), errors, null);
    }
    
    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<?> createError(String message) {
        return new ApiResponse<>(ERROR_STATUS, HttpStatus.FORBIDDEN.value(), null, message);
    }
    public static ApiResponse<?> createError(String message, HttpStatus httpStatus) {
        return new ApiResponse<>(ERROR_STATUS, httpStatus.value() , null, message);
    }
    public static ApiResponse<?> createError(String message, Integer httpStatusCode) {
        return new ApiResponse<>(ERROR_STATUS, httpStatusCode, null, message);
    }
    
    public static <T> ApiResponse<T> createError(T data,String message) {
        return new ApiResponse<>(ERROR_STATUS, HttpStatus.BAD_REQUEST.value(), data, message);
    }
    
    private ApiResponse(String status, Integer code,T data, String message) {
        this.status = status;
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
