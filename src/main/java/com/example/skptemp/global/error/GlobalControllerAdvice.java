package com.example.skptemp.global.error;

import com.example.skptemp.global.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {


    /**
     * Valid 검증 실패시 오류 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> invalidArgumentValidResponse(MethodArgumentNotValidException e) {
        log.error("Exception : {}, 입력값 : {}", e.getBindingResult().getFieldError(), e.getBindingResult().getFieldError());
        return new ApiResponse<>(GlobalErrorCode.VALID_EXCEPTION, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }


    /**
     * 변수 Binding시 발생하는 오류
     **/
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> invalidArgumentBindResponse(BindException e) {
        log.error("Exception : {}, 입력값 : {}", e.getBindingResult().getFieldError(), e.getBindingResult().getFieldError());
        return new ApiResponse<>(GlobalErrorCode.VALID_EXCEPTION, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }


    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);

        return new ApiResponse<>(GlobalErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ApiResponse<Object> handleAccessDeniedException(AccessDeniedException e) {
        log.info("{}", e.getMessage());
        return new ApiResponse<>(GlobalErrorCode.ACCESS_DENIED);
    }

    /**
     * 프로젝트내 설정한 예외가 발생할때 처리하는 부분
     *
     * @param e 발생한 예외
     * @return 예외를 처리해서 반환한다.
     */
    @ExceptionHandler(GlobalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<Object> handleGlobalBaseException(final GlobalException e) {
        log.error("{} Exception {}: {}", e.getErrorCode(), e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        return new ApiResponse<>(e.getErrorCode());
    }


    /**
     * 처리되지 않은 에러를 여기서 처리 한다.
     *
     * @param e 발생한 에러
     * @return ApiResponse로 메시지를 감춰서 반환한다.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiResponse<Object> handleException(Exception e) {
        log.error("Exception : {}", GlobalErrorCode.OTHER.getMessage(), e);
        return new ApiResponse<>(GlobalErrorCode.OTHER);
    }
}
