package com.nancyberry.wepost.support.model;

/**
 * Created by nan.zhang on 5/16/16.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusComment implements Serializable {

    private static final long serialVersionUID = -2320277741846548021L;

    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("id")
    private Long id;
    @SerializedName("text")
    private String text;
    @SerializedName("source")
    private String source;
    @SerializedName("user")
    private User user;
    @SerializedName("mid")
    private String mid;
    @SerializedName("idstr")
    private String idstr;
    @SerializedName("status")
    private StatusContent status;
//    @SerializedName("reply_comment")
//    @Expose
//    private Object replyComment;

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The source
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The mid
     */
    public String getMid() {
        return mid;
    }

    /**
     *
     * @param mid
     * The mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }


    /**
     *
     * @return
     * The idstr
     */
    public String getIdstr() {
        return idstr;
    }

    /**
     *
     * @param idstr
     * The idstr
     */
    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }


    /**
     *
     * @return
     * The status
     */
    public StatusContent getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(StatusContent status) {
        this.status = status;
    }


//    /**
//     *
//     * @return
//     * The replyComment
//     */
//    public String getReplyComment() {
//        return replyComment;
//    }
//
//    /**
//     *
//     * @param replyComment
//     * The reply_comment
//     */
//    public void setReplyComment(String replyComment) {
//        this.replyComment = replyComment;
//    }

}
