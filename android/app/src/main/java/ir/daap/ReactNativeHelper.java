package ir.daap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;

public final class ReactNativeHelper {
    private enum BuildType {

        DEBUG, DEBUG_WITH_REACT_DEV_SUPPORT, WITHOUT_DEV_SUPPORT;

        static BuildType fromBoolean(boolean value) {

            return value ? DEBUG_WITH_REACT_DEV_SUPPORT : WITHOUT_DEV_SUPPORT;
        }
        boolean equals(boolean value) {

            return value ? this == DEBUG_WITH_REACT_DEV_SUPPORT : this == WITHOUT_DEV_SUPPORT;
        }
    }


    @SuppressLint("StaticFieldLeak")
    private static ReactInstanceManager reactInstanceManager;

    private static BuildType buildType = BuildType.DEBUG;


    private ReactNativeHelper() {
        throw new IllegalStateException("can't instantiated");
    }


    public static View initReact(@NonNull Activity activity, boolean useDeveloperSupport, String imageUrl, int value) {

        if (buildType == BuildType.DEBUG) {
            assert reactInstanceManager == null;
            buildType = BuildType.fromBoolean(useDeveloperSupport);
        } else if (!buildType.equals(useDeveloperSupport)) {
            assert reactInstanceManager != null;
            throw new IllegalArgumentException("MODE MUST NOT BE CHANGED DURING APP LIFE CYCLE");
        }

        if (reactInstanceManager == null) {
            reactInstanceManager = getRNInstanceManager(activity, useDeveloperSupport);
        }
        ActivityLifeCycleHandler lifeCycleHandler = new ActivityLifeCycleHandler(activity, reactInstanceManager);
        activity.getApplication().registerActivityLifecycleCallbacks(lifeCycleHandler);
        ReactRootView reactRootView = new ReactRootView(activity);
        Bundle initialProps = new Bundle();
        initialProps.putString("images", imageUrl);
        initialProps.putInt("emoType", value);
        reactRootView.startReactApplication(reactInstanceManager, "HelloWorld", initialProps);
        return reactRootView;
    }


    private static ReactInstanceManager getRNInstanceManager(Activity activity, boolean isdebug) {
        return ReactInstanceManager.builder()
                .setApplication(activity.getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(isdebug)
                .setInitialLifecycleState(LifecycleState.BEFORE_RESUME)
                .build();
    }


}