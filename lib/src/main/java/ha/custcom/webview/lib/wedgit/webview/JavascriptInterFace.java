package ha.custcom.webview.lib.wedgit.webview;

import android.content.Context;

import ha.custcom.webview.lib.util.ToastUtils;

/**
 * Created by bin on 15/9/21.
 * webview js 交互
 */
public class JavascriptInterFace {
    Context context;
    CustomWebView webView;

    public JavascriptInterFace(CustomWebView webView) {
        this.context = webView.getContext();
        this.webView = webView;
    }

    @android.webkit.JavascriptInterface
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

}
