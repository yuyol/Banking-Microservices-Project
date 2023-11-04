package com.yy.loans.controller;

import com.yy.loans.constants.LoansConstants;
import com.yy.loans.dto.LoansDto;
import com.yy.loans.dto.ResponseDto;
import com.yy.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class LoansController {
    ILoansService iLoansService;

    @PostMapping("/createLoan")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber) {

        iLoansService.createLoan(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
    }

}
