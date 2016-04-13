package ha.custcom.webview.lib.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bin
 */
public class ToastUtils {
    private ToastUtils() {
    }

    private static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    private static void show(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showShort(Context context,int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context,int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
