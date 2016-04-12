package ha.custcom.webview.lib.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;


/**
 * Created by bin on 2016/4/11.
 * 使用依赖注入
 */
public abstract class SimpleBaseActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutResource());
    }

    protected abstract int getLayoutResource();
}
