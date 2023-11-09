package com.yy.loans.service.Impl;

import com.yy.loans.constants.LoansConstants;
import com.yy.loans.dto.LoansDto;
import com.yy.loans.entity.Loans;
import com.yy.loans.exception.LoanAlreadyExistsException;
import com.yy.loans.exception.ResourceNotFoundException;
import com.yy.loans.mapper.LoansMapper;
import com.yy.loans.repository.LoansRepository;
import com.yy.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {
    LoansRepository loansRepository;

    /**
     * Create loan
     * @param mobileNumber
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> byMobileNumber = loansRepository.findByMobileNumber(mobileNumber);
        if(byMobileNumber.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already existed");
        }
        Loans loans = createNewLoan(mobileNumber);
        loansRepository.save(loans);
    }

    /**
     * Create new loan details
     * @param mobileNumber
     * @return
     */
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

    /**
     * Fetch loan details
     * @return
     */
    @Override
    public LoansDto fetchLoanDetails(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans","Mobile Number",mobileNumber)
        );

        LoansDto loansDto = LoansMapper.mapToLoansDto(loans,new LoansDto());
        return loansDto;
    }

    /**
     * Update loan
     * @param loansDto
     * @return
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {

        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "Mobile Number", loansDto.getMobileNumber())
        );
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);

        return true;
    }

    /**
     * Delete loan
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "Mobile number", mobileNumber)
        );

        loansRepository.deleteById(loans.getLoanId());

        return true;
    }
}
