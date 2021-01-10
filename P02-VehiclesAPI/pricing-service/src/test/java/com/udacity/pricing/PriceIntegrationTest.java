package com.udacity.pricing;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.repository.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PriceIntegrationTest {
    @Autowired
    private PriceRepository priceRepository;

    @Test
    public void shouldInsertDataIntoDatabase() {
        Optional<Price> priceFromRepo = priceRepository.findById(21L);
        assertFalse(priceFromRepo.isPresent());

        final Price createdPrice = priceRepository.save(new Price(21L, "HRK",new BigDecimal(99)));
        priceFromRepo = priceRepository.findById(21L);
        assertTrue(priceFromRepo.isPresent());
        assertEquals(createdPrice.getVehicleId(), priceFromRepo.get().getVehicleId());
        assertEquals(createdPrice.getPrice(), new BigDecimal(99));
    }

    @Test
    public void shouldRetrieveOnePriceFromDatabase() {
        Optional<Price> priceFromRepo = priceRepository.findById(1L);
        assertTrue(priceFromRepo.isPresent());
    }

    @Test
    public void shouldRetrieveAllPricesFromDatabase() {
        final Iterable<Price> pricesIterator = priceRepository.findAll();
        assertEquals(pricesIterator.spliterator().estimateSize(), 20);
    }

    @Test
    public void shouldDeleteOnePriceFromDatabase() {
        Iterable<Price> pricesIterator = priceRepository.findAll();
        assertEquals(pricesIterator.spliterator().estimateSize(), 20);
        priceRepository.deleteById(1L);
        pricesIterator = priceRepository.findAll();
        assertEquals(pricesIterator.spliterator().estimateSize(), 19);
    }

    @Test
    public void shouldDeleteAllPricesFromDatabase() {
        Iterable<Price> pricesIterator = priceRepository.findAll();
        assertEquals(pricesIterator.spliterator().estimateSize(), 20);
        priceRepository.deleteAll();
        pricesIterator = priceRepository.findAll();
        assertEquals(pricesIterator.spliterator().estimateSize(), 0);
    }
}