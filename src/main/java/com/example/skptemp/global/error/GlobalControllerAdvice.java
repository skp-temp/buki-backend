package com.example.skptemp.global.error;

import com.example.skptemp.global.common.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CustomResponse<Void>> invalidArgumentValidResponse(MethodArgumentNotValidException e) {
        log.error("Exception : {}, 입력값 : {}", e.getBindingResult().getFieldError(), e.getBindingResult().getFieldError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.error(GlobalErrorCode.VALID_EXCEPTION, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()));
    }


    /**
     * 변수 Binding시 발생하는 오류
     **/
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomResponse<Void>> invalidArgumentBindResponse(BindException e) {
        log.error("Exception : {}, 입력값 : {}", e.getBindingResult().getFieldError(), e.getBindingResult().getFieldError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.error(GlobalErrorCode.VALID_EXCEPTION, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()));
    }


    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<CustomResponse<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.error(GlobalErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<CustomResponse<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.info("{}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.error(GlobalErrorCode.ACCESS_DENIED));
    }

    /**
     * 프로젝트내 설정한 예외가 발생할때 처리하는 부분
     *
     * @param e 발생한 예외
     * @return 예외를 처리해서 반환한다.
     */
    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<CustomResponse<Void>> handleGlobalBaseException(final GlobalException e) {
        log.error("{} Exception {}: {}", e.getErrorCode(), e.getErrorCode().getCode(), e.getErrorCode().getMessage());

        // TODO: Exception type에 따라 HTTP status code 정리 필요
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(CustomResponse.error(e.getErrorCode(), e.getMessage()));
    }


    /**
     * 처리되지 않은 에러를 여기서 처리 한다.
     *
     * @param e 발생한 에러
     * @return CustomResponse로 메시지를 감춰서 반환한다.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<CustomResponse<Void>> handleException(Exception e) {
        log.error("Exception : {}", GlobalErrorCode.OTHER.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponse.error(GlobalErrorCode.OTHER));
    }
}
