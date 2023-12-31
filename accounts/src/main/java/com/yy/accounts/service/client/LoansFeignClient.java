package com.yy.accounts.service.client;

import com.yy.accounts.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetchLoan", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("yyBank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
