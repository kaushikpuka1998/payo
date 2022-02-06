package com.kgstrivers.payoneer.Models;

import android.content.Context;

public class SearchinRecycle {

    String url;
    String searchkeywords;


    public SearchinRecycle(String url, String searchkeywords) {
        this.url = url;
        this.searchkeywords = searchkeywords;

    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSearchkeywords() {
        return searchkeywords;
    }

    public void setSearchkeywords(String searchkeywords) {
        this.searchkeywords = searchkeywords;
    }
}