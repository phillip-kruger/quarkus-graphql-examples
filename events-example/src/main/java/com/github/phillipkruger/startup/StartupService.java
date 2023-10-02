package com.github.phillipkruger.startup;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.graphql.execution.ExecutionResponse;
import io.smallrye.graphql.execution.ExecutionResponseWriter;
import io.smallrye.graphql.execution.ExecutionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

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
