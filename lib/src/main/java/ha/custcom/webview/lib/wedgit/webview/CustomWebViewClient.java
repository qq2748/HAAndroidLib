package ha.custcom.webview.lib.wedgit.webview;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;

import ha.custcom.webview.lib.appcation.AppController;

/**
 * Convenient extension of WebViewClient.
 */
public class CustomWebViewClient extends WebViewClient {
    private boolean error;
    private Context context;
    private WebViewCallBackListener webViewCallBackListener;

    public CustomWebViewClient(WebViewCallBackListener webViewCallBackListener) {
        super();
        this.webViewCallBackListener = webViewCallBackListener;
        context = AppController.getContext();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        ((CustomWebView) view).notifyPageFinished();
        if (!error) {
//            view.loadUrl("javascript:(function() { document.getElementsByTagName('header')[0].style.display = 'none'; })()");
            webViewCallBackListener.onPageFinished(url);
        }
        error = false;

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        error = false;

        ((CustomWebView) view).notifyPageStarted();
        webViewCallBackListener.onPageStarted(url);


    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {


    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (isExternalApplicationUrl(url)) {
            webViewCallBackListener.onExternalApplicationUrl(url);
            return true;

        } else if (view.getHitTestResult()!=null&&view.getHitTestResult().getType() == HitTestResult.EMAIL_TYPE) {
            onMailTo(url);
            return true;

        } else {


            ((CustomWebView) view).resetLoadedUrl();
            webViewCallBackListener.onUrlLoading(url);
            return false;
        }
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, final HttpAuthHandler handler, final String host, final String realm) {

    }

    private boolean isExternalApplicationUrl(String url) {
        return url.startsWith("vnd.") ||
                url.startsWith("rtsp://") ||
                url.startsWith("itms://") ||
                url.startsWith("itpc://");
    }

    public void onMailTo(String url) {
        Intent sendMail = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(sendMail);
    }

    public interface WebViewCallBackListener {
        public void onPageFinished(String url);

        public void onPageStarted(String url);

        public void onExternalApplicationUrl(String url);

        public void onUrlLoading(String url);

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        error = true;
        webViewCallBackListener.onReceivedError(view,errorCode,description,failingUrl);

    }
}
