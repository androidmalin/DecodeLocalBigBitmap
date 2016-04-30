
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 malin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.malin.decode.bitmap.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.malin.decode.bitmap.bean.BitmapInfo;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * 类描述:图片处理类
 * 创建人:malin.myemail@gmail.com
 * 创建时间:15-11-11.
 * 参考项目:
 * http://bugly.qq.com/bbs/forum.php?mod=viewthread&tid=498&extra=page%3D1
 */
public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();


    private ImageUtils() {
    }

    private volatile static ImageUtils mInstance;

    public static ImageUtils getInstance() {
        if (mInstance == null) {
            synchronized (ImageUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 从assets文件夹中获取制定路径的图片的Bitmap
     *
     * @param context:上下文
     * @param imagePathName:图片路径的名称:例如: "image/river.jpg";
     * @param reqWidth:图片需要显示的宽
     * @param reqHeight:图片需要显示的高
     * @param config:图片的格式
     * @return
     */
    public Bitmap getImageBitmapFromAssetsFolderThroughImagePathName(Context context, String imagePathName, int reqWidth, int reqHeight, Bitmap.Config config) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        AssetManager assetManager = context.getResources().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(imagePathName);
            inputStream.mark(Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            return null;
        }

        try {
            if (inputStream != null) {
                BitmapFactory.decodeStream(inputStream, null, opts);
                inputStream.reset();
            } else {
                return null;
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
        Logger.d("inSampleSize:" + opts.inSampleSize);
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = config;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inTempStorage = new byte[512 * 1024];
        try {
            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream, null, opts);
            } else {
                return null;
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        Logger.d("w:" + bitmap.getWidth() + " h:" + bitmap.getHeight());
        if (bitmap != null) {
            try {
                int orHeight = bitmap.getHeight();
                int orWith = bitmap.getWidth();

                bitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, (int) ((reqWidth * 1.0f * orHeight) / orWith), true);
                Logger.d("w:" + bitmap.getWidth() + " h:" + bitmap.getHeight());
            } catch (OutOfMemoryError outOfMemoryError) {
                outOfMemoryError.printStackTrace();
                System.gc();
                return null;
            }

        }
        return bitmap;
    }


    /**
     * 获取一个合适的缩放系数(2^n)
     *
     * @param options:Options
     * @param reqWidth:图片需要显示的宽
     * @param reqHeight:图片需要显示的高
     * @return 缩放系数, 2的次方
     * @Link http://hukai.me/android-training-course-in-chinese/graphics/displaying-bitmaps/load-bitmap.html
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    /**
     * 获取assets文件夹下某个文件夹中所有图片路径的集合
     * <p/>
     * 例如:"image/river.jpg"; 这个目录下的图片路径的集合
     * {
     * "   image/river.jpg";
     * }
     *
     * @param context:上下文
     * @param folderName:文件夹名称
     * @return
     */
    public static ArrayList<String> getAssetsImageNamePathList(Context context, String folderName) {

        ArrayList<String> imagePathList = new ArrayList<String>();

        String[] imageNameArray = getAssetsImageNameArray(context, folderName);

        if (imageNameArray != null && imageNameArray.length > 0 && folderName != null && !folderName.replaceAll(" ", "").trim().equals("")) {
            for (String imageName : imageNameArray) {
                imagePathList.add(new StringBuffer(folderName).append(File.separator).append(imageName).toString());
            }
        }

        return imagePathList;
    }


    /**
     * 得到assets文件夹下--某个文件夹下--所有文件的文件名
     * 例如:"image/river.jpg"; 这个目录下的图片名称(包含后缀)集合
     * {
     * river.jpg
     * }
     *
     * @param context:上下文
     * @param folderName:文件夹名称
     * @return
     */
    private static String[] getAssetsImageNameArray(Context context, String folderName) {
        String[] imageNameArray = null;
        try {
            imageNameArray = context.getAssets().list(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageNameArray;
    }


    /**
     * 将本地ResFolder图片转换为Bitmap
     */

    /**
     * inDensity 就是原始资源的 density
     * <p/>
     * inTargetDensity 就是屏幕的 density。
     */

    public Bitmap getLocalBitmapFromResFolder(Context context, int resId) {

        Bitmap bitmap = null;
        try {
             bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        } catch (Exception e) {
            e.printStackTrace();
        }catch (OutOfMemoryError outOfMemoryError){
            outOfMemoryError.printStackTrace();
            System.gc();
        }
        return bitmap;
    }


    /**
     * 解析mipmap文件夹中的图片
     * @param context
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @param config
     * @return
     */
    public Bitmap getLocalBitmapFromResFolder(Context context, int resId, int reqWidth, int reqHeight, Bitmap.Config config) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bitmap;
        try {
            BitmapFactory.decodeResource(context.getResources(), resId, opts);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
        Logger.d("inSampleSize:" + opts.inSampleSize);
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = config;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inTempStorage = new byte[10*1024 * 1024];
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(), resId, opts);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (bitmap != null) {
            try {

                int orHeight = bitmap.getHeight();
                int orWith = bitmap.getWidth();
                Logger.d("w:" + orHeight + " h:" + orWith);

                bitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, (int) ((reqWidth * 1.0f * orHeight) / orWith), true);

                Logger.d("w:" + bitmap.getWidth() + " h:" + bitmap.getHeight());
            } catch (OutOfMemoryError outOfMemoryError) {
                outOfMemoryError.printStackTrace();
                System.gc();
                return null;
            }

        }
        return bitmap;
    }

    /**
     * 解析mipmap文件夹中的图片
     * @param context
     * @param resId
     * @return
     */
    public static BitmapInfo getLocalBitmapSizeFromResFolder(Context context, int resId) {
        BitmapInfo bitmapInfo = new BitmapInfo();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeResource(context.getResources(), resId, opts);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        bitmapInfo.width = opts.outHeight;
        bitmapInfo.height = opts.outWidth;
        bitmapInfo.outMimeType =opts.outMimeType;
        bitmapInfo.scale = 1;

        return bitmapInfo;
    }


    /**
     * 获取Bitmap大小
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
//        Logger.d("Last Width:" + w);
//        Logger.d("Last Height:" + h);

        return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
    }

    /**
     * 获取Bitmap大小
     *
     * @param bitmap
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static int getBitmapSize2(Bitmap bitmap) {
        if (bitmap == null) return 0;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getRowBytes() * bitmap.getHeight();
        } else {
            return bitmap.getByteCount();
        }
    }

    /**
     * 获取Bitmap大小
     *
     * @param bitmap
     * @return
     */
    @SuppressLint("NewApi")
    public static int getBitmapSize3(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
    }


    /**
     * 获取mipmap文件夹下图片的大小
     * @param resource:图片的id
     * @param drawableDensityDpi：图片所在目录文件对应的DensityDpi
     * @return
     */
    public static int getDrawableBitmapSize(Context context,int resource,int drawableDensityDpi){
        BitmapInfo bitmapInfo = ImageUtils.getInstance().getLocalBitmapSizeFromResFolder(context, resource);
        int w = bitmapInfo.width;
        int h = bitmapInfo.height;
        int targetDensityDpi = DeviceInfo.mDensityDpi;
        float scale = (targetDensityDpi * 1.0f)/drawableDensityDpi;
        int w_scale = (int) (w * scale + 0.5f);
        int h_scale = (int) (h * scale + 0.5f);
        return w_scale*h_scale*4;
    }

    /**
     * 获取mipmap文件夹下图片的大小
     * @param resource:图片的id
     * @param drawableDensityDpi：图片所在目录文件对应的DensityDpi
     * @return
     */
    public static BitmapInfo getDrawableBitmapInfo(Context context,int resource,int drawableDensityDpi){
        BitmapInfo bitmapInfo = ImageUtils.getInstance().getLocalBitmapSizeFromResFolder(context, resource);
        int w = bitmapInfo.width;
        int h = bitmapInfo.height;
        String outMimeType = bitmapInfo.outMimeType;
        int targetDensityDpi = DeviceInfo.mDensityDpi;
        float scale = (targetDensityDpi * 1.0f)/drawableDensityDpi;
        int w_scale = (int) (w * scale + 0.5f);
        int h_scale = (int) (h * scale + 0.5f);
        bitmapInfo.width = w_scale;
        bitmapInfo.height = h_scale;
        bitmapInfo.outMimeType = outMimeType;
        bitmapInfo.scale = scale;
        return bitmapInfo;
    }
}
