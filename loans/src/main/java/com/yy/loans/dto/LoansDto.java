package com.yy.loans.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoansDto {

    @NotEmpty(message = "Mobile number can not be empty")
    private String mobileNumber;

    @NotEmpty(message = "Loan number can not be empty")
    private String loanNumber;

    @NotEmpty(message = "Loan type can not be empty")
    private String loanType;

    @NotEmpty(message = "Total loan can not be empty")
    private int totalLoan;

    @NotEmpty(message = "Amount paid can not be empty")
    private int amountPaid;

    @NotEmpty(message = "Outstanding amount can not be empty")
    private int outstandingAmount;
}
