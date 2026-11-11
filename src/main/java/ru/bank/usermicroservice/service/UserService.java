package ru.bank.usermicroservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.usermicroservice.dtos.*;
import ru.bank.usermicroservice.entity.*;
import ru.bank.usermicroservice.exception.UserNotFoundException;
import ru.bank.usermicroservice.mapper.UserMapper;
import ru.bank.usermicroservice.repository.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserDocumentRepository userDocumentRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        return userMapper.toResponse(findUserOrThrow(id));
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = userMapper.toEntity(request);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        if (request.getContact() != null) {
            UserContact contact = UserContact.builder()
                    .phoneNumber(request.getContact().getPhoneNumber())
                    .email(request.getContact().getEmail())
                    .user(user)
                    .build();
            userContactRepository.save(contact);
            user.setContact(contact);
        }

        if (request.getAddress() != null) {
            UserAddress address = UserAddress.builder()
                    .index(request.getAddress().getIndex())
                    .regAddress(request.getAddress().getRegAddress())
                    .factAddress(request.getAddress().getFactAddress())
                    .user(user)
                    .build();
            userAddressRepository.save(address);
            user.setAddress(address);
        }

        if (request.getDocument() != null) {
            UserDocument document = UserDocument.builder()
                    .inn(request.getDocument().getInn())
                    .passportSeries(request.getDocument().getPassportSeries())
                    .passportNumber(request.getDocument().getPassportNumber())
                    .user(user)
                    .build();
            userDocumentRepository.save(document);
            user.setDocument(document);
        }

        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        User user = findUserOrThrow(id);
        userMapper.updateEntityFromRequest(request, user);

        if (request.getContact() != null) {
            UserContact contact = userContactRepository.findByUser_UserId(id)
                    .orElseGet(() -> UserContact.builder().user(user).build());
            contact.setPhoneNumber(request.getContact().getPhoneNumber());
            contact.setEmail(request.getContact().getEmail());
            userContactRepository.save(contact);
            user.setContact(contact);
        }

        if (request.getAddress() != null) {
            UserAddress address = userAddressRepository.findByUser_UserId(id)
                    .orElseGet(() -> UserAddress.builder().user(user).build());
            address.setIndex(request.getAddress().getIndex());
            address.setRegAddress(request.getAddress().getRegAddress());
            address.setFactAddress(request.getAddress().getFactAddress());
            userAddressRepository.save(address);
            user.setAddress(address);
        }

        if (request.getDocument() != null) {
            UserDocument document = userDocumentRepository.findByUser_UserId(id)
                    .orElseGet(() -> UserDocument.builder().user(user).build());
            document.setInn(request.getDocument().getInn());
            document.setPassportSeries(request.getDocument().getPassportSeries());
            document.setPassportNumber(request.getDocument().getPassportNumber());
            userDocumentRepository.save(document);
            user.setDocument(document);
        }

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private User findUserOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
