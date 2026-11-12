package ru.bank.usermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bank.usermicroservice.entity.UserContact;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, UUID> {
    Optional<UserContact> findByUser_UserId(UUID userId);
}
