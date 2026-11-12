package ru.bank.usermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bank.usermicroservice.entity.UserAddress;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {
    Optional<UserAddress> findByUser_UserId(UUID userId);
}
