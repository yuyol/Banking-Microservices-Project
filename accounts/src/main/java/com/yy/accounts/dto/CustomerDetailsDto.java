package com.yy.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {
    @Schema(
            description = "Name of the customer",
            example = "Yang Yu"
    )
    @NotEmpty(message = "Name can not be a null or empty") // mandatory field
    @Size(min = 4, max = 30, message = "The length of the customer name should be between 4 to 30")
    private String name;

    @Schema(
            description = "Email address of the customer",
            example = "yy368@uw.edu"
    )
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Invalid email address")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",
            example = "4255438889"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    // Important role of the DTO: Combining data from other classes
    private AccountsDto accountsDto;

    @Schema(
            description = "Loans details of the Customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Cards details of the Customer"
    )
    private CardsDto cardsDto;
}
