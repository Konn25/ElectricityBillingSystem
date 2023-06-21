package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Client;
import com.billing.ElectricityBillingSystem.jpa.ClientRepository;
import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.jpa.MeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientServiceInterface {

    private final ClientRepository clientRepository;

    private final MeterRepository meterRepository;

    @Override
    public Client createClient(Client newClient) {
        return clientRepository.save(newClient);
    }

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public Meter getClientMeter(Long client_id) {


        Optional<Meter> clientMeter = meterRepository.findMeterByClientId(client_id);

        return clientMeter.orElse(null);
    }

    @Override
    public Optional<Client> findClientById(Long clientId) {
        return clientRepository.findClientById(clientId);
    }
}
