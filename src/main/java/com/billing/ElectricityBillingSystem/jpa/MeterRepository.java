package com.billing.ElectricityBillingSystem.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MeterRepository extends JpaRepository<Meter,Long> {

    Optional<Meter> findMeterByCustomerId(Long clientId);

}
