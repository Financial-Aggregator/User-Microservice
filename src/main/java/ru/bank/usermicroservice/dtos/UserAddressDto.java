package ru.bank.usermicroservice.dtos;

import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class UserAddressDto {
    @Pattern(regexp = "[0-9]{6}", message = "Postal index must contain 6 digits")
    private String index;
    @Size(max = 500)
    private String regAddress;
    @Size(max = 500)
    private String factAddress;
}
