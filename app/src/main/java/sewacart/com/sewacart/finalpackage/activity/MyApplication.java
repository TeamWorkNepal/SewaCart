package sewacart.com.sewacart.finalpackage.activity;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*if(!SharedPreferenceController.getLoginLog(MyApplication.this)){
            startActivity(new Intent(MyApplication.this, LoginActivity.class));
        }else{
            startActivity(new Intent(MyApplication.this, MainActivity.class));
        }*/
    }
}
