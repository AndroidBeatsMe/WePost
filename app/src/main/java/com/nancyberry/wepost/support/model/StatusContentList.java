package com.nancyberry.wepost.support.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nan.zhang on 4/7/16.
 */
public class StatusContentList {
    @SerializedName("statuses")
    public List<StatusContent> value;

    public List<StatusContent> getValue() {
        return value;
    }

    public void setValue(List<StatusContent> value) {
        this.value = value;
    }
}
