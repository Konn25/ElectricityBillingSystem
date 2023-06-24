package com.billing.ElectricityBillingSystem.controller;

import com.billing.ElectricityBillingSystem.dto.ConsumptionDTO;
import com.billing.ElectricityBillingSystem.jpa.Consumption;
import com.billing.ElectricityBillingSystem.service.ConsumptionService;
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
@RequiredArgsConstructor
public class ConsumptionController {

    private final ModelMapper modelMapper;

    private final ConsumptionService consumptionService;


    @GetMapping("/consumptions/{meterId}")
    @ResponseBody
    public Optional<Consumption> getAllConsumption(@PathParam(value = "meterId") Long meterId) {
        return consumptionService.getAllConsumption(meterId);
    }


    @GetMapping("/consumption/{meterId}/{year}/{month}")
    @ResponseBody
    public ResponseEntity<String> checkConsumptionByYearAndMonth(@PathVariable(value = "meterId") Long meterId,
                                              @PathVariable(value = "year") int year,
                                              @PathVariable(value = "month") int month) {

        Optional<Consumption> consumption = consumptionService.getConsumptionByYearAndMonth(meterId, year, month);

        if (consumption.isPresent()) {
            Map<String,?> map = Map.of(
                    "year", consumption.get().getYear(),
                    "day", consumption.get().getDay(),
                    "month", consumption.get().getMonth(),
                    "consumption", consumption.get().getConsumption()
            );
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.valueOf(map));
        } else {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Year or month not found or data missing!");
        }

    }


    @PostMapping("/consumption/create")
    @ResponseBody
    public ResponseEntity<String> createNewConsumption(@RequestBody ConsumptionDTO consumptionDTO){

        Consumption consumptionRequest = modelMapper.map(consumptionDTO, Consumption.class);

        Optional<Consumption> consumptionFound = consumptionService.checkConsumption(consumptionRequest);

        if (consumptionFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This year and month has already meter reading data!");
        }

        Consumption newConsumption = consumptionService.createNewConsumption(consumptionRequest);

        ConsumptionDTO consumptionResponse = modelMapper.map(newConsumption, ConsumptionDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(consumptionResponse));
    }


    @GetMapping("/consumption/{meterId}/{year}")
    @ResponseBody
    public List<Consumption> getConsumptionByYear(@PathVariable(value = "meterId") Long meterId, @PathVariable(value = "year") int year){
        return consumptionService.getConsumptionByYear(meterId, year);
    }


    @GetMapping("/consumption/allconsumption/{meterId}/{year}")
    @ResponseBody
    public ResponseEntity<String> getAllConsumptionByYear(@PathVariable(value = "meterId") Long meterId, @PathVariable(value = "year") int year){

        double allConsumption = consumptionService.getAllConsumptionByYear(meterId, year);

        return ResponseEntity.status(HttpStatus.OK).body(" "+(Map.of("year:", year, "total:", allConsumption)));
    }




}
