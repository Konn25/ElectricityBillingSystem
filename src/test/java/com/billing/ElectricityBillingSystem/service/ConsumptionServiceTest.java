package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.dto.PaymentDTO;
import com.billing.ElectricityBillingSystem.jpa.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsumptionServiceTest {

    @Mock
    private PaymentCategoryService paymentCategoryService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ConsumptionRepository consumptionRepository;

    @InjectMocks
    private ConsumptionService consumptionService;

    @BeforeEach
    void setUp() {
        this.consumptionService = new ConsumptionService(this.consumptionRepository, this.paymentCategoryService, this.paymentService, this.modelMapper);
    }

    @Test
    void getAllConsumption() {
       //GIVEN
        Consumption consumption = new Consumption();
        consumption.setConsumption(102.5);
        consumption.setDay(10);
        consumption.setMonth(1);
        consumption.setYear(2022);
        consumption.setMeterId(1L);

        List<Consumption> consumptionList = new ArrayList<>();
        consumptionList.add(consumption);

        //WHEN
        when(consumptionRepository.findConsumptionByMeterId(1L)).thenReturn(consumptionList);

        //THEN
        assertEquals(consumptionList,consumptionService.getAllConsumption(1L));


    }

    @Test
    void checkConsumption() {
        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setConsumption(102.5);
        consumption.setDay(10);
        consumption.setMonth(1);
        consumption.setYear(2022);
        consumption.setMeterId(1L);

        consumptionRepository.save(consumption);

        //WHEN
        when(consumptionService.checkConsumption(consumption)).thenReturn(Optional.of(consumption));

        //THEN
        assertEquals(Optional.of(consumption), consumptionService.checkConsumption(consumption));

    }

    @Test
    void createNewConsumption() {
        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setConsumption(102.5);
        consumption.setDay(10);
        consumption.setMonth(1);
        consumption.setYear(2022);
        consumption.setMeterId(1L);

        //WHEN
        when(consumptionRepository.save(consumption)).thenReturn(consumption);

        //THEN
        assertEquals(consumption,consumptionService.createNewConsumption(consumption));

    }

    @Test
    void getConsumptionByYear() {
        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setConsumption(102.5);
        consumption.setDay(10);
        consumption.setMonth(1);
        consumption.setYear(2022);
        consumption.setMeterId(1L);

        Consumption consumption2 = new Consumption();
        consumption2.setConsumption(110.5);
        consumption2.setDay(15);
        consumption2.setMonth(2);
        consumption2.setYear(2022);
        consumption2.setMeterId(1L);

        List<Consumption> consumptionList = new ArrayList<>();
        consumptionList.add(consumption2);
        consumptionList.add(consumption);

        consumptionRepository.save(consumption);
        consumptionRepository.save(consumption2);
        //WHEN
        when(consumptionService.getConsumptionByYear(1L, 2022)).thenReturn(consumptionList);

        //THEN
        assertEquals(consumptionList, consumptionService.getConsumptionByYear(1L, 2022));

    }

    @Test
    void getAllConsumptionByYear() {

        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setConsumption(102.5);
        consumption.setDay(10);
        consumption.setMonth(1);
        consumption.setYear(2022);
        consumption.setMeterId(1L);

        Consumption consumption2 = new Consumption();
        consumption.setConsumption(110.5);
        consumption.setDay(15);
        consumption.setMonth(2);
        consumption.setYear(2022);
        consumption.setMeterId(1L);

        List<Consumption> consumptionList = new ArrayList<>();
        consumptionList.add(consumption2);
        consumptionList.add(consumption);

        //WHEN
        when(consumptionRepository.findConsumptionByMeterIdAndYear(1L, 2022)).thenReturn(consumptionList);

        //THEN
        Double total = consumptionService.getAllConsumptionByYear(1L, 2022);

        assertEquals(consumption.getConsumption() + consumption2.getConsumption(), total);

    }

    @Test
    void getConsumptionByYearAndMonth() {

        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setConsumption(110.5);
        consumption.setDay(15);
        consumption.setMonth(1);
        consumption.setYear(2022);
        consumption.setMeterId(1L);

        Optional<Consumption> consumptionOptional = Optional.of(consumption);

        //WHEN
        when(consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(1L, 2022, 1)).thenReturn(consumptionOptional);

        //THEN
        assertEquals(consumptionOptional, consumptionService.getConsumptionByYearAndMonth(1L, 2022, 1));


    }

    @Test
    void checkAllPayment_Bad_Request() {
        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(2);
        payment.setPayment(122.2);
        payment.setCompleted(0);

        Payment payment2 = new Payment();
        payment2.setId(1L);
        payment2.setClientId(1L);
        payment2.setYear(2023);
        payment2.setMonth(1);
        payment2.setPayment(132.2);
        payment2.setCompleted(1);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment2);
        paymentList.add(payment);

        PaymentDTO paymentDTO = new PaymentDTO(1L,2023,1,132.2,1);

        //WHEN

        ResponseEntity<?> actual = consumptionService.checkAllPayment(paymentList,paymentDTO);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());


    }

    @Test
    void checkAllPayment_OK() {
        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(2);
        payment.setPayment(122.2);
        payment.setCompleted(0);

        Payment payment2 = new Payment();
        payment2.setId(1L);
        payment2.setClientId(1L);
        payment2.setYear(2023);
        payment2.setMonth(1);
        payment2.setPayment(132.2);
        payment2.setCompleted(1);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment2);
        paymentList.add(payment);

        PaymentDTO paymentDTO = new PaymentDTO(1L,2023,3,80,0);

        //WHEN

        ResponseEntity<?> actual = consumptionService.checkAllPayment(paymentList,paymentDTO);

        //THEN
        assertEquals(HttpStatus.OK, actual.getStatusCode());

    }



    @Test
    void createPaymentToDatabase_OK() {
        //GIVEN
        PaymentDTO paymentDTO = new PaymentDTO(1L,2023,1,1023.5,0);
        Payment payment = modelMapper.map(paymentDTO, Payment.class);


        //WHEN
        when(paymentCategoryService.calculatePaymentByPaymentCategory(1L,2023,1)).thenReturn(1023.5);
        when(paymentService.createNewPayment(payment)).thenReturn(payment);


        ResponseEntity<?> actual = consumptionService.createPaymentToDatabase(1L,2023,1);

        //THEN
        assertEquals(HttpStatus.OK, actual.getStatusCode());


    }

    @Test
    void createPaymentToDatabase_Bad_Request() {
        //GIVEN
        PaymentDTO paymentDTO = new PaymentDTO(1L,2023,1,1023.5,0);
        Payment payment = modelMapper.map(paymentDTO, Payment.class);


        //WHEN
        when(paymentCategoryService.calculatePaymentByPaymentCategory(1L,2023,1)).thenReturn(0.0);


        ResponseEntity<?> actual = consumptionService.createPaymentToDatabase(1L,2023,1);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());


    }
}