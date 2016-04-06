package com.nancyberry.wepost.support.model;

/**
 * Created by nan.zhang on 4/6/16.
 */
import com.google.gson.annotations.SerializedName;

public class Visible {

    @SerializedName("type")
//    @Expose
    private Integer type;
    @SerializedName("list_id")
//    @Expose
    private Integer listId;

    /**
     *
     * @return
     * The type
     */
    public Integer getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The listId
     */
    public Integer getListId() {
        return listId;
    }

    /**
     *
     * @param listId
     * The list_id
     */
    public void setListId(Integer listId) {
        this.listId = listId;
    }

}