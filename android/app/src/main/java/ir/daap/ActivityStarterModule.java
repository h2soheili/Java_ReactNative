package ir.daap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import java.lang.Class;
import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
class ActivityStarterModule extends ReactContextBaseJavaModule {
    private Class mActivityClass;
    ActivityStarterModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ActivityStarter";
    }

    @ReactMethod
    void navigateToActivity(String activityClassName,String message) {
        ReactApplicationContext context = getReactApplicationContext();
        if(activityClassName.equals("MainActivity")){
            mActivityClass=MainActivity.class;
        }
        else if(activityClassName.equals("MyReactActivity")){
            mActivityClass=MyReactActivity.class;
        }
        else if(activityClassName.equals("AuthReactActivity")){
            mActivityClass=AuthReactActivity.class;
        }
        Intent intent = new Intent(context,mActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(message !=null && message.length()>0){
            intent.putExtra("message",message);
        }
        context.startActivity(intent);
    }

    @ReactMethod
    void dialNumber(@NonNull String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        getReactApplicationContext().startActivity(intent);
    }

    @ReactMethod
    void getActivityName(@NonNull Callback callback) {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            callback.invoke(activity.getClass().getSimpleName());
        }
    }
}