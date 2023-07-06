package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.jpa.ClientRepository;
import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.service.ClientService;
import com.billing.ElectricityBillingSystem.service.MeterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name = "Meter API", description = "The Meter API.")
public class MeterController {

    private final MeterService meterService;

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @PostMapping("/meter/create/{clientId}")
    @ResponseBody
    @Operation(summary = "Create new meter for customer", description = "Create new meter for customer")
    @ApiResponse(responseCode = "201", description = "Create new meter for customer")
    @ApiResponse(responseCode = "400", description = "Already registered")
    public ResponseEntity<?> createNewMeter(@PathVariable(value = "clientId") Long clientId) {

        Meter newMeter = meterService.createNewMeter(clientId);

        if(clientService.findClientById(clientId).isPresent() && clientService.findClientById(clientId).get().getMeterId()==0){
            clientService.findClientById(clientId).get().setMeterId(newMeter.getId());
            clientRepository.save(clientService.findClientById(clientId).get());

            return ResponseEntity.status(HttpStatus.CREATED).body("Created:\n"+(Map.of("id",newMeter.getId()," customerId", newMeter.getClientId())));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can not update client's meter id");
        }
    }

}
