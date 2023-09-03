/*
 * @Author: james.junior
 * @Date: 8/28/23 17:38
 *
 * @Project: demo-ws-consumer
 */

package com.jamesaworo.demo.client;

import com.example.consumingwebservice.wsdl.GetCountryRequest;
import com.example.consumingwebservice.wsdl.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


public class CountryClient extends WebServiceGatewaySupport {
    private final Logger log = LoggerFactory.getLogger(CountryClient.class);

    public GetCountryResponse getCountry(String countryName){
        GetCountryRequest countryRequest = new GetCountryRequest();
        countryRequest.setName(countryName);

        log.info("Requesting location for " + countryName);

        GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/countries", countryRequest,
                        new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/GetCountryRequest"));
        return response;
    }
}
