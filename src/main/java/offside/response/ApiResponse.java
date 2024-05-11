package offside.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ApiResponse<T> {
    public Integer code;
    public String message;
    public T data;
    
    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(200, data, null);
    }
    public static <T> ApiResponse<T> createSuccess(T data, String message) {
        return new ApiResponse<>(200, data, message);
    }
    
    public static ApiResponse<?> createSuccess() {
        return new ApiResponse<>(200, null, null);
    }
    public static ApiResponse<?> createSuccess(String message) {
        return new ApiResponse<>(200, null, message);
    }
    
    private ApiResponse(Integer code,T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
