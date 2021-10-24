package com.nathaniel.utility;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.utility
 * @datetime 2021/10/16 - 13:26
 */
public class BitmapCacheUtils {
    /**
     * 此处建议是128
     */
    private static final float DEFAULT_DRAWABLE_SIZE = 96f;

    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    private static Drawable zoomDrawable(Context context, Drawable drawable, float distW, float distH) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width <= DEFAULT_DRAWABLE_SIZE && height <= DEFAULT_DRAWABLE_SIZE) {
            return drawable;
        }
        Bitmap oldBitmap = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = distW / width;
        float scaleHeight = distH / height;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(context.getResources(), newBitmap);
    }

    public static Drawable getBitmapFromCache(Context context, ApplicationInfo applicationInfo, PackageManager packageManager) {
        if (applicationInfo == null || packageManager == null) {
            return null;
        }
        Bitmap appIcon;
        Object appBitmap = BitmapLruCache.getInstance().getLruCache().get(applicationInfo.packageName);
        if (appBitmap == null) {
            Drawable drawable = applicationInfo.loadIcon(packageManager);
            Bitmap bitmap = drawableToBitmap(drawable);
            if (bitmap == null) {
                return null;
            }
            int scaleWidth = (int) DEFAULT_DRAWABLE_SIZE;
            int scaleHeight = (int) DEFAULT_DRAWABLE_SIZE;
            appIcon = Bitmap.createScaledBitmap(bitmap, scaleWidth, scaleHeight, true);
            BitmapLruCache.getInstance().getLruCache().put(applicationInfo.packageName, appIcon);
            bitmap.recycle();
        } else {
            appIcon = (Bitmap) appBitmap;
        }
        return new BitmapDrawable(context.getResources(), appIcon);
    }
} 