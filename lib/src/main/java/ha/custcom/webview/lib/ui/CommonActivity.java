package ha.custcom.webview.lib.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import ha.custcom.webview.lib.R;

/**
 * by bin
 * 装载fragment通用类
 */
public class CommonActivity extends FragmentActivity {
    public static final String CLAZ = "clas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        Intent intent = getIntent();
        Class clz = (Class) intent.getSerializableExtra(CLAZ);
        Fragment fragment = null;
        try {
            fragment = (Fragment) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commitAllowingStateLoss();
    }

    public static void launch(Context context, Class clz, Intent intent) {
        intent.setClass(context, CommonActivity.class);
        intent.putExtra(CLAZ, clz);
        context.startActivity(intent);
    }

}
