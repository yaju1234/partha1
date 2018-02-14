package com.oxilo.oioindia.modal.search;

/**
 * Created by root on 13/2/18.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}