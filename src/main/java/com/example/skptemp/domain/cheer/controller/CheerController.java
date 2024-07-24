package com.example.skptemp.domain.cheer.controller;

import com.example.skptemp.domain.cheer.dto.SendCheerRequest;
import com.example.skptemp.domain.cheer.service.CheerService;
import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class CheerController {

    private final CheerService cheerService;
    public ResponseEntity<CustomResponse<Void>> cok(Long userId, Long charmId){
        throw new GlobalException(GlobalErrorCode.VALID_EXCEPTION);
    }
    public ResponseEntity<CustomResponse<Void>> cheerUp(Long userId, Long friendId){
        throw new GlobalException(GlobalErrorCode.VALID_EXCEPTION);
    }

    @Operation(summary = "응원 메시지 보내기")
    @PostMapping("/cheer")
    public ResponseEntity<CustomResponse<SendCheerRequest>> sendCheer(@RequestBody SendCheerRequest request) {
        cheerService.sendCheer(request);

        return CustomResponse.okResponseEntity(request);
    }
}
