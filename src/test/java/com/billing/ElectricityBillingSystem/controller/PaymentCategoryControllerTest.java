package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.PaymentCategoryDTO;
import com.billing.ElectricityBillingSystem.jpa.*;
import com.billing.ElectricityBillingSystem.service.PaymentCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@MockitoSettings(strictness = Strictness.LENIENT)
class PaymentCategoryControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PaymentCategoryService paymentCategoryService;

    @Mock
    private PaymentCategoryRepository paymentCategoryRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ConsumptionRepository consumptionRepository;

    @InjectMocks
    private PaymentCategoryController paymentCategoryController;

    @Autowired
    private MockMvc mockMvc;

    private final Client client = new Client();
    private PaymentCategoryDTO paymentCategoryDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.paymentCategoryService = new PaymentCategoryService(paymentCategoryRepository, clientRepository, consumptionRepository);
        this.paymentCategoryController = new PaymentCategoryController(modelMapper, paymentCategoryService);

        this.client.setId(1L);
        this.client.setName("Test");
        this.client.setMeterId(1L);
        this.client.setPostalCode(1234);
        this.client.setCity("Test city");
        this.client.setStreet("Test street");
        this.client.setHouseNumber(2);
        this.client.setPaymentCategoryId(1);

        paymentCategoryDTO = new PaymentCategoryDTO();
        this.paymentCategoryDTO.setId(1L);
        this.paymentCategoryDTO.setPrice(20.0);

    }

    @Test
    void registerNewCategory_Created() {

        //GIVEN
        PaymentCategory paymentCategory = new PaymentCategory(1L, 20.0);
        PaymentCategory paymentCategory2 = new PaymentCategory(2L, 21.0);


        given(modelMapper.map(this.paymentCategoryDTO, PaymentCategory.class)).willReturn(paymentCategory);
        given(paymentCategoryService.getAllPaymentCategory()).willReturn(List.of(paymentCategory2));


        //WHEN
        ResponseEntity<?> actual = paymentCategoryController.registerNewCategory(paymentCategoryDTO);

        //THEN
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());

    }

    @Test
    void registerNewCategory_Conflict() {

        //GIVEN
        PaymentCategory paymentCategory = new PaymentCategory(1L, 20.0);


        given(modelMapper.map(this.paymentCategoryDTO, PaymentCategory.class)).willReturn(paymentCategory);
        given(paymentCategoryService.getAllPaymentCategory()).willReturn(List.of(paymentCategory));


        //WHEN
        ResponseEntity<?> actual = paymentCategoryController.registerNewCategory(paymentCategoryDTO);

        //THEN
        assertEquals(HttpStatus.CONFLICT, actual.getStatusCode());


    }


    @Test
    void getAllPaymentCategory() throws Exception {
        //GIVEN
        clientRepository.save(this.client);
        List<PaymentCategory> paymentCategoryList = new ArrayList<>();
        paymentCategoryList = paymentCategoryService.getAllPaymentCategory();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/paymentcategory/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentCategoryList));
        //WHEN

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //THEN
        assertEquals(200, result.getResponse().getStatus());


    }

    @Test
    void getActualPayment_OK() {
        //GIVEN
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setClientId(1L);

        PaymentCategory paymentCategory = new PaymentCategory(1L, 23.2);

        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(2);
        consumption.setDay(23);
        consumption.setConsumption(100.0);


        given(clientRepository.findClientById(1L)).willReturn(Optional.of(client));
        given(paymentCategoryRepository.findPaymentCategoryById(1L)).willReturn(Optional.of(paymentCategory));
        given(consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(1L, 2023, 2)).willReturn(Optional.of(consumption));


        //WHEN
        ResponseEntity<?> actual = paymentCategoryController.getActualPayment(1L, 2023, 2);

        //THEN
        assertEquals(HttpStatus.OK, actual.getStatusCode());


    }

    @Test
    void getActualPayment_Bad_Request() {
        //GIVEN
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setClientId(1L);

        PaymentCategory paymentCategory = new PaymentCategory(1L, 23.2);

        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(2);
        consumption.setDay(23);
        consumption.setConsumption(100.0);


        given(clientRepository.findClientById(1L)).willReturn(Optional.of(client));
        given(paymentCategoryRepository.findPaymentCategoryById(1L)).willReturn(Optional.of(paymentCategory));
        given(consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(1L, 2022, 2)).willReturn(Optional.of(consumption));


        //WHEN
        ResponseEntity<?> actual = paymentCategoryController.getActualPayment(1L, 2023, 2);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());


    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}