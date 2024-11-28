package com.github.phillipkruger.service;

import com.github.phillipkruger.model.Score;
import io.quarkus.logging.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.UncheckedIOException;

@ApplicationScoped
public class ScoreService {
    
    private final Map<String,List<Score>> scoreDatabase = new HashMap<>();
    
    public List<Score> getScores(String idNumber){
        Log.info(">>>>> Getting scores [" + idNumber + "]");
        List<Score> scores = scoreDatabase.get(idNumber);
        Log.info("<<<<< Got scores [" + idNumber + "]");
        return scores;
    }
    
    public List<List<Score>> getScores(List<String> idNumbers){
        Log.info(">>>>> Getting scores " + idNumbers);
        List<List<Score>> allscores = new ArrayList<>();
        for(String idNumber:idNumbers){
            allscores.add(scoreDatabase.get(idNumber));
        }
        Log.info("<<<<< Got scores " + idNumbers);
        return allscores;
    }
    
    @PostConstruct
    void init(){
        try(InputStream jsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("score.json")){
            if(jsonStream!=null){
                List<List<Score>> loaded = JSONB.fromJson(jsonStream, new ArrayList<List<Score>>(){}.getClass().getGenericSuperclass());
                for(List<Score> s:loaded){
                    scoreDatabase.put(ids.pop(), s);
                }
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
    
    private static final Jsonb JSONB = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
    
    private static final Stack<String> ids = new Stack<>();
    static{
        ids.addAll(Arrays.asList(new String[]{"797-95-4822","373-95-3047","097-87-6795","347-01-8880","733-86-4423","560-99-2165","091-07-5401","539-70-2014","029-18-5986","287-58-0690"}));
    }
}