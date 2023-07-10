package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.*;
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
class PaymentCategoryServiceTest {


    @Mock
    private  PaymentCategoryRepository paymentCategoryRepository;

    @Mock
    private  ClientRepository clientRepository;

    @Mock
    private  ConsumptionRepository consumptionRepository;

    @InjectMocks
    private PaymentCategoryService paymentCategoryService;

    @BeforeEach
    void setUp() {
        this.paymentCategoryService = new PaymentCategoryService(paymentCategoryRepository,clientRepository,consumptionRepository);
    }


    @Test
    void createNewPaymentCategory() {
        //GIVEN
        PaymentCategory paymentCategory = new PaymentCategory(1L,23.5);

        //WHEN
        when(paymentCategoryRepository.save(any(PaymentCategory.class))).thenReturn(paymentCategory);
        //THEN

        assertEquals(paymentCategory, paymentCategoryService.createNewPaymentCategory(23.5));


    }

    @Test
    void getAllPaymentCategory() {
        //GIVEN
        PaymentCategory paymentCategory1 = new PaymentCategory(1L,23.5);
        PaymentCategory paymentCategory2 = new PaymentCategory(2L,25.5);

        List<PaymentCategory> paymentCategoryList = new ArrayList<>();
        paymentCategoryList.add(paymentCategory1);
        paymentCategoryList.add(paymentCategory2);

        //WHEN
        when(paymentCategoryRepository.findAll()).thenReturn(paymentCategoryList);


        //THEN
        assertEquals(paymentCategoryList, paymentCategoryService.getAllPaymentCategory());

    }

    @Test
    void calculatePaymentByPaymentCategory() {

        //GIVEN
        Client client = new Client();
        client.setId(1L);
        client.setPaymentCategoryId(1);
        client.setMeterId(1L);
        client.setPostalCode(1234);
        client.setCity("Test city");
        client.setStreet("Test street");
        client.setHouseNumber(20);
        client.setName("Test");

        PaymentCategory paymentCategory = new PaymentCategory(1L,23.4);

        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(2);
        consumption.setDay(12);
        consumption.setConsumption(50);

        List<Client> clientList = new ArrayList<>();
        clientList.add(client);

        List<PaymentCategory> paymentCategoryList = new ArrayList<>();
        paymentCategoryList.add(paymentCategory);

        List<Consumption> consumptionList = new ArrayList<>();
        consumptionList.add(consumption);

        Optional<Client> optionalClient = clientList.stream().findAny();
        Optional<PaymentCategory> optionalPaymentCategory = paymentCategoryList.stream().findAny();
        Optional<Consumption> consumptionOptional = consumptionList.stream().findAny();

        //WHEN
        when(clientRepository.findClientById(1L)).thenReturn(optionalClient);
        when(paymentCategoryRepository.findPaymentCategoryById(1L)).thenReturn(optionalPaymentCategory);
        when(consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(1L,2023,2)).thenReturn(consumptionOptional);

        //THEN
        assertEquals(1170.0,paymentCategoryService.calculatePaymentByPaymentCategory(1L,2023,2));

    }

}