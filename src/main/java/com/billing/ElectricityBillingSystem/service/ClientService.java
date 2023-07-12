package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.Client;
import com.billing.ElectricityBillingSystem.jpa.ClientRepository;
import com.billing.ElectricityBillingSystem.jpa.Meter;
import com.billing.ElectricityBillingSystem.jpa.MeterRepository;
import com.billing.ElectricityBillingSystem.qrcode.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientServiceInterface {

    private final ClientRepository clientRepository;

    private final MeterRepository meterRepository;

    @Override
    public File generateQRCode(Long id) {
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        Optional<Client> optionalClient = clientRepository.findClientById(id);

        return qrCodeGenerator.byteArrayToQRCode("ID:"+ id+"\nName: "+optionalClient.get().getName()
                +"\nPostal code: "+optionalClient.get().getPostalCode()
                +"\nCity: "+optionalClient.get().getCity()
                +"\nStreet: "+optionalClient.get().getStreet()
                +"\nHouse number: "+optionalClient.get().getHouseNumber()
                +"\nMeter ID: "+optionalClient.get().getMeterId()
                +"\nPayment category ID: "+optionalClient.get().getPaymentCategoryId()
        );
    }

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

    @Override
    public Client setClientMeter(Client client, Long meterId) {

        Client getClient = client;

        getClient.setMeterId(meterId);

        return clientRepository.save(getClient);
    }
}
