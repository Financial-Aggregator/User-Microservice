package ru.bank.usermicroservice.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.bank.usermicroservice.entity.UserStatus;

@Data
public class CreateUserRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String middleName;

    private Integer inn;

    @Valid
    private UserContactDto contact;

    @Valid
    private UserAddressDto address;

    @Valid
    private UserDocumentDto document;
}
