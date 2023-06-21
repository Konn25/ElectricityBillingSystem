package com.billing.ElectricityBillingSystem.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption,Long> {



    Optional<Consumption> findConsumptionByMeterIdAndYearAndMonth(Long meterId, int year, int month);

    List<Consumption> findConsumptionByMeterIdAndYear(Long meterId, int year);

}
