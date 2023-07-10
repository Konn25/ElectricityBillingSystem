package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.jpa.MeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MeterServiceTest {

    @Mock
    private  MeterRepository meterRepository;
    @InjectMocks
    private MeterService meterService;

    @BeforeEach
    void setUp() {
        this.meterService = new MeterService(meterRepository);
    }


    @Test
    void createNewMeter() {

        //GIVEN
        Meter meter = new Meter();
        meter.setClientId(1L);
        meter.setId(1L);

        //WHEN
        when(meterService.createNewMeter(1L)).thenReturn(meter);
        when(meterRepository.save(any(Meter.class))).thenReturn(meter);


        //THEN
        assertEquals(meter,meterService.createNewMeter(1L));


    }

    @Test
    void findMeterByClientId() {

        //GIVEN
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setClientId(1L);

        List<Meter> meterList = new ArrayList<>();
        meterList.add(meter);

        Optional<Meter> meterOptional = meterList.stream().findAny();

        //WHEN
        when(meterRepository.findMeterByClientId(1L)).thenReturn(meterOptional);

        //THEN
        assertEquals(meterOptional,meterService.findMeterByClientId(1L));



    }
}