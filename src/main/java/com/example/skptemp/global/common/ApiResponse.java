package com.example.skptemp.global.common;


import com.example.skptemp.global.error.GlobalErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.skptemp.global.error.GlobalErrorCode.SUCCESS;

@Getter
@NoArgsConstructor
// AllArgsConstructor : 모든 필드값을 받는 생성자 생성
@AllArgsConstructor
// @JsonPropertyOrder : json serialization 순서를 정의
@JsonPropertyOrder({"status", "code", "message", "result"})
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL) // 결과값이 공백일 경우 json에 포함하지 않도록
    private T result;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", result=" + result +
                '}';
    }

    public static ApiResponse<Void> ok(){
        return new ApiResponse<>(SUCCESS);
    }

    public static <T> ApiResponse<T> ok(T result){
        return new ApiResponse<>(result);
    }

    public static ApiResponse<Void> error(GlobalErrorCode status){
        return new ApiResponse<>(status);
    }
    public static ApiResponse<Void> error(GlobalErrorCode status, String message){
        return new ApiResponse<>(status, message);
    }

    // 요청에 성공한 경우
    private ApiResponse(T result) {
        this.status = SUCCESS.getStatus();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    private ApiResponse(GlobalErrorCode status) {
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    // GlobalControllerAdvice에서 오류 설정
    private ApiResponse(GlobalErrorCode status, String message) {
        this.status = status.getStatus();
        this.message = message;
        this.code = status.getCode();
    }}
