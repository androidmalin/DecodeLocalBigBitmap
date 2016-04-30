package com.malin.decode.bitmap.util;

/**
 * Created by malin on 16-4-29.
 */
public class DimensUtils {

    private DimensUtils(){}

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int pxToDip(float pxValue) {
        final float scale = DeviceInfo.mDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dipToPx(float dipValue) {
        final float scale = DeviceInfo.mDensity;
        return (int) (dipValue * scale + 0.5f);
    }
}
