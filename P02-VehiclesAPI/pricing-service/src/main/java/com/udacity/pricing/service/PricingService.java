package com.udacity.pricing.service;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.repository.PriceRepository;
import com.udacity.pricing.util.PriceException;

import java.util.Optional;

public class PricingService {
    private PriceRepository priceRepository;

    public PricingService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    /**
     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
     *
     * @param vehicleId ID number of the vehicle the price is requested for.
     * @return price of the requested vehicle
     * @throws PriceException vehicleID was not found
     */
    public Price getPrice(Long vehicleId) throws PriceException {
        Optional<Price> price = priceRepository.findById(vehicleId);
        if (!price.isPresent()) {
            throw new PriceException("Cannot find price for Vehicle " + vehicleId);
        }

        return price.get();
    }
}
