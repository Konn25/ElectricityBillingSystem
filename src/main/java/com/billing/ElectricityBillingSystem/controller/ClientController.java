package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.ClientDTO;
import com.billing.ElectricityBillingSystem.jpa.Client;
import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.service.ClientService;
import com.billing.ElectricityBillingSystem.service.MeterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ClientController {

    private final ModelMapper modelMapper;

    private final ClientService clientService;

    private final MeterService meterService;

    @PostMapping("/createclient")
    @ResponseBody
    public ResponseEntity<String> createClient(@RequestBody ClientDTO clientDTO){

        Client clientRequest = modelMapper.map(clientDTO,Client.class);

        List<Client> clientList = clientService.getAllClient();

       for(Client value: clientList){
            if(value.getPostalCode()==clientRequest.getPostalCode() && value.getCity().equals(clientRequest.getCity()) &&
                    value.getStreet().equals(clientRequest.getStreet()) && value.getHouseNumber() == clientRequest.getHouseNumber()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This address is already registered!");
            }
        }

        Client newClient = clientService.createClient(clientRequest);

        ClientDTO clientResponse = modelMapper.map(newClient, ClientDTO.class);

        meterService.createNewMeter(clientResponse.getId());

        Optional<Meter> findClientMeter = meterService.findMeterByClientId(clientResponse.getId());

        clientService.setClientMeter(newClient,findClientMeter.get().getId());

        clientService.createClient(newClient);

        return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(clientResponse));
    }


    @GetMapping("/clients")
    @ResponseBody
    public List<Client> getAllClient(){
        return clientService.getAllClient();
    }

    @GetMapping("/client/meter/{clientId}")
    @ResponseBody
    public Meter getClientMeter(@PathVariable(name = "clientId") Long clientId){
        return clientService.getClientMeter(clientId);
    }

    @GetMapping("/client/find/{clientId}")
    @ResponseBody
    public Optional<Client> findClientById(@PathVariable(name = "clientId") Long clientId){
        return clientService.findClientById(clientId);
    }


}
