package com.github.phillipkruger.service;

import com.github.phillipkruger.model.PageInfo;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@RequestScoped
@GraphQLApi
public class PageInfoService {

    private PageInfo pageInfo;
    
    void setPageInfo(PageInfo pageInfo){
        this.pageInfo = pageInfo;
    }
    
    @Query
    public PageInfo getPageInfo(){
        return pageInfo;
    }
}
