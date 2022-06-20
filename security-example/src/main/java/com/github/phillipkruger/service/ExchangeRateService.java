package com.github.phillipkruger.service;

import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Service that return the exchange rate against a certain base
 *
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class ExchangeRateService {
    
    @Inject
    Vertx vertx;

    @ConfigProperty(name = "exchangerate.api.key", defaultValue = "I0kFZLJP1KHGI1oqil4SbTvLlDcYbEim")
    String apiKey;
    
    private Map<CurencyCode,Map<CurencyCode,ExchangeRate>> cache = new HashMap<>();
    
    public Map<CurencyCode,ExchangeRate> getExchangeRate(CurencyCode base) throws InterruptedException, ExecutionException{
        if(cache.containsKey(base)){
            return cache.get(base);
        }else{
        
            WebClient client = WebClient.create(vertx);

            HttpRequest<Buffer> httpRequest = client.get(80, "api.apilayer.com", "/exchangerates_data/latest")
                    .addQueryParam("base", base.toString())
                    .putHeader("apikey", apiKey);

            Future<HttpResponse<Buffer>> futureResponse = httpRequest.send();

            Function<AsyncResult<HttpResponse<Buffer>>,Future<Map<CurencyCode, ExchangeRate>>> f = new Function<>(){
                public Future<Map<CurencyCode,ExchangeRate>> apply(AsyncResult<HttpResponse<Buffer>> futureResponse){
                    HttpResponse<Buffer> result = futureResponse.result();
                    JsonObject jsonResponse = result.bodyAsJsonObject();
                    Map<CurencyCode, Double> map = new HashMap<>();
                    Map<String, Object> rates = jsonResponse.getJsonObject("rates").getMap();

                    Map<CurencyCode,ExchangeRate> mappedRates = new HashMap<>();

                    for(Map.Entry<String, Object> rate:rates.entrySet()){
                        CurencyCode cc = CurencyCode.valueOf(rate.getKey());
                        Double d = Double.valueOf(String.valueOf(rate.getValue()));
                        ExchangeRate exchangeRate = new ExchangeRate(cc, base, d);
                        mappedRates.put(cc, exchangeRate);
                    }

                    return Future.succeededFuture(mappedRates);

                }
            };

            Map<CurencyCode, ExchangeRate> rates = futureResponse.transform(f).toCompletionStage().toCompletableFuture().get();
            
            cache.put(base, rates);
            
            return rates;
        }
    }

}
