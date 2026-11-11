package ru.bank.usermicroservice.dtos;

import lombok.Data;

@Data
public class UserDocumentDto {
    private Integer inn;
    private Integer passportSeries;
    private Integer passportNumber;
}
