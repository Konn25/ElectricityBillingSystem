package com.billing.ElectricityBillingSystem.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory,Long> {
    Optional<PaymentCategory> findPaymentCategoryById(Long id);

}
