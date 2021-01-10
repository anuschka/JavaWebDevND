package com.udacity.pricing;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.repository.PriceRepository;
import com.udacity.pricing.util.BigDecimalUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.LongStream;

/**
 * Creates a Spring Boot Application to run the Pricing MicroService.
 */
@SpringBootApplication
public class PricingServiceApplication {

    @Autowired
    private PriceRepository priceRepository;

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

    @Bean
    InitializingBean generateInitialData() {
        return () -> {
            LongStream
                    .range(1, 20)
                    .forEach(i -> priceRepository.save(new Price("USD", BigDecimalUtils.randomPrice(), i)));
        };
    }

}