package ru.bank.usermicroservice.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.bank.usermicroservice.dtos.CreateUserRequest;
import ru.bank.usermicroservice.dtos.UserAddressDto;
import ru.bank.usermicroservice.dtos.UserContactDto;
import ru.bank.usermicroservice.dtos.UserDocumentDto;
import ru.bank.usermicroservice.dtos.UserResponseDto;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends user.UserServiceGrpc.UserServiceImplBase {

    private final UserService userService;

    @Override
    public void createUserProfile(
            user.CreateUserRequest request,
            StreamObserver<user.UserResponse> responseObserver) {
        try {
            UserResponseDto createdUser = userService.createUser(toCreateUserRequest(request));
            responseObserver.onNext(toGrpcResponse(createdUser));
            responseObserver.onCompleted();
        } catch (IllegalArgumentException exception) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription(exception.getMessage())
                    .withCause(exception)
                    .asRuntimeException());
        } catch (RuntimeException exception) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to create user profile")
                    .withCause(exception)
                    .asRuntimeException());
        }
    }

    @Override
    public void getUserById(
            user.UserRequest request,
            StreamObserver<user.UserResponse> responseObserver) {
        final UUID userId;
        try {
            userId = UUID.fromString(request.getUserId());
        } catch (IllegalArgumentException exception) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("user_id must be a valid UUID")
                    .withCause(exception)
                    .asRuntimeException());
            return;
        }

        try {
            UserResponseDto foundUser = userService.getUserById(userId);
            responseObserver.onNext(toGrpcResponse(foundUser));
            responseObserver.onCompleted();
        } catch (ru.bank.usermicroservice.exception.UserNotFoundException exception) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(exception.getMessage())
                    .withCause(exception)
                    .asRuntimeException());
        } catch (RuntimeException exception) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to get user profile")
                    .withCause(exception)
                    .asRuntimeException());
        }
    }

    private CreateUserRequest toCreateUserRequest(user.CreateUserRequest request) {
        CreateUserRequest result = new CreateUserRequest();
        result.setFirstName(request.getFirstName());
        result.setLastName(request.getLastName());
        result.setMiddleName(request.getMiddleName());
        result.setInn(request.getInn());

        if (!request.getPhoneNumber().isBlank() || !request.getEmail().isBlank()) {
            UserContactDto contact = new UserContactDto();
            contact.setPhoneNumber(emptyToNull(request.getPhoneNumber()));
            contact.setEmail(emptyToNull(request.getEmail()));
            result.setContact(contact);
        }

        if (!request.getIndex().isBlank()
                || !request.getRegAddress().isBlank()
                || !request.getFactAddress().isBlank()) {
            UserAddressDto address = new UserAddressDto();
            address.setIndex(emptyToNull(request.getIndex()));
            address.setRegAddress(emptyToNull(request.getRegAddress()));
            address.setFactAddress(emptyToNull(request.getFactAddress()));
            result.setAddress(address);
        }

        if (!request.getPassportSeries().isBlank() || !request.getPassportNumber().isBlank()) {
            UserDocumentDto document = new UserDocumentDto();
            document.setPassportSeries(emptyToNull(request.getPassportSeries()));
            document.setPassportNumber(emptyToNull(request.getPassportNumber()));
            result.setDocument(document);
        }

        return result;
    }

    private user.UserResponse toGrpcResponse(UserResponseDto userResponse) {
        user.UserResponse.Builder response = user.UserResponse.newBuilder()
                .setUserId(userResponse.getUserId().toString())
                .setFirstName(nullToEmpty(userResponse.getFirstName()))
                .setLastName(nullToEmpty(userResponse.getLastName()))
                .setMiddleName(nullToEmpty(userResponse.getMiddleName()));

        if (userResponse.getContact() != null) {
            response.setPhoneNumber(nullToEmpty(userResponse.getContact().getPhoneNumber()));
        }

        return response.build();
    }

    private String emptyToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
