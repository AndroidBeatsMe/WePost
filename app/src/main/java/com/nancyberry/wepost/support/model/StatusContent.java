package com.nancyberry.wepost.support.model;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.nancyberry.wepost.R;
import com.nancyberry.wepost.ui.BrowserActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatusContent implements Serializable {

    private static final long serialVersionUID = -2994626484778505236L;

    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("id")
    private Long id;
    @SerializedName("mid")
    private Long mid;
    @SerializedName("idstr")
    private String idstr;
    @SerializedName("text")
    private String text;
    @SerializedName("source_allowclick")
    private Integer sourceAllowclick;
    @SerializedName("source_type")
    private Integer sourceType;
    @SerializedName("source")
    private String source;
    @SerializedName("favorited")
    private Boolean favorited;
    @SerializedName("truncated")
    private Boolean truncated;
    @SerializedName("in_reply_to_status_id")
    private String inReplyToStatusId;
    @SerializedName("in_reply_to_user_id")
    private String inReplyToUserId;
    @SerializedName("in_reply_to_screen_name")
    private String inReplyToScreenName;
    @SerializedName("pic_urls")
    private List<PicUrl> picUrls = new ArrayList<>();
    @SerializedName("thumbnail_pic")
    private String thumbnailPic;
    @SerializedName("bmiddle_pic")
    private String bmiddlePic;
    @SerializedName("original_pic")
    private String originalPic;
    @SerializedName("geo")
    private Object geo;
    @SerializedName("user")
    private User user;
    @SerializedName("retweeted_status")
    private StatusContent retweetedStatus;
    //    @SerializedName("annotations")
//   //    @Expose
//    private List<Annotation_> annotations = new ArrayList<Annotation_>();
    @SerializedName("reposts_count")
    private Integer repostsCount;
    @SerializedName("comments_count")
    private Integer commentsCount;
    @SerializedName("attitudes_count")
    private Integer attitudesCount;
    @SerializedName("isLongText")
    private Boolean isLongText;
    @SerializedName("mlevel")
    private Integer mlevel;
    @SerializedName("visible")
    private Visible visible;
    @SerializedName("biz_feature")
    private Long bizFeature;
    @SerializedName("darwin_tags")
    private List<Object> darwinTags = new ArrayList<Object>();
    @SerializedName("hot_weibo_tags")
    private List<Object> hotWeiboTags = new ArrayList<Object>();
    @SerializedName("text_tag_tips")
    private List<Object> textTagTips = new ArrayList<Object>();
    @SerializedName("rid")
    private String rid;
    @SerializedName("userType")
    private Integer userType;
    @SerializedName("cardid")
    private String cardid;

    public String formatDesc() {
        return source;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The mid
     */
    public Long getMid() {
        return mid;
    }

    /**
     * @param mid The mid
     */
    public void setMid(Long mid) {
        this.mid = mid;
    }

    /**
     * @return The idstr
     */
    public String getIdstr() {
        return idstr;
    }

    /**
     * @param idstr The idstr
     */
    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The sourceAllowclick
     */
    public Integer getSourceAllowclick() {
        return sourceAllowclick;
    }

    /**
     * @param sourceAllowclick The source_allowclick
     */
    public void setSourceAllowclick(Integer sourceAllowclick) {
        this.sourceAllowclick = sourceAllowclick;
    }

    /**
     * @return The sourceType
     */
    public Integer getSourceType() {
        return sourceType;
    }

    /**
     * @param sourceType The source_type
     */
    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The favorited
     */
    public Boolean getFavorited() {
        return favorited;
    }

    /**
     * @param favorited The favorited
     */
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    /**
     * @return The truncated
     */
    public Boolean getTruncated() {
        return truncated;
    }

    /**
     * @param truncated The truncated
     */
    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    /**
     * @return The inReplyToStatusId
     */
    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     * @param inReplyToStatusId The in_reply_to_status_id
     */
    public void setInReplyToStatusId(String inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     * @return The inReplyToUserId
     */
    public String getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     * @param inReplyToUserId The in_reply_to_user_id
     */
    public void setInReplyToUserId(String inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     * @return The inReplyToScreenName
     */
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     * @param inReplyToScreenName The in_reply_to_screen_name
     */
    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     * @return The picUrls
     */
    public List<PicUrl> getPicUrls() {
        return picUrls;
    }

    /**
     * @param picUrls The pic_urls
     */
    public void setPicUrls(List<PicUrl> picUrls) {
        this.picUrls = picUrls;
    }

    /**
     * @return The geo
     */
    public Object getGeo() {
        return geo;
    }

    /**
     * @param geo The geo
     */
    public void setGeo(Object geo) {
        this.geo = geo;
    }

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return The retweetedStatus
     */
    public StatusContent getRetweetedStatus() {
        return retweetedStatus;
    }

    /**
     * @param retweetedStatus The retweeted_status
     */
    public void setRetweetedStatus(StatusContent retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

//    /**
//     *
//     * @return
//     * The annotations
//     */
//    public List<Annotation_> getAnnotations() {
//        return annotations;
//    }
//
//    /**
//     *
//     * @param annotations
//     * The annotations
//     */
//    public void setAnnotations(List<Annotation_> annotations) {
//        this.annotations = annotations;
//    }

    /**
     * @return The repostsCount
     */
    public Integer getRepostsCount() {
        return repostsCount;
    }

    /**
     * @param repostsCount The reposts_count
     */
    public void setRepostsCount(Integer repostsCount) {
        this.repostsCount = repostsCount;
    }

    /**
     * @return The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     * @param commentsCount The comments_count
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * @return The attitudesCount
     */
    public Integer getAttitudesCount() {
        return attitudesCount;
    }

    /**
     * @param attitudesCount The attitudes_count
     */
    public void setAttitudesCount(Integer attitudesCount) {
        this.attitudesCount = attitudesCount;
    }

    /**
     * @return The isLongText
     */
    public Boolean getIsLongText() {
        return isLongText;
    }

    /**
     * @param isLongText The isLongText
     */
    public void setIsLongText(Boolean isLongText) {
        this.isLongText = isLongText;
    }

    /**
     * @return The mlevel
     */
    public Integer getMlevel() {
        return mlevel;
    }

    /**
     * @param mlevel The mlevel
     */
    public void setMlevel(Integer mlevel) {
        this.mlevel = mlevel;
    }

    /**
     * @return The visible
     */
    public Visible getVisible() {
        return visible;
    }

    /**
     * @param visible The visible
     */
    public void setVisible(Visible visible) {
        this.visible = visible;
    }

    /**
     * @return The bizFeature
     */
    public Long getBizFeature() {
        return bizFeature;
    }

    /**
     * @param bizFeature The biz_feature
     */
    public void setBizFeature(Long bizFeature) {
        this.bizFeature = bizFeature;
    }

    /**
     * @return The darwinTags
     */
    public List<Object> getDarwinTags() {
        return darwinTags;
    }

    /**
     * @param darwinTags The darwin_tags
     */
    public void setDarwinTags(List<Object> darwinTags) {
        this.darwinTags = darwinTags;
    }

    /**
     * @return The hotWeiboTags
     */
    public List<Object> getHotWeiboTags() {
        return hotWeiboTags;
    }

    /**
     * @param hotWeiboTags The hot_weibo_tags
     */
    public void setHotWeiboTags(List<Object> hotWeiboTags) {
        this.hotWeiboTags = hotWeiboTags;
    }

    /**
     * @return The textTagTips
     */
    public List<Object> getTextTagTips() {
        return textTagTips;
    }

    /**
     * @param textTagTips The text_tag_tips
     */
    public void setTextTagTips(List<Object> textTagTips) {
        this.textTagTips = textTagTips;
    }

    /**
     * @return The rid
     */
    public String getRid() {
        return rid;
    }

    /**
     * @param rid The rid
     */
    public void setRid(String rid) {
        this.rid = rid;
    }

    /**
     * @return The userType
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * @param userType The userType
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * @return The cardid
     */
    public String getCardid() {
        return cardid;
    }

    /**
     * @param cardid The cardid
     */
    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getThumbnailPic() {
        return thumbnailPic;
    }

    public void setThumbnailPic(String thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }

    public String getBmiddlePic() {
        return bmiddlePic;
    }

    public void setBmiddlePic(String bmiddlePic) {
        this.bmiddlePic = bmiddlePic;
    }

    public String getOriginalPic() {
        return originalPic;
    }

    public void setOriginalPic(String originalPic) {
        this.originalPic = originalPic;
    }

    private static final Pattern USER_PATTERN = Pattern.compile("@[^ :\\n]+[ :]");
    private static final Pattern TOPIC_PATTERN = Pattern.compile("#[^#\\n]+#");
    private static final Pattern URL_PATTERN = Patterns.WEB_URL;
    private static final String SPECIAL_URL_SUFFIX = "...全文";

//    public static final Pattern WEB_URL = Pattern.compile(
//            "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
//                    + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
//                    + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
//                    + "(?:" + Patterns.DOMAIN_NAME + ")"
//                    + "(?:\\:\\d{1,5})?)" // plus option port number
//                    + "(\\/(?:(?:[" + Patterns.GOOD_IRI_CHAR + "\\;\\/\\?\\:\\@\\&\\=\\#\\~"  // plus option query params
//                    + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
//                    + "(?:\\b|$|...)");

    public static SpannableString parseText(final Context context, final String text) {
        if (null == text) {
            return null;
        }

        SpannableString ss = new SpannableString(text);

        /**
         * Parse user
         */
        Matcher userMatcher = USER_PATTERN.matcher(text);
        while (userMatcher.find()) {
            String s = userMatcher.group();
            final String userText = s.substring(0, s.length() - 1);    // exclude the last ':'

            ClickableSpan span = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // jump to the specific user page
                    Toast toast = Toast.makeText(context, userText + " on click!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(context.getResources().getColor(R.color.comm_blue));
                }
            };

            ss.setSpan(span, userMatcher.start(), userMatcher.end() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * Parse topic
         */
        Matcher topicMatcher = TOPIC_PATTERN.matcher(text);
        while (topicMatcher.find()) {
            final String topicText = topicMatcher.group();

            ClickableSpan span = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // jump to the specific user page
                    Toast toast = Toast.makeText(context, topicText + " on click!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(context.getResources().getColor(R.color.comm_blue));
                }
            };

            ss.setSpan(span, topicMatcher.start(), topicMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * Parse url
         */
        Matcher urlMatcher = URL_PATTERN.matcher(text);
        while (urlMatcher.find()) {
            int start = urlMatcher.start();
            int end = urlMatcher.end();
            final String urlText = urlMatcher.group();

            ClickableSpan span = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    /**
                     * Implicit intent
                     */
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(urlText));
//                    context.startActivity(intent);
                    /**
                     * Explicit intent
                     */
                    BrowserActivity.actionStart(context, Uri.parse(urlText));
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(context.getResources().getColor(R.color.comm_blue));
                    ds.setUnderlineText(true);
                }
            };

            if (urlText.endsWith(SPECIAL_URL_SUFFIX)) {
                end -= SPECIAL_URL_SUFFIX.length();
            }
            ss.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ss;

//        int i = 0;
//        int j;
//        int length = text.length();
//
//        while (i < length) {
//            if (text.charAt(i) == '@') {
//                // parse screen name
//                j = i + 1;
//                while (j < length && text.charAt(j) != ':' && text.charAt(j) != ' ') {
//                    ++j;
//                }
//
//                if (j - i < 2 || j == length) { // empty of couldn't find
//                    i = j + 1;
//                } else {
//                    final String userText = text.substring(i, j);
//
//                    ClickableSpan span = new ClickableSpan() {
//                        @Override
//                        public void onClick(View widget) {
//                            // jump to the specific user page
//                            Toast toast = Toast.makeText(context, userText + " on click!", Toast.LENGTH_SHORT);
//                            toast.show();
//                        }
//
//                        @Override
//                        public void updateDrawState(TextPaint ds) {
//                            ds.setColor(context.getResources().getColor(R.color.comm_blue));
//                        }
//                    };
//
//                    ss.setSpan(span, i, j, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    i = j + 1;
//                }
//            } else if (text.charAt(i) == '#') {
//                // find next #
//                j = i + 1;
//                while (j < length && text.charAt(j) != '#') {
//                    ++j;
//                }
//
//                if (j - i < 2 || j == length) { // empty of couldn't find
//                    i = j + 1;
//                } else {
//                    final String topicText = text.substring(i, j + 1);
//
//                    ClickableSpan span = new ClickableSpan() {
//                        @Override
//                        public void onClick(View widget) {
//                            // jump to the topic page
//                            Toast toast = Toast.makeText(context, topicText + " on click!", Toast.LENGTH_SHORT);
//                            toast.show();
//                        }
//
//                        @Override
//                        public void updateDrawState(TextPaint ds) {
//                            ds.setColor(context.getResources().getColor(R.color.comm_blue));
////                            ds.setUnderlineText(true);
//                        }
//                    };
//
//                    ss.setSpan(span, i, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    i = j + 1;
//                }
//            } else {
//                ++i;
//            }
//        }
    }
}
