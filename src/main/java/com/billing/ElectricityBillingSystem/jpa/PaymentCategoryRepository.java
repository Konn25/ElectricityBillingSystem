package com.billing.ElectricityBillingSystem.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory,Long> {

}
