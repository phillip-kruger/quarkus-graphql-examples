package com.github.phillipkruger.service;

import com.github.phillipkruger.model.ISO6391;
import com.github.phillipkruger.model.Language;
import java.util.HashMap;
import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Service that facade the Person database.
 * For this example we just use memory.
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class LanguageService {
    
    private static final Map<ISO6391, Language> LANG = new HashMap<>();
    
    public Map<ISO6391, Language> getLanguages(){
        return LANG;
    }
    
    static {    
        LANG.put(ISO6391.en, new Language(ISO6391.en, "english", "english", "please", "thank you"));
        LANG.put(ISO6391.af, new Language(ISO6391.af, "afrikaans", "afrikaans", "asseblief", "dankie"));
        LANG.put(ISO6391.de, new Language(ISO6391.de, "deutsch", "german", "bitte", "danke dir"));
        LANG.put(ISO6391.fr, new Language(ISO6391.fr, "français", "french", "s'il te plaît", "merci"));
    }
}
