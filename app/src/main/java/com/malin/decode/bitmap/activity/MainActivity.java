package com.malin.decode.bitmap.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.malin.decode.bitmap.R;
import com.malin.decode.bitmap.util.DeviceInfo;
import com.malin.decode.bitmap.util.DimensUtils;
import com.malin.decode.bitmap.util.ImageUtils;
import com.orhanobut.logger.Logger;

/**
 * 类描述:图片处理类
 * 创建人:malin.myemail@gmail.com
 * 创建时间:16-04-29.
 * 备注：测试手机为Nexus 6p DensityDpi:560
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



    //不同目录对应的 DensityDpi
    private static final int DENSITY_MDPI = 160;//mdpi
    private static final int DENSITY_HDPI = 240;//hdpi
    private static final int DENSITY_XHDPI = 320;//xhdpi
    private static final int DENSITY_XXHDPI = 480;//xxhdpi
    private static final int DENSITY_XXXHDPI = 640;//xxxhdpi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init(TAG);
        initView();
        initData();
        Logger.d("mDensity:" + DeviceInfo.mDensity);
        Logger.d("mDensityDpi:" + DeviceInfo.mDensityDpi);
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

            getMdpiBitmap();
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
     * mipmap-mdpi
     * <p/>
     * density 1
     * densityDpi 160
     */
    private void getMdpiBitmap() {
        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.mdpi_girl);

        //nexus 6p
        // R.mipmap.mdpi_girl
        // 960*640
        int w_hdpi = (int) (640 * ((560 * 1.0f) / 160) + 0.5f);
        int h_hdpi = (int) (960 * ((560 * 1.0f) / 160) + 0.5f);
        int hdpi_size = w_hdpi * h_hdpi * 4;
        Logger.d("mdpi_size:" + hdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.mipmap.mdpi_girl, DENSITY_MDPI);

        Logger.d("mHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));
        Logger.d("size:" + size);


        if (size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("mdpi is ok");
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

        //nexus 6p
        // R.mipmap.hdpi_girl
        // 960*640
        int w_hdpi = (int) (640 * ((560 * 1.0f) / 240) + 0.5f);
        int h_hdpi = (int) (960 * ((560 * 1.0f) / 240) + 0.5f);
        int hdpi_size = w_hdpi * h_hdpi * 4;
        Logger.d("hdpi_size:" + hdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.mipmap.hdpi_girl, DENSITY_HDPI);

        Logger.d("mHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));
        Logger.d("size:" + size);


        if (size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("hdpi is ok");
        }
    }

    /**
     * mipmap-xhdpi 2
     * <p/>
     * density 320
     */
    private void getXHdpiBitmap() {

        //nexus 6p
        // R.mipmap.hdpi_girl
        // 960*640
        int w_xhdpi = (int) (640 * ((560 * 1.0f) / 320) + 0.5f);
        int h_xhdpi = (int) (960 * ((560 * 1.0f) / 320) + 0.5f);
        int xhdpi_size = w_xhdpi * h_xhdpi * 4;
        Logger.d("xhdpi_size:" + xhdpi_size);


        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.mipmap.xhdpi_girl, DENSITY_XHDPI);

        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.xhdpi_girl);
        Logger.d("mxHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));
        Logger.d("size:" + size);
        if (size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("xhdpi is ok");
        }
    }

    /**
     * mipmap-xxhdpi 3
     * <p/>
     * density 480
     */
    private void getXXHdpiBitmap() {

        //nexus 6p
        // R.mipmap.hdpi_girl
        // 960*640
        int w_xxhdpi = (int) (640 * ((560 * 1.0f) / 480) + 0.5f);
        int h_xxhdpi = (int) (960 * ((560 * 1.0f) / 480) + 0.5f);
        int xxhdpi_size = w_xxhdpi * h_xxhdpi * 4;

        Logger.d("w_hdpi:" + w_xxhdpi);
        Logger.d("h_hdpi:" + h_xxhdpi);
        Logger.d("xxhdpi_size:" + xxhdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.mipmap.xxhdpi_girl, DENSITY_XXHDPI);

        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.xxhdpi_girl);
        Logger.d("mxxHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));
        Logger.d("size:" + size);

        if (size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("xxhdpi is ok");
        }
    }

    /**
     * mipmap-xxxhdpi 4
     * <p/>
     * density 640
     */
    private void getXXXHdpiBitmap() {

        //nexus 6p
        // R.mipmap.hdpi_girl
        // 960*640
        int w_xxxhdpi = (int) (640 * ((560 * 1.0f) / 640) + 0.5f);
        int h_xxxhdpi = (int) (960 * ((560 * 1.0f) / 640) + 0.5f);
        int xxxhdpi_size = w_xxxhdpi * h_xxxhdpi * 4;

        Logger.d("w_xxxhdpi" + w_xxxhdpi);
        Logger.d("h_xxxhdpi_" + h_xxxhdpi);
        Logger.d("xxxhdpi_size:" + xxxhdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.mipmap.xxxhdpi_girl, DENSITY_XXXHDPI);

        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.xxxhdpi_girl);
        Logger.d("mxxxHdiBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));
        Logger.d("xxxhdpi_size:" + size);
        if (size == ImageUtils.getBitmapSize(mHdiBitmap)) {
            Logger.d("xxxhdpi is ok");

        }
    }

    private void getBigBitmapFromHdpi() {
        int width = DimensUtils.dipToPx(200);
        int height = DimensUtils.dipToPx(200);
        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.bigmap, width, height, Bitmap.Config.ARGB_8888);
        Logger.d("BigBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));
        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.mipmap.bigmap, DENSITY_XXXHDPI);
        Logger.d("size:" + size);


        //不压缩，直接读取，OOM
        Bitmap oBitmap = null;

//      oBitmap  = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.mipmap.bigmap);


        if (oBitmap == null) {
            Logger.d("oBitmap==null");
        } else {
            Logger.d("oBitmap!=null");
        }
        if (size == ImageUtils.getBitmapSize(oBitmap)) {
            Logger.d("big image is ok");
        }
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

