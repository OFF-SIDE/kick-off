package offside.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomExceptionTypes {
    // 0~999 : 공통 에러
    VALIDATION_ERROR(0,HttpStatus.BAD_REQUEST.value(), "잘못된 API 요청입니다."),
    TOKEN_UNAUTHORIZED_ERROR(1, HttpStatus.UNAUTHORIZED.value(), "잘못된 토큰입니다."),
    TOKEN_NOT_FOUND(2, HttpStatus.NOT_FOUND.value(), "토큰을 찾을 수 없습니다."),
    TOKEN_TIME_OUT(3, HttpStatus.NOT_FOUND.value(), "토큰이 만료되었습니다."),
    
    
    // 1000 ~ 1999 : Auth 에러
    USER_ID_NOT_FOUND(1000, HttpStatus.BAD_REQUEST.value(), "userId 가 입력되지 않았습니다."),
    USER_NOT_FOUND(1001, HttpStatus.NOT_FOUND.value(), "해당하는 유저가 존재하지 않습니다."),
    USER_ALREADY_EXIST(1002, HttpStatus.FORBIDDEN.value(), "이미 존재하는 유저입니다."),
    
    // 2000 ~ 2999 : Stadium 에러
    STADIUM_NOT_FOUND(2000, HttpStatus.NOT_FOUND.value(), "해당하는 구장이 없습니다."),
    
    // 3000 ~ 3999 : 심판 에러
    REFEREE_NOT_FOUND(3000, HttpStatus.NOT_FOUND.value(), "해당하는 심판글이 없습니다.");
    
    Integer errorCode;
    Integer httpStatus;
    String message;
}
