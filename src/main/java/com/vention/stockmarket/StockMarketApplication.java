package com.vention.stockmarket;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(
                title = "Stock market project",
                description = "Stock market APIs",
                contact = @Contact(
                        name = "Abbos Akramov",
                        email = "abbosakramov121@gmail.com"
                )

        )
)
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class StockMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class, args);
    }

}
