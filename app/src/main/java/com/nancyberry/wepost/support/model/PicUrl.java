package com.nancyberry.wepost.support.model;

/**
 * Created by nan.zhang on 4/6/16.
 */

import com.google.gson.annotations.SerializedName;

public class PicUrl {

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