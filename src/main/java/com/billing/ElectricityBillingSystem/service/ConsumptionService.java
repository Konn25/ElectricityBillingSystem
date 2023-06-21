package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Consumption;
import com.billing.ElectricityBillingSystem.jpa.ConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsumptionService implements ConsumptionServiceInterface {

    private final ConsumptionRepository consumptionRepository;

    @Override
    public List<Consumption> getAllConsumption(Long meterId) {
        return consumptionRepository.findAll();
    }

    @Override
    public Optional<Consumption> checkConsumption(Consumption consumption) {
        return consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(consumption.getMeterId(), consumption.getYear(), consumption.getMonth());
    }

    @Override
    public Consumption createNewConsumption(Consumption newConsumption) {
        return consumptionRepository.save(newConsumption);
    }

    @Override
    public List<Consumption> getConsumptionByYear(Long meterId, int year) {
        return consumptionRepository.findConsumptionByMeterIdAndYear(meterId, year);
    }

    @Override
    public Double getAllConsumptionByYear(Long meterId, int year) {
        double allConsumption = 0.0;
        List<Consumption> consumptionList = consumptionRepository.findConsumptionByMeterIdAndYear(meterId, year);

        for (Consumption consumption : consumptionList) {
            allConsumption = allConsumption + consumption.getConsumption();

        }

        return allConsumption;
    }

    @Override
    public Optional<Consumption> getConsumptionByYearAndMonth(Long meterId, int year, int month) {
        return consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(meterId, year, month);
    }
}
