package ru.bank.usermicroservice.mapper;

import org.mapstruct.*;
import ru.bank.usermicroservice.dtos.*;
import ru.bank.usermicroservice.entity.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "contact", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "document", ignore = true)
    User toEntity(CreateUserRequest request);

    UserContactDto toContactDto(UserContact contact);

    UserAddressDto toAddressDto(UserAddress address);

    UserDocumentDto toDocumentDto(UserDocument document);

    @Mapping(target = "contact", expression = "java(toContactDto(user.getContact()))")
    @Mapping(target = "address", expression = "java(toAddressDto(user.getAddress()))")
    @Mapping(target = "document", expression = "java(toDocumentDto(user.getDocument()))")
    UserResponse toResponse(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "contact", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "document", ignore = true)
    void updateEntityFromRequest(UpdateUserRequest request, @MappingTarget User user);
}
