package com.github.phillipkruger.model;

import org.eclipse.microprofile.graphql.Name;

public class PageInfo {
    public int currentPageNumber;
    public int nextPageNumber;
    @Name("isLastPage")
    public boolean isLastPage;
    @Name("isFirstPage")
    public boolean isFirstPage;
    
    public int itemsOnPage;
    
    public int totalNumberOfPages;
    public int totalNumberOfItems;
}
