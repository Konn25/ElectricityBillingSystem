package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.ConsumptionDTO;
import com.billing.ElectricityBillingSystem.jpa.Consumption;
import com.billing.ElectricityBillingSystem.service.ConsumptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Tag(name = "Consumption API", description = "The Consumption API.")
public class ConsumptionController {

    private final ModelMapper modelMapper;

    private final ConsumptionService consumptionService;

    @GetMapping("/consumptions/{meterId}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get client's all consumptions by meter id", description = "Get client's all consumptions by meter id")
    @ApiResponse(responseCode = "200", description = "Get client's all consumptions by meter id")
    public List<Consumption> getAllConsumption(@PathParam(value = "meterId") Long meterId) {
        return  consumptionService.getAllConsumption(meterId);
    }


    @GetMapping("/consumption/{meterId}/{year}/{month}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get client's consumption by meter id, year and month", description = "Get client's all consumption in a specific year and month")
    @ApiResponse(responseCode = "200", description = "Get client's consumption by meter id, year and month")
    public ResponseEntity<String> checkConsumptionByYearAndMonth(@PathVariable(value = "meterId") Long meterId,
                                                                 @PathVariable(value = "year") int year,
                                                                 @PathVariable(value = "month") int month) {

        Optional<Consumption> consumption = consumptionService.getConsumptionByYearAndMonth(meterId, year, month);

        if (consumption.isPresent()) {
            Map<String, ?> map = Map.of(
                    "year", consumption.get().getYear(),
                    "day", consumption.get().getDay(),
                    "month", consumption.get().getMonth(),
                    "consumption", consumption.get().getConsumption()
            );
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.valueOf(map));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Year or month not found or data missing!");
        }

    }


    @PostMapping("/consumption/{clientId}/create")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "New consumption registration", description = "Add a new consumption to the database")
    @ApiResponse(responseCode = "201", description = "New consumption created")
    @ApiResponse(responseCode = "400", description = "Already have a reading data in this year and month")
    public ResponseEntity<String> createNewConsumption(@PathParam(value = "clientId") Long clientId, @RequestBody ConsumptionDTO consumptionDTO) {

        Consumption consumptionRequest = modelMapper.map(consumptionDTO, Consumption.class);

        Optional<Consumption> consumptionFound = consumptionService.checkConsumption(consumptionRequest);

        if (consumptionFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This year and month has already taken!");
        }

        Consumption newConsumption = consumptionService.createNewConsumption(consumptionRequest);

        ConsumptionDTO consumptionResponse = modelMapper.map(newConsumption, ConsumptionDTO.class);

        consumptionService.createPaymentToDatabase(clientId, consumptionRequest.getYear(), consumptionResponse.getMonth());

        return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(consumptionResponse));
    }


    @GetMapping("/consumption/{meterId}/{year}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get client consumption by year", description = "Get client consumption by meter id and year")
    @ApiResponse(responseCode = "200", description = "Get client consumption by year")
    public List<Consumption> getConsumptionByYear(@PathVariable(value = "meterId") Long meterId, @PathVariable(value = "year") int year) {
        return consumptionService.getConsumptionByYear(meterId, year);
    }


    @GetMapping("/consumption/allconsumption/{meterId}/{year}")
    @ResponseBody
    @SecurityRequirement(name = "bearerToken")
    @Operation(summary = "Get total consumption by year", description = "Get total consumption by meter id and year")
    @ApiResponse(responseCode = "200", description = "Get total consumption by year")
    public ResponseEntity<String> getAllConsumptionByYear(@PathVariable(value = "meterId") Long meterId, @PathVariable(value = "year") int year) {

        double allConsumption = consumptionService.getAllConsumptionByYear(meterId, year);

        return ResponseEntity.status(HttpStatus.OK).body(" " + (Map.of("year:", year, "total:", allConsumption)));
    }


}
