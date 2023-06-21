package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Consumption;

import java.util.List;
import java.util.Optional;

public interface ConsumptionServiceInterface {

    List<Consumption> getAllConsumption(Long meterId);

    Optional<Consumption> checkConsumption(Consumption consumption);

    Consumption createNewConsumption(Consumption newConsumption);

    List<Consumption> getConsumptionByYear(Long meterId, int year);

    Double getAllConsumptionByYear(Long meterId, int year);

    Optional<Consumption> getConsumptionByYearAndMonth(Long meterId, int year, int month);

}
