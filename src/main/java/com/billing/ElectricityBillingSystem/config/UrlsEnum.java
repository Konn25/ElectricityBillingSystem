package com.billing.ElectricityBillingSystem.config;

import lombok.Getter;

@Getter
public enum UrlsEnum {

    CLIENT("/v1/clientdata/{clientId}","/v1/clients","/v1/client/meter/{clientId}","/v1/client/find/{clientId}","/v1/consumptions/{meterId}",
            "/v1/consumption/{meterId}/{year}/{month}", "/v1/consumption/{clientId}/create","/v1/consumption/{meterId}/{year}",
            "/v1/consumption/allconsumption/{meterId}/{year}", "/v1/paymentcategory/payment/{clientId}/{year}/{month}",
            "/v1/payment/all/{clientId}","/v1/payment/paying/{clientId}/{paymentId}","/v1/payment/bill/paid/{clientId}",
            "/v1/payment/bill/notpaid/{clientId}"

    ),
    NOT_LOGGED_IN("/v1/authenticate", "/v1/createclient", "/v1/paymentcategory/all",
            "/v1/paymentcategory/registration", "/v1/payment/register",
            "/swagger-ui/**", "/v3/api-docs/**","/v2/api-docs/**", "/swagger-resources/**");

    private final String[] urls;

    UrlsEnum(String... urls) {
        this.urls = urls;
    }
}
