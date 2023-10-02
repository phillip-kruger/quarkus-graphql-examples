package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.model.ISO6391;
import com.github.phillipkruger.model.Language;
import com.github.phillipkruger.service.LanguageService;
import java.util.Map;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

/**
 * Language GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class LanguageEndpoint {

    @Inject
    LanguageService languageService;
    
    @Query
    public Map<ISO6391, Language> language() {
        return languageService.getLanguages();
    }
}
