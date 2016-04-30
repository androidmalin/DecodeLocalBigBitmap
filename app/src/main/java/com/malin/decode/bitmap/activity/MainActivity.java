package com.malin.decode.bitmap.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.malin.decode.R;
import com.malin.decode.bitmap.util.DeviceInfo;
import com.malin.decode.bitmap.util.DimensUtils;
import com.malin.decode.bitmap.util.ImageUtils;
import com.orhanobut.logger.Logger;

/**
 * 测试手机为Nexus 6p DensityDpi:560
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TestBitmapDecode";

    private static final String PATH = "image/river.jpg";
    //private static final String PATH = "image/bigmap.jpg";

    private ImageView mImageView;
    private ImageView mImageViewTwo;
    private ImageView mImageViewThree;
    private ImageView mImageViewDpi;

    private ProgressBar mProgressBar;
    private ProgressBar mProgressBarTwo;
    private ProgressBar mProgressBarThree;
    private ProgressBar mProgressBarFour;

    private Bitmap mBitmap_ARGB_8888;
    private Bitmap mBitmap_RGB_565;
    private Bitmap mBitmap_ALPHA_8;

    private Bitmap mHdiBitmap;

    private RelativeLayout mRelativeLayoutOne;
    private RelativeLayout mRelativeLayoutTwo;
    private RelativeLayout mRelativeLayoutThree;
    private RelativeLayout mRelativeLayoutFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init(TAG);
        initView();
        initData();

        int w = (int) (640 * ((560 * 1.0f) / 240) + 0.5f);
        int h = (int) (960 * ((560 * 1.0f) / 240) + 0.5f);
        Logger.d("w:" + w);
        Logger.d("h:" + h);

        Logger.d("s:" + (560 * 1.0f) / 240);
        Logger.d("ddd:" + (int) 19.6f);
        Logger.d("www:" + 560 / 240);
        mProgressBar.setVisibility(View.VISIBLE);
        new LoadImageThread().start();
    }


    private void initData() {
        DeviceInfo.getInstance().initializeScreenInfo(this);
    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.iv_img);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mProgressBarTwo = (ProgressBar) findViewById(R.id.pb_loading_two);
        mProgressBarThree = (ProgressBar) findViewById(R.id.pb_loading_three);
        mProgressBarFour = (ProgressBar) findViewById(R.id.pb_loading_four);

        mImageViewTwo = (ImageView) findViewById(R.id.iv_img_two);
        mImageViewThree = (ImageView) findViewById(R.id.iv_img_three);
        mImageViewDpi = (ImageView) findViewById(R.id.iv_img_dpi);

        mRelativeLayoutOne = (RelativeLayout) findViewById(R.id.rl_one);
        mRelativeLayoutTwo = (RelativeLayout) findViewById(R.id.rl_two);
        mRelativeLayoutThree = (RelativeLayout) findViewById(R.id.rl_three);
        mRelativeLayoutFour = (RelativeLayout) findViewById(R.id.rl_four);
    }


    class LoadImageThread extends Thread {
        @Override
        public void run() {
            super.run();


            int width = DimensUtils.dipToPx(200);
            int height = DimensUtils.dipToPx(200);
            Logger.d("DeviceInfo.mDensity:" + DeviceInfo.mDensity);
            Logger.d("height:" + height);
            Logger.d("width:" + width);
            mBitmap_ARGB_8888 = ImageUtils.getInstance().getImageBitmapFromAssetsFolderThroughImagePathName(
                    getApplicationContext(),
                    PATH,
                    width,
                    height,
                    Bitmap.Config.ARGB_8888

            );

            mBitmap_RGB_565 = ImageUtils.getInstance().getImageBitmapFromAssetsFolderThroughImagePathName(
                    getApplicationContext(),
                    PATH,
                    width,
                    height,
                    Bitmap.Config.RGB_565

            );

            mBitmap_ALPHA_8 = ImageUtils.getInstance().getImageBitmapFromAssetsFolderThroughImagePathName(
                    getApplicationContext(),
                    PATH,
                    width,
                    height,
                    Bitmap.Config.ALPHA_8
            );

            getHdpiBitmap();
            getXHdpiBitmap();
            getXXHdpiBitmap();
            getXXXHdpiBitmap();
            getBigBitmapFromHdpi();

            Logger.d("bitmap_ARGB_8888 size:" + ImageUtils.getBitmapSize(mBitmap_ARGB_8888));
            Logger.d("bitmap_RGB_565 size:" + ImageUtils.getBitmapSize(mBitmap_RGB_565));
            Logger.d("mBitmap_ALPHA_8 size:" + ImageUtils.getBitmapSize(mBitmap_ALPHA_8));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.GONE);
                    mProgressBarTwo.setVisibility(View.GONE);
                    mProgressBarThree.setVisibility(View.GONE);
                    mProgressBarFour.setVisibility(View.GONE);

                    mRelativeLayoutOne.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    mRelativeLayoutTwo.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    mRelativeLayoutThree.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    mRelativeLayoutFour.setBackgroundColor(getResources().getColor(R.color.colorTransparent));

                    mImageView.setImageBitmap(mBitmap_ARGB_8888);
                    mImageViewTwo.setImageBitmap(mBitmap_RGB_565);
                    mImageViewThree.setImageBitmap(mBitmap_ALPHA_8);
                    mImageViewDpi.setImageBitmap(mHdiBitmap);
                }
            });


        }
    }


    /**
     * mipmap-hdpi
     * <p/>
     * density 1.5
     * densityDpi 240
     */
    private void getHdpiBitmap() {
        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.hdpi_girl);
        int w_hdpi = (int) (640 * ((560 * 1.0f) / 240) + 0.5f);
        int h_hdpi = (int) (960 * ((560 * 1.0f) / 240) + 0.5f);
        int hdpi_size = w_hdpi * h_hdpi * 4;

        Logger.d("w_hdpi" + w_hdpi);
        Logger.d("h_hdpi_" + h_hdpi);
        Logger.d("hdpi_size:" + hdpi_size);

        Logger.d("mHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));

        if (hdpi_size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("hdpi is ok");
        }
    }

    /**
     * mipmap-xhdpi 2
     * <p/>
     * density 320
     */
    private void getXHdpiBitmap() {
        int w_xhdpi = (int) (640 * ((560 * 1.0f) / 320) + 0.5f);
        int h_xhdpi = (int) (960 * ((560 * 1.0f) / 320) + 0.5f);
        int xhdpi_size = w_xhdpi * h_xhdpi * 4;

        Logger.d("w_hdpi:" + w_xhdpi);
        Logger.d("h_hdpi:" + h_xhdpi);
        Logger.d("hdpi_size:" + xhdpi_size);

        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.xhdpi_girl);
        Logger.d("mxHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));

        if (xhdpi_size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("xhdpi is ok");
        }
    }

    /**
     * mipmap-xxhdpi 3
     * <p/>
     * density 480
     */
    private void getXXHdpiBitmap() {
        int w_xxhdpi = (int) (640 * ((560 * 1.0f) / 480) + 0.5f);
        int h_xxhdpi = (int) (960 * ((560 * 1.0f) / 480) + 0.5f);
        int xxhdpi_size = w_xxhdpi * h_xxhdpi * 4;

        Logger.d("w_hdpi:" + w_xxhdpi);
        Logger.d("h_hdpi:" + h_xxhdpi);
        Logger.d("hdpi_size:" + xxhdpi_size);

        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.xxhdpi_girl);
        Logger.d("mxxHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));

        if (xxhdpi_size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("xxhdpi is ok");
        }
    }

    /**
     * mipmap-xxxhdpi 4
     * <p/>
     * density 640
     */
    private void getXXXHdpiBitmap() {
        int w_xxxhdpi = (int) (640 * ((560 * 1.0f) / 640) + 0.5f);
        int h_xxxhdpi = (int) (960 * ((560 * 1.0f) / 640) + 0.5f);
        int xxxhdpi_size = w_xxxhdpi * h_xxxhdpi * 4;

        Logger.d("w_xxxhdpi" + w_xxxhdpi);
        Logger.d("h_xxxhdpi_" + h_xxxhdpi);
        Logger.d("xxxhdpi_size:" + xxxhdpi_size);


        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.xxxhdpi_girl);
        Logger.d("mxxxHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));

        if (xxxhdpi_size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("xxxhdpi is ok");
        }
    }

    private void getBigBitmapFromHdpi() {
        int width = DimensUtils.dipToPx(200);
        int height = DimensUtils.dipToPx(200);
        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.bigmap, width, height, Bitmap.Config.RGB_565);
        Logger.d("BigBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));
    }

    /**
     * 实现字符串的倒序
     *
     * @param s
     * @return
     */
    private String getShow(String s) {
        if (s != null) {
            StringBuffer sb = new StringBuffer();
            int length = s.length();
            for (int i = length; i > 0; i--) {
                Log.d(TAG, i + ":" + s.charAt(i - 1));
                sb.append(s.charAt(i - 1));
            }
            return sb.toString();
        } else {
            return null;
        }
    }
}

