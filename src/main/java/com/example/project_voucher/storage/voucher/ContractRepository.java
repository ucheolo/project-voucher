package com.example.project_voucher.storage.voucher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    Optional<ContractEntity> findByCode(String code);
}
