package com.nancyberry.wepost.support.model;

/**
 * Created by nan.zhang on 4/6/16.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PicUrl implements Serializable {

    private static final long serialVersionUID = 3353028445443180643L;

    @SerializedName("thumbnail_pic")
//    @Expose
    private String thumbnailPic;

    /**
     *
     * @return
     * The thumbnailPic
     */
    public String getThumbnailPic() {
        return thumbnailPic;
    }

    /**
     *
     * @param thumbnailPic
     * The thumbnail_pic
     */
    public void setThumbnailPic(String thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }

}