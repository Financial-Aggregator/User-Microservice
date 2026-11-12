package ru.bank.usermicroservice.dtos;

import lombok.Data;
import jakarta.validation.constraints.Pattern;

@Data
public class UserDocumentDto {
    @Pattern(regexp = "[0-9]{4}", message = "Passport series must contain 4 digits")
    private String passportSeries;
    @Pattern(regexp = "[0-9]{6}", message = "Passport number must contain 6 digits")
    private String passportNumber;
}
