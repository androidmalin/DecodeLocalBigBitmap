package com.malin.decode.bitmap.activity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malin.decode.bitmap.R;
import com.malin.decode.bitmap.bean.BitmapInfo;
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
    private ImageView mImageViewFive;

    private ProgressBar mProgressBar;
    private ProgressBar mProgressBarTwo;
    private ProgressBar mProgressBarThree;
    private ProgressBar mProgressBarFour;
    private ProgressBar mProgressBarFive;

    private Bitmap mBitmap_ARGB_8888;
    private Bitmap mBitmap_RGB_565;
    private Bitmap mBitmap_ALPHA_8;

    private Bitmap mHdiBitmap;

    private Bitmap mHeartBitmap;

    private RelativeLayout mRelativeLayoutOne;
    private RelativeLayout mRelativeLayoutTwo;
    private RelativeLayout mRelativeLayoutThree;
    private RelativeLayout mRelativeLayoutFour;
    private RelativeLayout mRelativeLayoutFive;


    //不同目录对应的 DensityDpi
    private static final int DENSITY_MDPI = 160;//mdpi
    private static final int DENSITY_HDPI = 240;//hdpi
    private static final int DENSITY_XHDPI = 320;//xhdpi
    private static final int DENSITY_XXHDPI = 480;//xxhdpi
    private static final int DENSITY_XXXHDPI = 640;//xxxhdpi


    private TextView mTvResult;
    private TextView mTvResult2;


    private Bitmap mMdpiBitmap;
    private Bitmap mHdpiBitmap;
    private Bitmap mXhdpiBitmap;
    private Bitmap mXXhdpiBitmap;
    private Bitmap mXXXhdpiBitmap;

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

        mTvResult = findViewById(R.id.tv_result);
        mTvResult2 = findViewById(R.id.tv_result2);
        mImageView = findViewById(R.id.iv_img);
        mProgressBar = findViewById(R.id.pb_loading);
        mProgressBarTwo = findViewById(R.id.pb_loading_two);
        mProgressBarThree = findViewById(R.id.pb_loading_three);
        mProgressBarFour = findViewById(R.id.pb_loading_four);
        mProgressBarFive = findViewById(R.id.pb_loading_five);

        mImageViewTwo = findViewById(R.id.iv_img_two);
        mImageViewThree = findViewById(R.id.iv_img_three);
        mImageViewDpi = findViewById(R.id.iv_img_dpi);
        mImageViewFive = findViewById(R.id.iv_img_five);

        mRelativeLayoutOne = findViewById(R.id.rl_one);
        mRelativeLayoutTwo = findViewById(R.id.rl_two);
        mRelativeLayoutThree = findViewById(R.id.rl_three);
        mRelativeLayoutFour = findViewById(R.id.rl_four);
        mRelativeLayoutFive = findViewById(R.id.rl_five);
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

            mHeartBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.heart);

            int hearBitmapSize = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.heart, DENSITY_XXXHDPI);
            int heartSize = ImageUtils.getBitmapSize(mHeartBitmap);
            BitmapInfo bitmapInfo = ImageUtils.getLocalBitmapSizeFromResFolder(getApplicationContext(), R.drawable.heart);
            if (hearBitmapSize == heartSize) {
                Logger.d("heart is ok");
            }
            Logger.d("heartSize:" + heartSize);
            Logger.d("hearBitmapSize:" + hearBitmapSize);
            Logger.d("原始width:" + bitmapInfo.width);//w:38
            Logger.d("原始height:" + bitmapInfo.height);//h:48
            Logger.d("原始outMimeType:" + bitmapInfo.outMimeType);

            BitmapInfo info = ImageUtils.getDrawableBitmapInfo(getApplicationContext(), R.drawable.heart, DENSITY_XXXHDPI);
            Logger.d("最后scale:" + info.scale);
            Logger.d("最后with:" + info.width);
            Logger.d("最后height:" + info.height);
            Logger.d("最后outMimeType:" + info.outMimeType);
            getMdpiBitmap();
            getHdpiBitmap();
            getXHdpiBitmap();
            getXXHdpiBitmap();
            getXXXHdpiBitmap();
            getBigBitmapFromHdpi();

            getXHdpiBitmapTest();
            getXXHdpiBitmapTest();


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
                    mProgressBarFive.setVisibility(View.GONE);

                    mRelativeLayoutOne.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    mRelativeLayoutTwo.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    mRelativeLayoutThree.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    mRelativeLayoutFour.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    mRelativeLayoutFive.setBackgroundColor(getResources().getColor(R.color.colorTransparent));

                    mImageView.setImageBitmap(mMdpiBitmap);
                    mImageViewTwo.setImageBitmap(mHdpiBitmap);
                    mImageViewThree.setImageBitmap(mXhdpiBitmap);
                    mImageViewDpi.setImageBitmap(mXXhdpiBitmap);
                    mImageViewDpi.setImageBitmap(mXXhdpiBitmap);
                    mImageViewFive.setImageBitmap(mXXXhdpiBitmap);

                    mTvResult.setText(mBuilder);
                    mTvResult2.setText(mBuilder2);
                }
            });


        }
    }


    private StringBuilder mBuilder = new StringBuilder("同一张图片在不同目录下占用的内存" + "\n");

    private StringBuilder mBuilder2 = new StringBuilder("不同目录下占用的内存" + "\n");

    /**
     * drawable-mdpi
     * <p>
     * density 1
     * densityDpi 160
     */
    private void getMdpiBitmap() {
        mMdpiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.mdpi_wechat);

        //nexus 6p
        // R.mipmap.mdpi_wechat
        // 960*640
        int w_hdpi = (int) (640 * ((560 * 1.0f) / 160) + 0.5f);
        int h_hdpi = (int) (960 * ((560 * 1.0f) / 160) + 0.5f);
        int hdpi_size = w_hdpi * h_hdpi * 4;
        Logger.d("mdpi_size:" + hdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.mdpi_wechat, DENSITY_MDPI);

        int bitmapSize = ImageUtils.getBitmapSize(mMdpiBitmap);
        Logger.d("mHdiBitmap:" + bitmapSize);
        Logger.d("size:" + size);

        mBuilder.append("drawable-mdpi:" + getMB(bitmapSize));
        mBuilder.append("\n");


        if (size == bitmapSize) {
            Logger.d("mdpi is ok");
        }
    }

    /**
     * drawable-hdpi
     * <p>
     * density 1.5
     * densityDpi 240
     */
    private void getHdpiBitmap() {
        mHdpiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.hdpi_wechat);

        //nexus 6p
        // R.mipmap.hdpi_wechat
        // 960*640
        int w_hdpi = (int) (640 * ((560 * 1.0f) / 240) + 0.5f);
        int h_hdpi = (int) (960 * ((560 * 1.0f) / 240) + 0.5f);
        int hdpi_size = w_hdpi * h_hdpi * 4;
        Logger.d("hdpi_size:" + hdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.hdpi_wechat, DENSITY_HDPI);


        int bitmapSize = ImageUtils.getBitmapSize(mHdpiBitmap);
        Logger.d("mHdiBitmap:" + bitmapSize);
        Logger.d("size:" + size);
        mBuilder.append("drawable-hdpi:" + getMB(bitmapSize));
        mBuilder.append("\n");

        if (size == bitmapSize) {
            Logger.d("hdpi is ok");
        }
    }

    /**
     * drawable-xhdpi 2
     * <p>
     * density 320
     */
    private void getXHdpiBitmap() {

        //nexus 6p
        // R.mipmap.hdpi_wechat
        // 960*640
        int w_xhdpi = (int) (640 * ((560 * 1.0f) / 320) + 0.5f);
        int h_xhdpi = (int) (960 * ((560 * 1.0f) / 320) + 0.5f);
        int xhdpi_size = w_xhdpi * h_xhdpi * 4;
        Logger.d("xhdpi_size:" + xhdpi_size);


        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.xhdpi_wechat, DENSITY_XHDPI);

        mXhdpiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.xhdpi_wechat);


        int bitmapSize = ImageUtils.getBitmapSize(mXhdpiBitmap);

        mBuilder.append("drawable-xhdpi:" + getMB(bitmapSize));
        mBuilder.append("\n");


        Logger.d("mxHdiBitmap:" + bitmapSize);
        Logger.d("size:" + size);
        if (size == bitmapSize) {
            Logger.d("xhdpi is ok");
        }
    }

    private void getXHdpiBitmapTest() {

        //nexus 6p
        // R.mipmap.hdpi_wechat
        // 960*640
        int w_xhdpi = (int) (640 * ((560 * 1.0f) / 320) + 0.5f);
        int h_xhdpi = (int) (960 * ((560 * 1.0f) / 320) + 0.5f);
        int xhdpi_size = w_xhdpi * h_xhdpi * 4;
        Logger.d("xhdpi_size:" + xhdpi_size);


        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.xhdpi_launcher, DENSITY_XHDPI);

        Bitmap mXhdpiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.xhdpi_launcher);


        int bitmapSize = ImageUtils.getBitmapSize(mXhdpiBitmap);

        mBuilder2.append("drawable-xhdpi:" + getMB(bitmapSize));
        mBuilder2.append("\n");


        Logger.d("mxHdiBitmap:" + bitmapSize);
        Logger.d("size:" + size);
        if (size == bitmapSize) {
            Logger.d("xhdpi is ok");
        }
    }

    /**
     * drawable-xxhdpi 3
     * <p>
     * density 480
     */
    private void getXXHdpiBitmapTest() {

        //nexus 6p
        // R.mipmap.hdpi_wechat
        // 960*640
        int w_xxhdpi = (int) (640 * ((560 * 1.0f) / 480) + 0.5f);
        int h_xxhdpi = (int) (960 * ((560 * 1.0f) / 480) + 0.5f);
        int xxhdpi_size = w_xxhdpi * h_xxhdpi * 4;

        Logger.d("w_hdpi:" + w_xxhdpi);
        Logger.d("h_hdpi:" + h_xxhdpi);
        Logger.d("xxhdpi_size:" + xxhdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.xxdpi_launcher, DENSITY_XXHDPI);

        Bitmap mXXhdpiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.xxdpi_launcher);


        int bitmapSize = ImageUtils.getBitmapSize(mXXhdpiBitmap);

        mBuilder2.append("drawable-xxhdpi:" + getMB(bitmapSize));
        mBuilder2.append("\n");

        Logger.d("mxxHdiBitmap:" + bitmapSize);
        Logger.d("size:" + size);

        if (size == bitmapSize) {
            Logger.d("xxhdpi is ok");
        }
    }

    /**
     * drawable-xxhdpi 3
     * <p>
     * density 480
     */
    private void getXXHdpiBitmap() {

        //nexus 6p
        // R.mipmap.hdpi_wechat
        // 960*640
        int w_xxhdpi = (int) (640 * ((560 * 1.0f) / 480) + 0.5f);
        int h_xxhdpi = (int) (960 * ((560 * 1.0f) / 480) + 0.5f);
        int xxhdpi_size = w_xxhdpi * h_xxhdpi * 4;

        Logger.d("w_hdpi:" + w_xxhdpi);
        Logger.d("h_hdpi:" + h_xxhdpi);
        Logger.d("xxhdpi_size:" + xxhdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.xxhdpi_wechat, DENSITY_XXHDPI);

        mXXhdpiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.xxhdpi_wechat);


        int bitmapSize = ImageUtils.getBitmapSize(mXXhdpiBitmap);

        mBuilder.append("drawable-xxhdpi:" + getMB(bitmapSize));
        mBuilder.append("\n");

        Logger.d("mxxHdiBitmap:" + bitmapSize);
        Logger.d("size:" + size);

        if (size == bitmapSize) {
            Logger.d("xxhdpi is ok");
        }
    }

    /**
     * drawable-xxxhdpi 4
     * <p>
     * density 640
     */
    private void getXXXHdpiBitmap() {

        //nexus 6p
        // R.mipmap.hdpi_wechat
        // 960*640
        int w_xxxhdpi = (int) (640 * ((560 * 1.0f) / 640) + 0.5f);
        int h_xxxhdpi = (int) (960 * ((560 * 1.0f) / 640) + 0.5f);
        int xxxhdpi_size = w_xxxhdpi * h_xxxhdpi * 4;

        Logger.d("w_xxxhdpi" + w_xxxhdpi);
        Logger.d("h_xxxhdpi_" + h_xxxhdpi);
        Logger.d("xxxhdpi_size:" + xxxhdpi_size);

        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.xxxhdpi_wechat, DENSITY_XXXHDPI);

        mXXXhdpiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.xxxhdpi_wechat);


        int bitmapSize = ImageUtils.getBitmapSize(mXXXhdpiBitmap);

        mBuilder.append("drawable-xxxhdpi:" + getMB(bitmapSize));
        mBuilder.append("\n");


        Logger.d("mxxxHdiBitmap:" + bitmapSize);
        Logger.d("xxxhdpi_size:" + size);
        if (size == bitmapSize) {
            Logger.d("xxxhdpi is ok");

        }
    }


    private void getBigBitmapFromHdpi() {
        int width = DimensUtils.dipToPx(200);
        int height = DimensUtils.dipToPx(200);
        mHdiBitmap = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.bigmap, width, height, Bitmap.Config.ARGB_8888);
        Logger.d("BigBitmap:" + ImageUtils.getBitmapSize(mHdiBitmap));
        int size = ImageUtils.getDrawableBitmapSize(getApplicationContext(), R.drawable.bigmap, DENSITY_XXXHDPI);
        Logger.d("size:" + size);


        //不压缩，直接读取，OOM
        Bitmap oBitmap = null;

//      oBitmap  = ImageUtils.getInstance().getLocalBitmapFromResFolder(getApplicationContext(), R.drawable.bigmap);


        if (oBitmap == null) {
            Logger.d("oBitmap==null");
        } else {
            Logger.d("oBitmap!=null");
        }
        if (size == ImageUtils.getBitmapSize(oBitmap)) {
            Logger.d("big image is ok");
        }
    }


    private void loadImageMatrix() {
        Matrix matrix = new Matrix();
        matrix.postScale(5, 5, 0, 0);
        mImageViewFive.setImageMatrix(matrix);
        mImageViewFive.setScaleType(ImageView.ScaleType.MATRIX);
        mImageViewFive.setImageBitmap(mHeartBitmap);
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

    private String getMB(int size) {

        String result;

        float ss = size * 1.0f / 10248 * 1.0f / 1024 * 1.0f;

        result = String.valueOf(ss) + "MB";
        return result;
    }
}

