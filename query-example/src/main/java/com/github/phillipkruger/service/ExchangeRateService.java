package com.github.phillipkruger.service;

import com.github.phillipkruger.model.CurrencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import io.quarkus.logging.Log;
import java.util.concurrent.CompletionStage;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class ExchangeRateService {

    private final Random random = new Random();
    
    public ExchangeRate getExchangeRate(CurrencyCode base, CurrencyCode forCurencyCode){
        try {
            return getFutureExchangeRate(base, forCurencyCode).toCompletableFuture().get(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public CompletionStage<ExchangeRate> getFutureExchangeRate(CurrencyCode base, CurrencyCode forCurrencyCode){
        Log.info(">>>>> Getting exchange rate for " + forCurrencyCode + " against " + base);
        return CompletableFuture.completedFuture(generateMockRate(base, forCurrencyCode));
    }

    private ExchangeRate generateMockRate(CurrencyCode base, CurrencyCode against) {
        double rate = 0.5 + (1.5 * random.nextDouble()); // Generates a rate between 0.5 and 2.0
        ExchangeRate exchangeRate = new ExchangeRate(against, base, round(rate));
        Log.infof("<<<<< Got exchange rate: %s", exchangeRate);
        return exchangeRate;
    }

    private double round(double value) {
        return Math.round(value * 10000.0) / 10000.0; // Round to 4 decimal places
    }
    
}
