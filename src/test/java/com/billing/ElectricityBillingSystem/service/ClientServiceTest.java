package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Client;
import com.billing.ElectricityBillingSystem.jpa.ClientRepository;
import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.jpa.MeterRepository;
import com.billing.ElectricityBillingSystem.qrcode.QRCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private MeterRepository meterRepository;

    private QRCodeGenerator qrCodeGenerator;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        this.clientService = new ClientService(clientRepository,meterRepository);
        this.qrCodeGenerator = new QRCodeGenerator();
    }


    @Test
    void generateQRCode() throws IOException{
        //GIVEN
        Client client = new Client();
        client.setId(1L);
        client.setMeterId(1L);
        client.setPaymentCategoryId(1);
        client.setName("Test");
        client.setPostalCode(1234);
        client.setCity("Test city");
        client.setStreet("Test street");
        client.setHouseNumber(21);

        given(clientRepository.findClientById(1L)).willReturn(Optional.of(client));
        File qrCodeFile = qrCodeGenerator.byteArrayToQRCode("ID:"+ client.getId()+"\nName: "+client.getName()
                +"\nPostal code: "+client.getPostalCode()
                +"\nCity: "+client.getCity()
                +"\nStreet: "+client.getStreet()
                +"\nHouse number: "+client.getHouseNumber()
                +"\nMeter ID: "+client.getMeterId()
                +"\nPayment category ID: "+client.getPaymentCategoryId());

        //WHEN
        File newQRCode = clientService.generateQRCode(1L);
        FileInputStream fileInputStream = new FileInputStream(qrCodeFile);
        FileInputStream fileInputStream2 = new FileInputStream(newQRCode);

        byte[] file = new byte[(int) qrCodeFile.length()];
        byte[] file2 = new byte[(int) newQRCode.length()];

        fileInputStream.read(file);
        fileInputStream2.read(file2);
        fileInputStream.close();
        fileInputStream2.close();

        //THEN
        assertEquals(Arrays.toString(file),Arrays.toString(file2));

    }

    @Test
    void createClient() {
        //GIVEN
        Client newClient = new Client();
        newClient.setName("Test");
        newClient.setPostalCode(1234);
        newClient.setCity("Test city");
        newClient.setStreet("Test street");
        newClient.setHouseNumber(27);
        newClient.setMeterId(1L);

        //WHEN
        when(clientRepository.save(newClient)).thenReturn(newClient);
        //THEN
        assertEquals(newClient,clientService.createClient(newClient));
    }

    @Test
    void getAllClient() {
        //GIVEN
        Client client1 = new Client();
        client1.setName("Test");
        client1.setPostalCode(1234);
        client1.setCity("Test city");
        client1.setStreet("Test street");
        client1.setHouseNumber(27);
        client1.setMeterId(1L);

        Client client2 = new Client();
        client2.setName("Test2");
        client2.setPostalCode(1234);
        client2.setCity("Test city");
        client2.setStreet("Test street");
        client2.setHouseNumber(29);
        client2.setMeterId(2L);

        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);

        //WHEN
        when(clientRepository.findAll()).thenReturn(clientList);

        //THEN
        assertEquals(clientList,clientService.getAllClient());
    }

    @Test
    void getClientMeter() {
        //GIVEN
        Meter meter = new Meter(1L);

        Optional<Meter> meterOptional = Optional.of(meter);

        //WHEN
        when(meterRepository.findMeterByClientId(1L)).thenReturn(meterOptional);

        //THEN
        assertEquals(meter,clientService.getClientMeter(1L));
    }

    @Test
    void findClientById() {
        //GIVEN
        Client client = new Client();
        client.setName("Test");
        client.setPostalCode(1234);
        client.setCity("Test city");
        client.setStreet("Test street");
        client.setHouseNumber(27);
        client.setMeterId(1L);

        Optional<Client> clientOptional = Optional.of(client);

        //WHEN
        when(clientRepository.findClientById(1L)).thenReturn(clientOptional);

        //THEN
        assertEquals(clientOptional,clientService.findClientById(1L));
    }

    @Test
    void setClientMeter() {
        //GIVEN
        Client client = new Client();
        client.setName("Test");
        client.setPostalCode(1234);
        client.setCity("Test city");
        client.setStreet("Test street");
        client.setHouseNumber(27);
        client.setMeterId(1L);



        //WHEN
        when(clientRepository.save(client)).thenReturn(client);

        //THEN
        assertEquals(client,clientService.setClientMeter(client,1L));
    }
}