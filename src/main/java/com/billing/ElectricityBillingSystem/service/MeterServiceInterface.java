package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Meter;

import java.util.Optional;

public interface MeterServiceInterface {

    Meter createNewMeter(Long clientId);

    Optional<Meter> findMeterByClientId(Long clientId);

}
