package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.ClientDTO;
import com.billing.ElectricityBillingSystem.jpa.Client;
import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.service.ClientService;
import com.billing.ElectricityBillingSystem.service.MeterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200",allowCredentials="true" )
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name = "Client API", description = "The Client API.")
public class ClientController {

    private final ModelMapper modelMapper;

    private final ClientService clientService;

    private final MeterService meterService;

    @PostMapping("/createclient")
    @ResponseBody
    @Operation(summary = "Create new client.", description = "Create new client to the database")
    @ApiResponse(responseCode = "201", description = "Create new client")
    @ApiResponse(responseCode = "400", description = "Client already registered or something went wrong.")
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

    @GetMapping("/clientdata/{clientId}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get client's data", description = "Get client's important datat")
    @ApiResponse(responseCode = "200", description = "Get all client from database")
    public ResponseEntity<byte[]> getQRCode(@PathVariable("clientId") Long customerID) throws IOException {

        File f = clientService.generateQRCode(customerID);
        f.deleteOnExit();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(Files.readAllBytes(f.toPath()));
    }


    @GetMapping("/clients")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get all registered client", description = "Return all registered client")
    @ApiResponse(responseCode = "200", description = "Get all client from database")
    public List<Client> getAllClient(){
        return clientService.getAllClient();
    }

    @GetMapping("/client/meter/{clientId}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get client's meter by client id.", description = "Return client's meter")
    @ApiResponse(responseCode = "200", description = "Get client's meter by client id.")
    public Meter getClientMeter(@PathVariable(name = "clientId") Long clientId){
        return clientService.getClientMeter(clientId);
    }

    @GetMapping("/client/find/{clientId}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get client's data by client id.", description = "Return client's data")
    @ApiResponse(responseCode = "200", description = "Get client's data by client id")
    public Optional<Client> findClientById(@PathVariable(name = "clientId") Long clientId){
        return clientService.findClientById(clientId);
    }

    @GetMapping("/client/data/{email}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get client's data by client id.", description = "Return client's data")
    @ApiResponse(responseCode = "200", description = "Get client's data by client id")
    public Optional<Client> findClientByEmail(@PathVariable(name = "email") String email){
        System.out.println("FOUND IT: "+clientService.findClientByEmail(email));
        return clientService.findClientByEmail(email);
    }


}
