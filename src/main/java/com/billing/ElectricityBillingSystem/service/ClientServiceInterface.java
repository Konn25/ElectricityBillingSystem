package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Client;
import com.billing.ElectricityBillingSystem.jpa.Meter;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ClientServiceInterface {

    File generateQRCode(Long id);

    Client createClient(Client newClient);
    List<Client> getAllClient();

    Meter getClientMeter(Long clientId);

    Optional<Client> findClientById(Long clientId);

    Client setClientMeter(Client client,Long meterId);

}
