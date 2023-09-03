package com.jamesaworo.demo;

import com.example.consumingwebservice.wsdl.GetCountryResponse;
import com.jamesaworo.demo.client.CountryClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(CountryClient client) {
        return args -> {
            String country = "Spain";
            if (args.length > 0) {
                country = args[0];
            }

            GetCountryResponse response = client.getCountry(country);
            if (!ObjectUtils.isEmpty(response) && !ObjectUtils.isEmpty(response.getCountry())) {
                System.out.println(response.getCountry().getCapital());
                System.out.println(response.getCountry().getName());
                System.out.println(response.getCountry().getPopulation());
                System.out.println(response.getCountry().getCurrency());
            } else {
				System.out.println("Could not find a country: "+ country);
			}
        };
    }
}
