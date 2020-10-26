package com.github.phillipkruger.service;

import com.github.phillipkruger.model.CurencyCode;
import io.smallrye.graphql.api.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Service that return the exchange rate against a certain base
 *
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class ExchangeRateService {

    @Inject
    Context context;
    
    @Inject
    Vertx vertx;

    public Map<CurencyCode, Double> getExchangeRates(CurencyCode base) {
        System.err.println("context in exchange rate service = " + context);
        try {
            CompletableFuture<Map<CurencyCode, Double>> futureExchangeRates = getFutureExchangeRates(base);
            return futureExchangeRates.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public CompletableFuture<Map<CurencyCode, Double>> getFutureExchangeRates(CurencyCode base) {
        Future<Map<CurencyCode, Double>> future = Future.future();
        WebClient client = WebClient.create(vertx);
        client.get(80, "api.exchangeratesapi.io", "/latest")
                .addQueryParam("base", base.toString())
                .send((var e) -> {
                    if(e.succeeded()){
                        HttpResponse<Buffer> response = e.result();
                        JsonObject jsonResponse = response.bodyAsJsonObject();
                        Map<CurencyCode, Double> map = new HashMap<>();
                        Map<String,Object> jsonMap = jsonResponse.getJsonObject("rates").getMap();
                        for(Map.Entry<String, Object> entry:jsonMap.entrySet()){
                            map.put(CurencyCode.valueOf(entry.getKey()), Double.valueOf(entry.getValue().toString()));
                        }
                        future.complete(map);
                    }else if(e.failed()){
                        future.failed();
                    }
                });
        
        return future.toCompletionStage().toCompletableFuture();
    }

}
