package ha.custcom.webview.lib.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.custcom.webview.lib.R;
import ha.custcom.webview.lib.R2;
import ha.custcom.webview.lib.wedgit.MProgressWheel;
import ha.custcom.webview.lib.wedgit.fly.PullToRefreshView;
import ha.custcom.webview.lib.wedgit.webview.CustomWebView;
import ha.custcom.webview.lib.wedgit.webview.CustomWebViewClient;

/**
 * Created by bin on 2016/4/11.
 */
public abstract class BaseWebViewActivity extends SimpleBaseActivity implements CustomWebViewClient.WebViewCallBackListener {

    @BindView(R2.id.webview)
    CustomWebView mWebview;
    @BindView(R2.id.pull_to_refresh)
    PullToRefreshView mPullToRefresh;
    @BindView(R2.id.progress_wheel)
    MProgressWheel mProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        findView();
        initViews();
        loadUrl();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_webview;
    }

    protected void loadUrl() {
        mWebview.loadUrl(getLoadUrl());
    }

    protected void initViews() {
        mWebview.setWebViewClient(new CustomWebViewClient(this));
        mPullToRefresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPullToRefresh != null)
                            mPullToRefresh.setRefreshing(false);
                    }
                }, 1000);
                mWebview.reload();
            }
        });
    }

    protected void findView() {
        mWebview = (CustomWebView) findViewById(R.id.webview);
        mPullToRefresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mProgressWheel = (MProgressWheel) findViewById(R.id.progress_wheel);
    }

    protected abstract String getLoadUrl();

    @Override
    public void onPageStarted(String url) {
        if (mProgressWheel != null)
            mProgressWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(final String url) {
        if (mProgressWheel != null)
            mProgressWheel.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onExternalApplicationUrl(String url) {

    }

    @Override
    public void onUrlLoading(String url) {
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        mProgressWheel.setVisibility(View.GONE);
    }
}
