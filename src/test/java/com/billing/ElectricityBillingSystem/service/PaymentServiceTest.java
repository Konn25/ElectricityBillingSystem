package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Payment;
import com.billing.ElectricityBillingSystem.jpa.PaymentRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;


    @BeforeEach
    void setUp() {
        this.paymentService = new PaymentService(this.paymentRepository);
    }

    @Test
    void createNewPayment() {
        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(2);
        payment.setPayment(2341.67);
        payment.setCompleted(0);

        //WHEN
        when(paymentRepository.save(payment)).thenReturn(payment);
        //THEN
        assertEquals(payment, paymentService.createNewPayment(payment));
    }

    @Test
    void getClientAllPayment() {
        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(2);
        payment.setPayment(2341.67);
        payment.setCompleted(0);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);

        //WHEN
        when(paymentRepository.findPaymentsByClientId(1L)).thenReturn(paymentList);
        //THEN
        assertEquals(paymentList,paymentService.getClientAllPayment(1L));

    }

    @Test
    void clientPayingBill() {
        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(2);
        payment.setPayment(2341.67);
        payment.setCompleted(0);


        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);

        Optional<Payment> paymentOptional = paymentList.stream().findAny();

        //WHEN
        when(paymentRepository.findPaymentByClientId(1L)).thenReturn(paymentOptional);
        when(paymentRepository.save(payment)).thenReturn(payment);

        //THEN
        assertEquals(payment,paymentService.clientPayingBill(1L,1L));
        assertEquals(null ,paymentService.clientPayingBill(1L,2L));

    }

    @Test
    void getClientAllPaidBill() {
        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(2);
        payment.setPayment(2341.67);
        payment.setCompleted(1);

        Payment payment2 = new Payment();
        payment2.setId(1L);
        payment2.setClientId(1L);
        payment2.setYear(2023);
        payment2.setMonth(3);
        payment2.setPayment(2563.67);
        payment2.setCompleted(1);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentList.add(payment2);

        //WHEN

        when(paymentRepository.findPaymentsByClientId(1L)).thenReturn(paymentList);

        //THEN
        assertEquals(paymentList,paymentService.getClientAllPaidBill(1L));
    }

    @Test
    void getClientAllNotPaidBill() {
        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(2);
        payment.setPayment(2341.67);
        payment.setCompleted(0);

        Payment payment2 = new Payment();
        payment2.setId(1L);
        payment2.setClientId(1L);
        payment2.setYear(2023);
        payment2.setMonth(3);
        payment2.setPayment(2563.67);
        payment2.setCompleted(0);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentList.add(payment2);

        //WHEN

        when(paymentRepository.findPaymentsByClientId(1L)).thenReturn(paymentList);

        //THEN
        assertEquals(paymentList,paymentService.getClientAllNotPaidBill(1L));
    }

    @Test
    void getAllBill() {
        //GIVEN
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setClientId(1L);
        payment.setYear(2023);
        payment.setMonth(2);
        payment.setPayment(2341.67);
        payment.setCompleted(0);

        Payment payment2 = new Payment();
        payment2.setId(1L);
        payment2.setClientId(1L);
        payment2.setYear(2023);
        payment2.setMonth(3);
        payment2.setPayment(2563.67);
        payment2.setCompleted(1);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        paymentList.add(payment2);

        //WHEN
        when(paymentRepository.findAll()).thenReturn(paymentList);

        //THEN
        assertEquals(paymentList, paymentService.getAllBill());
    }
}