package com.nancyberry.wepost.ui.timeline;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.nancyberry.wepost.R;
import com.nancyberry.wepost.support.model.StatusContent;
import com.viewpagerindicator.PageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by nan.zhang on 4/12/16.
 */
public class PicsViewPagerActivity extends FragmentActivity implements View.OnLongClickListener {

    public static final String TAG = PicsViewPagerActivity.class.getSimpleName();

    public static final String BUNDLE_STATUS_CONTENT = "startscontent";
    public static final String BUNDLE_INDEX = "index";

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.view_pager_indicator)
    PageIndicator viewPagerIndicator;

    private PagerAdapter pagerAdapter;

    private StatusContent statusContent;

    private int index;

    private PhotoViewAttacher attacher;

    public static void actionStart(Context context, StatusContent statusContent, int index) {
        Intent intent = new Intent(context, PicsViewPagerActivity.class);
        intent.putExtra(BUNDLE_STATUS_CONTENT, statusContent);
        intent.putExtra(BUNDLE_INDEX, index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_pager);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        statusContent = (StatusContent) intent.getSerializableExtra(BUNDLE_STATUS_CONTENT);
        index = intent.getIntExtra(BUNDLE_INDEX, 0);

        pagerAdapter = new CustomPagerAdapter(this);

        viewPager.setAdapter(pagerAdapter);

        // Bind the indicator to the adapter
        // Don't forget to set current item to the specific one
        viewPagerIndicator.setViewPager(viewPager, index);
//        viewPager.setCurrentItem(index);
    }

    @Override
    public boolean onLongClick(View v) {
        // TODO: save image
        Toast.makeText(this, "onLongClick", Toast.LENGTH_SHORT).show();
        return true;
    }

    public class CustomPagerAdapter extends PagerAdapter {

        private Context context;

        public CustomPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.pager_picture, null);
            final ImageView imageView = (ImageView) view.findViewById(R.id.img_large);

            String thumbnailUrl = statusContent.getPicUrls().get(position).getThumbnailPic();
            String largeUrl = thumbnailUrl.replace("thumbnail", "large");

            Glide.with(context)
                    .load(largeUrl)
                    .thumbnail(Glide.with(context).load(thumbnailUrl))
                    .fitCenter()
                    .crossFade()
                    .into(new GlideDrawableImageViewTarget(imageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            Log.wtf(TAG, "onResourceReady");
                            // here it's similar to RequestListener, but with less information (e.g. no model available)
                            super.onResourceReady(resource, animation);
                            // here you can be sure it's already set
                            // Use PhotoViewAttacher to deal with zoom in & out
                            attacher = new PhotoViewAttacher((imageView));
                            // deal with long click
                            attacher.setOnLongClickListener(PicsViewPagerActivity.this);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            Log.e(TAG, "onLoadFailed!");
                            super.onLoadFailed(e, errorDrawable);
                        }
                    });


            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return statusContent.getPicUrls().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    //    public class MainPagerAdapter extends PagerAdapter {
//        // This holds all the currently displayable views, in order from left to right.
//        private ArrayList<View> views = new ArrayList<View>();
//
//        //-----------------------------------------------------------------------------
//        // Used by ViewPager.  "Object" represents the page; tell the ViewPager where the
//        // page should be displayed, from left-to-right.  If the page no longer exists,
//        // return POSITION_NONE.
//        @Override
//        public int getItemPosition(Object object) {
//            int index = views.indexOf(object);
//            if (index == -1)
//                return POSITION_NONE;
//            else
//                return index;
//        }
//
//        //-----------------------------------------------------------------------------
//        // Used by ViewPager.  Called when ViewPager needs a page to display; it is our job
//        // to add the page to the container, which is normally the ViewPager itself.  Since
//        // all our pages are persistent, we simply retrieve it from our "views" ArrayList.
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            View v = views.get(position);
//            container.addView(v);
//            return v;
//        }
//
//        //-----------------------------------------------------------------------------
//        // Used by ViewPager.  Called when ViewPager no longer needs a page to display; it
//        // is our job to remove the page from the container, which is normally the
//        // ViewPager itself.  Since all our pages are persistent, we do nothing to the
//        // contents of our "views" ArrayList.
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(views.get(position));
//        }
//
//        //-----------------------------------------------------------------------------
//        // Used by ViewPager; can be used by app as well.
//        // Returns the total number of pages that the ViewPage can display.  This must
//        // never be 0.
//        @Override
//        public int getCount() {
//            return views.size();
//        }
//
//        //-----------------------------------------------------------------------------
//        // Used by ViewPager.
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        //-----------------------------------------------------------------------------
//        // Add "view" to right end of "views".
//        // Returns the position of the new view.
//        // The app should call this to add pages; not used by ViewPager.
//        public int addView(View v) {
//            return addView(v, views.size());
//        }
//
//        //-----------------------------------------------------------------------------
//        // Add "view" at "position" to "views".
//        // Returns position of new view.
//        // The app should call this to add pages; not used by ViewPager.
//        public int addView(View v, int position) {
//            views.add(position, v);
//            return position;
//        }
//
//        //-----------------------------------------------------------------------------
//        // Removes "view" from "views".
//        // Retuns position of removed view.
//        // The app should call this to remove pages; not used by ViewPager.
//        public int removeView(ViewPager pager, View v) {
//            return removeView(pager, views.indexOf(v));
//        }
//
//        //-----------------------------------------------------------------------------
//        // Removes the "view" at "position" from "views".
//        // Retuns position of removed view.
//        // The app should call this to remove pages; not used by ViewPager.
//        public int removeView(ViewPager pager, int position) {
//            // ViewPager doesn't have a delete method; the closest is to set the adapter
//            // again.  When doing so, it deletes all its views.  Then we can delete the view
//            // from from the adapter and finally set the adapter to the pager again.  Note
//            // that we set the adapter to null before removing the view from "views" - that's
//            // because while ViewPager deletes all its views, it will call destroyItem which
//            // will in turn cause a null pointer ref.
//            pager.setAdapter(null);
//            views.remove(position);
//            pager.setAdapter(this);
//
//            return position;
//        }
//
//        //-----------------------------------------------------------------------------
//        // Returns the "view" at "position".
//        // The app should call this to retrieve a view; not used by ViewPager.
//        public View getView(int position) {
//            return views.get(position);
//        }
//
//        // Other relevant methods:
//
//        // finishUpdate - called by the ViewPager - we don't care about what pages the
//        // pager is displaying so we don't use this method.
//    }
}
