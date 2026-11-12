package ru.bank.usermicroservice.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.bank.usermicroservice.entity.UserStatus;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    @Size(max = 100)
    private String middleName;

    @NotNull(message = "Status is required")
    private UserStatus status;

    @Pattern(regexp = "(?:[0-9]{10}|[0-9]{12})", message = "INN must contain 10 or 12 digits")
    private String inn;

    @Valid
    private UserContactDto contact;

    @Valid
    private UserAddressDto address;

    @Valid
    private UserDocumentDto document;
}
