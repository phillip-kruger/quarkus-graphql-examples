package com.github.phillipkruger.endpoint;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
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
    
    public Uni<ThreadInfo> threadInfoNonBlockingChild(@Source ThreadInfo ti) {
       return Uni.createFrom().item(getThreadInfo());
    }
    
    public ThreadInfo threadInfoBlockingChild(@Source ThreadInfo ti) {
       return getThreadInfo();
    }
    
    private ThreadInfo getThreadInfo(){
        ThreadInfo info = new ThreadInfo();
        
        Thread currentThread = Thread.currentThread();
        info.setName(currentThread.getName());
        info.setId(currentThread.getId());
        info.setState(currentThread.getState().name());
        info.setPriority(currentThread.getPriority());
        info.setThreadGroup(currentThread.getThreadGroup().getName());
        info.setIsAlive(currentThread.isAlive());
        info.setIsDaemon(currentThread.isDaemon());
        return info;  
    }
    
}
