package com.nancyberry.wepost.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nancyberry.wepost.R;


/**
 * Created by Pan_ on 2015/2/2.
 */
public class CustomImageView extends ImageView {
    public static final String TAG = CustomImageView.class.getSimpleName();

    private String url;
    private boolean isAttachedToWindow;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context) {
        super(context);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onTouchEvent:" + event.getAction());
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Drawable drawable = getDrawable();
//                if (drawable != null) {
//                    drawable.mutate().setColorFilter(Color.GRAY,
//                            PorterDuff.Mode.MULTIPLY);
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                Drawable drawableUp = getDrawable();
//                if (drawableUp != null) {
//                    drawableUp.mutate().clearColorFilter();
//                }
//
//                if (imageViewSelectedListener != null) {
//                    imageViewSelectedListener.onImageViewSelected(this);
//                }
//
//                break;
//        }
//
////        return super.onTouchEvent(event);
//        return true;
//    }

    @Override
    public void onAttachedToWindow() {
        isAttachedToWindow = true;
        setImageUrl(url);
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
//        Picasso.with(getContext()).cancelRequest(this);
        isAttachedToWindow = false;
        setImageBitmap(null);
        super.onDetachedFromWindow();
    }


    public void setImageUrl(String url) {
        setBackgroundResource(R.color.comm_red);

        if (!TextUtils.isEmpty(url)) {
            this.url = url;
            if (isAttachedToWindow) {
                Glide.with(getContext()).load(url)
                        .centerCrop()
                        .placeholder(R.color.comm_gray)
                        .crossFade()
                        .into(this);
            }
        }
    }
}
