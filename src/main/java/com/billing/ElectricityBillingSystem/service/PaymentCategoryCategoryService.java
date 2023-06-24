package com.billing.ElectricityBillingSystem.service;

import com.billing.ElectricityBillingSystem.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentCategoryCategoryService implements PaymentCategoryServiceInterface {

    private final PaymentCategoryRepository paymentCategoryRepository;

    private final ClientRepository clientRepository;

    private final ConsumptionRepository consumptionRepository;

    @Override
    public PaymentCategory createNewPaymentCategory(double price) {

        PaymentCategory paymentCategory = new PaymentCategory();
        paymentCategory.setPrice(price);

        return paymentCategoryRepository.save(paymentCategory);
    }

    @Override
    public List<PaymentCategory> getAllPaymentCategory() {
        return paymentCategoryRepository.findAll();
    }

    @Override
    public Double calculatePaymentByPaymentCategory(Long clientId, int year, int month) {

        double paymentCost = 0.0;

        Optional<Client> findClient = clientRepository.findClientById(clientId);
        Optional<PaymentCategory> findPaymentCategory = paymentCategoryRepository.findPaymentCategoryById((long) findClient.get().getPaymentCategoryId());
        Optional<Consumption> findConsumption = consumptionRepository.findConsumptionByMeterIdAndYearAndMonth(findClient.get().getMeterId(), year, month);


        if (findConsumption.isPresent() && findClient.isPresent() && findPaymentCategory.isPresent()) {
            if (findClient.get().getPaymentCategoryId() == findPaymentCategory.get().getId()) {
                paymentCost = findConsumption.get().getConsumption() * findPaymentCategory.get().getPrice();
            }
        }


        return paymentCost;
    }
}
