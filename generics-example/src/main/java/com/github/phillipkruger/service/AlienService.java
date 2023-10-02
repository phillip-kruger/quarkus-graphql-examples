package com.github.phillipkruger.service;

import com.github.phillipkruger.model.Alien;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Service that facade the Alien database.
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class AlienService {
    
    public Alien getAlien(int id){
        return ALIENS.get(id);
    }
    
    private static final Map<Integer,Alien> ALIENS = new ConcurrentHashMap<>();
    static {
        
        Alien a1 = new Alien();
        a1.id = 1;
        a1.type = "Grey";
        a1.from = "Zeta Reticuli";

        Alien a2 = new Alien();
        a2.id = 2;
        a2.type = "Reptilian";
        a2.from = "Alpha Draconis";

        ALIENS.put(a1.id, a1);
        ALIENS.put(a2.id, a2);
    }
    
}
