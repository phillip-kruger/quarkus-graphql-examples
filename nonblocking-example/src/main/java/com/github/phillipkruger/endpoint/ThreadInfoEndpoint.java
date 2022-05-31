package com.github.phillipkruger.endpoint;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

/**
 * Thread Info GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class ThreadInfoEndpoint {

    @Query
    public ThreadInfo threadInfo() {
        return getThreadInfo();
    }
    
    @Query
    public Uni<ThreadInfo> threadInfoUni() {
       return Uni.createFrom().item(getThreadInfo());
    }
    
    @Query
    @NonBlocking
    public ThreadInfo threadInfoNonBlocking() {
       return getThreadInfo();
    }
    
    @Query
    @Blocking
    public Uni<ThreadInfo> threadInfoBlocking() {
       return Uni.createFrom().item(getThreadInfo());
    }
    
    public ThreadInfo threadInfoChild(@Source ThreadInfo ti) {
        return getThreadInfo();
    }
    
    public Uni<ThreadInfo> threadInfoUniChild(@Source ThreadInfo ti) {
       return Uni.createFrom().item(getThreadInfo());
    }
    
    @NonBlocking
    public ThreadInfo threadInfoNonBlockingChild(@Source ThreadInfo ti) {
       return getThreadInfo();
    }
    
    @Blocking
    public Uni<ThreadInfo> threadInfoBlockingChild(@Source ThreadInfo ti) {
       return Uni.createFrom().item(getThreadInfo());
    }
    
    private ThreadInfo getThreadInfo(){
        Map<String,String> info = new HashMap<>();
        
        Thread currentThread = Thread.currentThread();
        info.put("name", currentThread.getName());
        info.put("id", String.valueOf(currentThread.getId()));
        info.put("state", currentThread.getState().name());
        info.put("priority", String.valueOf(currentThread.getPriority()));
        info.put("threadGroup", currentThread.getThreadGroup().getName());
        info.put("isAlive", String.valueOf(currentThread.isAlive()));
        info.put("isDaemon", String.valueOf(currentThread.isDaemon()));
        return new ThreadInfo(info);  
    }
    
}
