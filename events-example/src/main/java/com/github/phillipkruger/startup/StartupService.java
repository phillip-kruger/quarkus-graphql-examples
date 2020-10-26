package com.github.phillipkruger.startup;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.graphql.execution.ExecutionService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

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
        JsonObject response = executionService.execute(request);
        
        System.err.println(">>>>> " + response);
        
    }
    
    private static final String ALL_NAMES = "{\n" +
                                            "people{\n" +
                                            "    names\n" +
                                            "  }\n" +
                                            "}";
}
