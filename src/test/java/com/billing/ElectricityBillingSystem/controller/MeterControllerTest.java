package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.jpa.Client;
import com.billing.ElectricityBillingSystem.jpa.ClientRepository;
import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.jpa.MeterRepository;
import com.billing.ElectricityBillingSystem.service.ClientService;
import com.billing.ElectricityBillingSystem.service.MeterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@MockitoSettings(strictness = Strictness.LENIENT)
class MeterControllerTest {

    @InjectMocks
    private MeterController meterController;

    @Mock
    private MeterService meterService;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private MeterRepository meterRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.meterService = new MeterService(meterRepository);
        this.meterController = new MeterController(meterService, clientService, clientRepository);
    }


    @Test
    void createNewMeter_Create() {
        //GIVEN
        Meter meter = new Meter();
        meter.setClientId(1L);
        meter.setId(1L);

        Client client = new Client();
        client.setId(1L);
        client.setMeterId(0L);
        client.setName("Test");
        client.setPostalCode(1234);
        client.setCity("Test city");
        client.setStreet("Test street");
        client.setHouseNumber(2);
        client.setPaymentCategoryId(1);

        given(meterRepository.save(any(Meter.class))).willReturn(meter);
        given(meterService.createNewMeter(1L)).willReturn(meter);
        given(clientService.findClientById(1L)).willReturn(Optional.of(client));

        //WHEN
        when(meterService.createNewMeter(1L)).thenReturn(meter);

        ResponseEntity<?> actual = meterController.createNewMeter(1L);

        //THEN
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
    }


    @Test
    void createNewMeter_Bad_Request() {
        Meter meter = new Meter();
        meter.setClientId(1L);
        meter.setId(1L);

        Client client = new Client();
        client.setId(1L);
        client.setName("Test");
        client.setPostalCode(1234);
        client.setCity("Test city");
        client.setStreet("Test street");
        client.setHouseNumber(2);
        client.setPaymentCategoryId(1);

        //GIVEN
        Optional<Client> clientOptional = Optional.of(client);

        given(meterRepository.save(meter)).willReturn(meter);
        given(clientRepository.findClientById(1L)).willReturn(clientOptional);
        given(meterService.createNewMeter(1L)).willReturn(meter);

        //WHEN
        ResponseEntity<?> actual = meterController.createNewMeter(1L);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }
}