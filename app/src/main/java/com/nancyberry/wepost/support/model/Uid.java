package com.nancyberry.wepost.support.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nan.zhang on 4/7/16.
 */
public class Uid {
    @SerializedName("uid")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
