package ru.bank.usermicroservice.dtos;

import lombok.Data;
import ru.bank.usermicroservice.entity.UserStatus;

import java.util.UUID;

@Data
public class UserResponse {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private UserStatus status;
    private String inn;
    private UserContactDto contact;
    private UserAddressDto address;
    private UserDocumentDto document;
}
