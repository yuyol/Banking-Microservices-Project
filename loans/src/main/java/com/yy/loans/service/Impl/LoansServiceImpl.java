package com.yy.loans.service.Impl;

import com.yy.loans.constants.LoansConstants;
import com.yy.loans.dto.LoansDto;
import com.yy.loans.entity.Loans;
import com.yy.loans.mapper.LoansMapper;
import com.yy.loans.repository.LoansRepository;
import com.yy.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {
    LoansRepository loansRepository;
    @Override
    public void createLoan(String mobileNumber) {

        Loans loans = createNewLoan(mobileNumber);
        loansRepository.save(loans);
    }

    public Loans createNewLoan(String mobileNumber) {
        Loans loans = new Loans();
        long loanNumber = 1000000000L + new Random().nextInt(900000000);
        loans.setMobileNumber(mobileNumber);
        loans.setLoanNumber(Long.toString(loanNumber));
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return loans;
    }
}
