package com.udacity.pricing.controller;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.service.PricingService;
import com.udacity.pricing.util.PriceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PricingController {
    private PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    /**
     * Gets the price for a requested vehicle.
     *
     * @param vehicleId ID number of the vehicle for which the price is requested
     * @return price of the vehicle, or error that it was not found.
     */
    @GetMapping
    public Price get(@RequestParam(name = "vehicleId") Long vehicleId) {
        try {
            return pricingService.getPrice(vehicleId);
        } catch (PriceException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Price Not Found", ex);
        }

    }

}
