package ir.daap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;

public class MyReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoLoader.init(this, /* native exopackage */ false);
        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceSingleton.getReactInstanceManager(getApplication());
        // The string here (e.g. "MyReactNativeApp") has to match
        // the string in AppRegistry.registerComponent() in index.js
        Bundle initialProps = new Bundle();

        initialProps.putString("message", getIntent().getStringExtra("message"));
        mReactRootView.startReactApplication(mReactInstanceManager, "MyReactNativeApp", initialProps);

        setContentView(mReactRootView);
        WritableMap map = Arguments.createMap();
        map.putString("key1", "Value1");
        map.putString("key1", "Value1");

        try {
            mReactInstanceManager.getCurrentReactContext()
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("customEventName", map);

        } catch (Exception e){
            Log.e("log:: ReactNative", "Caught Exception: " + e.getMessage());
        }
    }
//    protected List<ReactPackage> getPackages() {
//        // Add additional packages you require here
//        // No need to add RnnPackage and MainReactPackage
//        return Arrays.<ReactPackage>asList(
//                new ActivityStarterReactPackage()
//        );
//    }


    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
        }
    }
    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }@ReactMethod
    void navigateToMain() {

        this.backToMain();
        Log.e("log:: naviagete", "KKKKKKK");
        Intent intent; ;
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
     // this.onBackPressed();
    }
    public void backToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }


}