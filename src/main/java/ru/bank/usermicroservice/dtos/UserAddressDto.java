package ru.bank.usermicroservice.dtos;

import lombok.Data;

@Data
public class UserAddressDto {
    private Integer index;
    private String regAddress;
    private String factAddress;
}
