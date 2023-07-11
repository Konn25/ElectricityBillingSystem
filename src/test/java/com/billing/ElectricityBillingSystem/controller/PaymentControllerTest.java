package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.PaymentDTO;
import com.billing.ElectricityBillingSystem.jpa.Payment;
import com.billing.ElectricityBillingSystem.jpa.PaymentRepository;
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
class PaymentControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentController paymentController;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        this.paymentService = new PaymentService(paymentRepository);
        this.paymentController = new PaymentController(modelMapper, paymentService);
    }

    @Test
    void registerNewPayment_Created() {
        //GIVEN
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setClientId(1L);
        paymentDTO.setYear(2022);
        paymentDTO.setMonth(2);
        paymentDTO.setPayment(2320.3);
        paymentDTO.setCompleted(0);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2022);
        payment.setMonth(2);
        payment.setPayment(2320.3);
        payment.setCompleted(0);

        Payment payment2 = new Payment();
        payment2.setId(2L);
        payment2.setClientId(2L);
        payment2.setYear(2023);
        payment2.setMonth(2);
        payment2.setPayment(2320.3);
        payment2.setCompleted(0);

        given(modelMapper.map(paymentDTO, Payment.class)).willReturn(payment);
        given(paymentService.getAllBill()).willReturn(List.of(payment2));
        given(paymentService.createNewPayment(payment)).willReturn(payment);
        given(modelMapper.map(payment, PaymentDTO.class)).willReturn(paymentDTO);

        //WHEN
        ResponseEntity<?> actual = paymentController.registerNewPayment(paymentDTO);

        //THEN
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());

    }

    @Test
    void registerNewPayment_Conflict() {
        //GIVEN
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setClientId(1L);
        paymentDTO.setYear(2022);
        paymentDTO.setMonth(2);
        paymentDTO.setPayment(2320.3);
        paymentDTO.setCompleted(0);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2022);
        payment.setMonth(2);
        payment.setPayment(2320.3);
        payment.setCompleted(0);

        given(modelMapper.map(paymentDTO, Payment.class)).willReturn(payment);
        given(paymentService.getAllBill()).willReturn(List.of(payment));
        given(paymentService.createNewPayment(payment)).willReturn(payment);
        given(modelMapper.map(payment, PaymentDTO.class)).willReturn(paymentDTO);

        //WHEN
        ResponseEntity<?> actual = paymentController.registerNewPayment(paymentDTO);

        //THEN
        assertEquals(HttpStatus.CONFLICT, actual.getStatusCode());

    }

    @Test
    void getClientAllPayment() throws Exception {

        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(6);
        payment.setPayment(2356.6);
        payment.setCompleted(1);

        paymentRepository.save(payment);
        List<Payment> paymentList;
        paymentList = paymentRepository.findPaymentsByClientId(1L);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/payment/all/{clientId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentList));
        //WHEN

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //THEN
        assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    void clientPayingBill() throws Exception {

        //GIVEN
        Payment payment = new Payment();
        payment.setId(3L);
        payment.setClientId(2L);
        payment.setYear(2023);
        payment.setMonth(6);
        payment.setPayment(2356.6);
        payment.setCompleted(0);

        given(paymentRepository.findPaymentByClientId(2L)).willReturn(Optional.of(payment));
        given(paymentService.clientPayingBill(2L, 3L)).willReturn(payment);

        //WHEN
        Payment actual = paymentController.clientPayingBill(2L, 3L);

        //THEN
        assertEquals(1, actual.getCompleted());

    }

    @Test
    void getClientAllPaidBill() throws Exception {

        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(6);
        payment.setPayment(2356.6);
        payment.setCompleted(1);

        paymentRepository.save(payment);
        List<Payment> paymentList;
        paymentList = paymentRepository.findPaymentsByClientId(1L);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/payment/bill/paid/{clientId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentList));
        //WHEN

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //THEN
        assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    void getClientAllNotPaidBill() throws Exception {

        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(6);
        payment.setPayment(2356.6);
        payment.setCompleted(0);

        paymentRepository.save(payment);
        List<Payment> paymentList;
        paymentList = paymentRepository.findPaymentsByClientId(1L);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/payment/bill/notpaid/{clientId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentList));
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