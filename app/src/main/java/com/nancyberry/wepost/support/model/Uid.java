package com.nancyberry.wepost.support.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nan.zhang on 4/7/16.
 */
public class Uid {
    @SerializedName("uid")
    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
