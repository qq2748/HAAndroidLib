package ha.custcom.webview.lib.wedgit.webview;


import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;



/**
 * Convenient extension of WebViewClient.
 */
public class CustomWebViewClient extends WebViewClient {
    private boolean error;
    private WebViewCallBackListener webViewCallBackListener;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public CustomWebViewClient(WebViewCallBackListener webViewCallBackListener) {
        super();
        this.webViewCallBackListener = webViewCallBackListener;
    }

    @Override
    public void onPageFinished(WebView view, final String url) {
        super.onPageFinished(view, url);
        ((CustomWebView) view).notifyPageFinished();
        if (!error) {
            view.loadUrl("javascript:(function() { document.getElementsByTagName('header')[0].style.display = 'none'; })()");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    webViewCallBackListener.onPageFinished(url);
                }
            });
        }
        error = false;

    }

    @Override
    public void onPageStarted(WebView view, final String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        error = false;

        ((CustomWebView) view).notifyPageStarted();
        handler.post(new Runnable() {
            @Override
            public void run() {
                webViewCallBackListener.onPageStarted(url);
            }
        });
    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {


    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, final String url) {
        if (isExternalApplicationUrl(url)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    webViewCallBackListener.onExternalApplicationUrl(url);
                }
            });
            return true;

        } else {
            ((CustomWebView) view).resetLoadedUrl();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    webViewCallBackListener.onUrlLoading(url);
                }
            });
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

    public interface WebViewCallBackListener {
        void onPageFinished(String url);

        void onPageStarted(String url);

        void onExternalApplicationUrl(String url);

        void onUrlLoading(String url);

        void onReceivedError(WebView view, int errorCode, String description, String failingUrl);
    }

    @Override
    public void onReceivedError(final WebView view, final int errorCode, final String description, final String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        error = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                webViewCallBackListener.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }
}
