package com.github.phillipkruger.resource;

import com.github.phillipkruger.model.Score;
import com.github.phillipkruger.service.ScoreService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/score")
public class ScoreResource {
    @Inject 
    ScoreService scoreService;
    
    @GET 
    @Path("/{idNumber}")
    public List<Score> getScores(@PathParam("idNumber") String idNumber){
        return scoreService.getScores(idNumber);
    }
}
