package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Meter;

public interface MeterServiceInterface {

    Meter createNewMeter(Long clientId);

}
