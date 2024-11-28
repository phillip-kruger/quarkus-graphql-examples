package com.github.phillipkruger.service;

import com.github.phillipkruger.model.CurrencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import io.quarkus.logging.Log;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Service that return the exchange rate against a certain base
 *
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class ExchangeRateService {

    @Inject
    Vertx vertx;

    public ExchangeRate getExchangeRate(CurrencyCode base, CurrencyCode forCurencyCode){
        try {
            return getFutureExchangeRate(base, forCurencyCode).toCompletableFuture().get(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public CompletionStage<ExchangeRate> getFutureExchangeRate(CurrencyCode base, CurrencyCode forCurencyCode){
        Log.info(">>>>> Getting exchange rate for " + forCurencyCode + " against " + base);
         
        WebClient client = WebClient.create(vertx);
        HttpRequest<Buffer> httpRequest = client.get(80, "open.er-api.com", "/v6/latest/" + base.toString());
        Future<HttpResponse<Buffer>> futureResponse = httpRequest.send();
        Function<AsyncResult<HttpResponse<Buffer>>,Future<ExchangeRate>> f = new Function<>(){
            public Future<ExchangeRate> apply(AsyncResult<HttpResponse<Buffer>> futureResponse){
                HttpResponse<Buffer> result = futureResponse.result();
                JsonObject jsonResponse = result.bodyAsJsonObject();
                JsonObject rates = jsonResponse.getJsonObject("rates");
                Double rate = rates.getDouble(forCurencyCode.toString());
                ExchangeRate exchangeRate = new ExchangeRate(forCurencyCode, base, rate);
                Log.info("<<<<< Got exchange rate for " + forCurencyCode + " against " + base);
                return Future.succeededFuture(exchangeRate);
            }
        };
        
        return futureResponse.transform(f).toCompletionStage();
    }

}
