package com.ucokm.myjavanewnews.DataModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespSourceNewsModel {
    @SerializedName("status")
    private String status;

    @SerializedName("sources")
    private List<SourceNews> sources = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SourceNews> getSources() {
        return sources;
    }

    public void setSources(List<SourceNews> sources) {
        this.sources = sources;
    }
}
