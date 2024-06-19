package com.example.skptemp.domain.cheer.controller;

import com.example.skptemp.global.common.CustomResponse;
import com.example.skptemp.global.error.GlobalErrorCode;
import com.example.skptemp.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CheerController {
    public ResponseEntity<CustomResponse<Void>> cok(Long userId, Long charmId){
        throw new GlobalException(GlobalErrorCode.VALID_EXCEPTION);
    }
    public ResponseEntity<CustomResponse<Void>> cheerUp(Long userId, Long friendId){
        throw new GlobalException(GlobalErrorCode.VALID_EXCEPTION);
    }
}
