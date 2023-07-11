package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.ConsumptionDTO;
import com.billing.ElectricityBillingSystem.jpa.Consumption;
import com.billing.ElectricityBillingSystem.jpa.ConsumptionRepository;
import com.billing.ElectricityBillingSystem.service.ConsumptionService;
import com.billing.ElectricityBillingSystem.service.PaymentCategoryService;
import com.billing.ElectricityBillingSystem.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@MockitoSettings(strictness = Strictness.LENIENT)
class ConsumptionControllerTest {


    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ConsumptionService consumptionService;

    @Mock
    private ConsumptionRepository consumptionRepository;

    @Mock
    private PaymentCategoryService paymentCategoryService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private ConsumptionController consumptionController;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {

        this.consumptionService = new ConsumptionService(consumptionRepository, paymentCategoryService, paymentService, modelMapper);
        this.consumptionController = new ConsumptionController(modelMapper, consumptionService);

    }

    @Test
    void getAllConsumption() throws Exception {

        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(3);
        consumption.setDay(23);
        consumption.setConsumption(235.2);

        consumptionRepository.save(consumption);

        List<Consumption> consumptionList;
        consumptionList = consumptionService.getAllConsumption(1L);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/consumptions/{meterId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(consumptionList));
        //WHEN

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //THEN
        assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    void checkConsumptionByYearAndMonth_OK() {
        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(3);
        consumption.setDay(23);
        consumption.setConsumption(235.2);

        given(consumptionService.getConsumptionByYearAndMonth(1L, 2023, 3)).willReturn(Optional.of(consumption));

        //WHEN
        ResponseEntity<?> actual = consumptionController.checkConsumptionByYearAndMonth(1L, 2023, 3);

        //THEN
        assertEquals(HttpStatus.OK, actual.getStatusCode());

    }

    @Test
    void checkConsumptionByYearAndMonth_Bad_Request() {
        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(3);
        consumption.setDay(23);
        consumption.setConsumption(235.2);

        given(consumptionService.getConsumptionByYearAndMonth(1L, 2023, 3)).willReturn(Optional.of(consumption));

        //WHEN
        ResponseEntity<?> actual = consumptionController.checkConsumptionByYearAndMonth(1L, 2023, 4);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());

    }

    @Test
    void createNewConsumption_OK() {
        //GIVEN
        ConsumptionDTO consumptionDTO = new ConsumptionDTO();
        consumptionDTO.setId(1L);
        consumptionDTO.setMeterId(1L);
        consumptionDTO.setYear(2023);
        consumptionDTO.setMonth(5);
        consumptionDTO.setDay(23);
        consumptionDTO.setConsumption(2302.3);

        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(5);
        consumption.setDay(23);
        consumption.setConsumption(2302.3);

        Consumption consumption2 = new Consumption();
        consumption2.setId(2L);
        consumption2.setMeterId(2L);
        consumption2.setYear(2023);
        consumption2.setMonth(5);
        consumption2.setDay(23);
        consumption2.setConsumption(2302.3);


        given(modelMapper.map(consumptionDTO, Consumption.class)).willReturn(consumption);
        given(consumptionService.checkConsumption(consumption2)).willReturn(Optional.of(consumption2));
        given(consumptionService.createNewConsumption(consumption)).willReturn(consumption);
        given(modelMapper.map(consumption, ConsumptionDTO.class)).willReturn(consumptionDTO);

        //WHEN
        ResponseEntity<?> actual = consumptionController.createNewConsumption(1L, consumptionDTO);

        //THEN
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());

    }

    @Test
    void createNewConsumption_Bad_Request() {
        //GIVEN
        ConsumptionDTO consumptionDTO = new ConsumptionDTO();
        consumptionDTO.setId(1L);
        consumptionDTO.setMeterId(1L);
        consumptionDTO.setYear(2023);
        consumptionDTO.setMonth(5);
        consumptionDTO.setDay(23);
        consumptionDTO.setConsumption(2302.3);

        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(5);
        consumption.setDay(23);
        consumption.setConsumption(2302.3);


        given(modelMapper.map(consumptionDTO, Consumption.class)).willReturn(consumption);
        given(consumptionService.checkConsumption(consumption)).willReturn(Optional.of(consumption));

        //WHEN
        ResponseEntity<?> actual = consumptionController.createNewConsumption(1L, consumptionDTO);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());

    }

    @Test
    void getConsumptionByYear() throws Exception {

        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(3);
        consumption.setDay(23);
        consumption.setConsumption(235.2);

        consumptionRepository.save(consumption);

        List<Consumption> consumptionList;
        consumptionList = consumptionService.getConsumptionByYear(1L, 2023);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/consumption/{meterId}/{year}", 1, 2023)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(consumptionList));
        //WHEN

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //THEN
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void getAllConsumptionByYear() throws Exception {
        //GIVEN
        Consumption consumption = new Consumption();
        consumption.setId(1L);
        consumption.setMeterId(1L);
        consumption.setYear(2023);
        consumption.setMonth(3);
        consumption.setDay(23);
        consumption.setConsumption(235.2);

        consumptionRepository.save(consumption);


        Double price = consumptionService.getAllConsumptionByYear(1L, 2023);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/consumption/allconsumption/{meterId}/{year}", 1, 2023)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(price));
        //WHEN

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //THEN
        assertEquals(200, result.getResponse().getStatus());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}