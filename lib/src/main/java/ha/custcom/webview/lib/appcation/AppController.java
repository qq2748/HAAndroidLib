package ha.custcom.webview.lib.appcation;

import android.app.Application;

/**
 * Created by bin on 2016/4/11.
 * app初始化
 */
public class AppController extends Application {

    static AppController context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static AppController getContext(){
        return context;
    }
}
