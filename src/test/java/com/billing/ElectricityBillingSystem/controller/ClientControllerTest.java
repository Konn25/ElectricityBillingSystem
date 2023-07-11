package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.ClientDTO;
import com.billing.ElectricityBillingSystem.jpa.*;
import com.billing.ElectricityBillingSystem.service.ClientService;
import com.billing.ElectricityBillingSystem.service.MeterService;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private MeterService meterService;

    @Mock
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<Client> clientList;
    private final Client client = new Client();
    private final Client client2 = new Client();

    private ClientDTO clientDTO = new ClientDTO();

    private ClientDTO clientDTO2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        this.clientService = new ClientService(clientRepository, meterRepository);
        this.clientController = new ClientController(modelMapper, clientService, meterService);

        this.clientList = new ArrayList<>();

        this.client.setId(1L);
        this.client.setName("Test");
        this.client.setMeterId(1L);
        this.client.setPostalCode(1234);
        this.client.setCity("Test city");
        this.client.setStreet("Test street");
        this.client.setHouseNumber(2);
        this.client.setPaymentCategoryId(1);

        this.client2.setId(2L);
        this.client2.setName("Test2");
        this.client2.setPostalCode(1234);
        this.client2.setCity("Test city");
        this.client2.setStreet("Test street3");
        this.client2.setHouseNumber(22);
        this.client2.setPaymentCategoryId(1);

        this.clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setName("Test");
        clientDTO.setMeterId(1L);
        clientDTO.setPostalCode(1234);
        clientDTO.setCity("Test city");
        clientDTO.setStreet("Test street");
        clientDTO.setHouseNumber(2);
        clientDTO.setPaymentCategoryId(1);

        this.clientDTO2 = new ClientDTO();
        this.clientDTO2.setId(2L);
        this.clientDTO2.setMeterId(2L);
        this.clientDTO2.setName("Test2");
        this.clientDTO2.setPostalCode(1234);
        this.clientDTO2.setCity("Test city");
        this.clientDTO2.setStreet("Test street3");
        this.clientDTO2.setHouseNumber(22);
        this.clientDTO2.setPaymentCategoryId(1);

    }


    @Test
    void createClient_Created() {
        //GIVEN
        Meter meter = new Meter();
        meter.setId(2L);
        meter.setClientId(2L);

        given(modelMapper.map(this.clientDTO2, Client.class)).willReturn(client2);
        given(clientRepository.findAll()).willReturn(List.of(this.client2));
        given(clientService.getAllClient()).willReturn(List.of(client));
        given(modelMapper.map(client2, ClientDTO.class)).willReturn(clientDTO2);
        given(clientService.createClient(client2)).willReturn(client2);
        given(clientRepository.save(client2)).willReturn(client2);
        given(meterService.findMeterByClientId(2L)).willReturn(Optional.of(meter));
        given(clientService.setClientMeter(client2, 2L)).willReturn(client2);

        //WHEN
        ResponseEntity<?> actual = clientController.createClient(clientDTO2);

        //THEN
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());

    }

    @Test
    void createClient_Bad_Request() {
        //GIVEN
        given(modelMapper.map(this.clientDTO, Client.class)).willReturn(client);
        given(clientRepository.findAll()).willReturn(List.of(this.client));
        given(clientService.getAllClient()).willReturn(List.of(client));

        //WHEN
        ResponseEntity<?> actual = clientController.createClient(clientDTO);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void getAllClient() throws Exception {
        //GIVEN
        clientRepository.save(this.client);
        this.clientList = clientService.getAllClient();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientList));
        //WHEN

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //THEN
        assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    void getClientMeter() throws Exception {
        //GIVEN
        Meter meter = new Meter();
        meter.setClientId(1L);
        meter.setId(1L);

        meterRepository.save(meter);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/client/meter/{clientId}", 1);

        //WHEN
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //THEN
        assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    void findClientById() throws Exception {
        //GIVEN
        clientRepository.save(this.client);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/client/find/{clientId}", 1);

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