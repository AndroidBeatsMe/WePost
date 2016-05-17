package com.nancyberry.wepost.support.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nan.zhang on 5/16/16.
 */
public class StatusCommentList {
    @SerializedName("comments")
    public List<StatusComment> value;
    @SerializedName("total_number")
    public Long totalNumber;

    public List<StatusComment> getValue() {
        return value;
    }

    public void setValue(List<StatusComment> value) {
        this.value = value;
    }

    public Long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
    }

}
