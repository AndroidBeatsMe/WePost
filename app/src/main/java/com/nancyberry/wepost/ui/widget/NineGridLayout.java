package com.nancyberry.wepost.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nancyberry.wepost.R;
import com.nancyberry.wepost.support.model.PicUrl;

import java.util.List;

/**
 * Created by nan.zhang on 4/8/16.
 */
public class NineGridLayout extends ViewGroup {

    public static final String TAG = NineGridLayout.class.getSimpleName();

    private List<PicUrl> data;

    private int totalWidth;

    private int gap;

    private int rowCount;

    private int columnCount;

    public NineGridLayout(Context context) {
        super(context);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        totalWidth = getMeasuredWidth();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NineGridLayout,
                0, 0);

        try {
            // read custom attributes from TypedArray
            gap = a.getDimensionPixelSize(R.styleable.NineGridLayout_gap_pics, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure width: " + widthMeasureSpec + ", height: " + heightMeasureSpec);

        int desiredWidth = 200;
        int desiredHeight = 200;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged");
        // calculate and layout children again
        totalWidth = getMeasuredWidth();
        layoutChildrenView();
    }

    public void setImageData(List<PicUrl> list) {
        Log.d(TAG, "setImageData");
        if (list == null || list.isEmpty()) {
            return;
        }

        generateChildrenLayout(list.size());
        addChildren(list.size());
        data = list;
        layoutChildrenView();
    }

    private void layoutChildrenView() {
        int singleWidth = (totalWidth - gap * 2) / 3;
        int singleHeight = singleWidth;

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = singleHeight * rowCount + gap * (rowCount - 1);
        setLayoutParams(params);

        for (int i = 0; i < getChildCount(); ++i) {
            CustomImageView childView = (CustomImageView) getChildAt(i);

            String url = data.get(i).getThumbnailPic();
//            url.replace("thumbnail_pic", "bmiddle_pic");
//            childView.setImageUrl(data.get(i).getThumbnailPic());
            childView.setImageUrl(url);

            int[] pos = findPosition(i);
            int left = (singleWidth + gap) * pos[1];
            int top = (singleHeight + gap) * pos[0];
            getChildAt(i).layout(left, top, left + singleWidth, top + singleHeight);
        }
    }

    private int[] findPosition(int childIndex) {
        int[] pos = new int[2];

        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                if (i * columnCount + j == childIndex) {
                    pos[0] = i;
                    pos[1] = j;
                    break;
                }
            }
        }

        return pos;
    }

    private void generateChildrenLayout(int size) {
        switch (size) {
            case 1:
            case 2:
            case 3:
                rowCount = 1;
                columnCount = size;
                break;
            case 4:
                rowCount = 2;
                columnCount = 2;
                break;
            case 5:
            case 6:
                rowCount = 2;
                columnCount = 3;
                break;
            case 7:
            case 8:
            case 9:
                rowCount = 3;
                columnCount = 3;
                break;
            default:
                break;
        }
    }

    private CustomImageView generateImageView() {
        CustomImageView view = new CustomImageView(getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        view.setBackgroundColor(Color.parseColor("#f5f5f5"));

        return view;
    }

    private void addChildren(int newSize) {
        // reuse view
        int oldSize = data == null ? 0 : data.size();

        if (oldSize < newSize) {
            // add additional views
            for (int i = oldSize; i < newSize; ++i) {
                CustomImageView view = generateImageView();
                addView(view, generateDefaultLayoutParams());
            }
        } else if (oldSize > newSize) {
            // remove redundant views
            removeViews(newSize, oldSize - newSize);
        }
    }
}
