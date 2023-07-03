package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.jpa.MeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeterService implements MeterServiceInterface{

    private final MeterRepository meterRepository;


    @Override
    public Meter createNewMeter(Long clientid) {
        Meter newMeter = new Meter(clientid);
        return meterRepository.save(newMeter);
    }

    @Override
    public Optional<Meter> findMeterByClientId(Long clientId) {
        return meterRepository.findMeterByClientId(clientId);
    }
}
