package com.l.lookandtake.entity;

import java.util.List;

/**
 * Created by L on 2017/8/3.
 */

public class GankData {
    private boolean error;

    private List<Gank> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Gank> getResults() {
        return results;
    }

    public void setResults(List<Gank> results) {
        this.results = results;
    }

}
