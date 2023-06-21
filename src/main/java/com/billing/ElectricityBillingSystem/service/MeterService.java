package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.jpa.MeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeterService implements MeterServiceInterface{

    private final MeterRepository meterRepository;


    @Override
    public Meter createNewMeter(Long clientid) {
        Meter newMeter = new Meter(clientid);
        return meterRepository.save(newMeter);
    }
}
