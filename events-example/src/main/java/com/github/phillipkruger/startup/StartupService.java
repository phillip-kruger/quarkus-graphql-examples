package com.github.phillipkruger.startup;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.graphql.api.Context;
import io.smallrye.graphql.execution.ExecutionResponse;
import io.smallrye.graphql.execution.ExecutionResponseWriter;
import io.smallrye.graphql.execution.ExecutionService;
import io.smallrye.graphql.execution.JsonObjectResponseWriter;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Service that does things on startup
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class StartupService {

    @Inject ExecutionService executionService;
    
    public void init(@Observes StartupEvent event){
        
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("query", ALL_NAMES);
        JsonObject request = builder.build();
        ExecutionResponseWriter writer = new ExecutionResponseWriter(){
            @Override
            public void write(ExecutionResponse response) {
                System.err.println(">>>>> " + response.getExecutionResultAsString());
            }
        };
        executionService.execute(request, writer, false);
        
    }
    
    private static final String ALL_NAMES = "{\n" +
                                            "people{\n" +
                                            "    names\n" +
                                            "  }\n" +
                                            "}";
}
