package ir.daap;


import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

/**
 * Static storage of the React instance, used to share a JS heap across the activity and service.
 */
public class ReactInstanceSingleton {
    private static volatile ReactInstanceManagerBuilder sSingletonManagerBuilder;

    public static synchronized ReactInstanceManager getReactInstanceManager(
            Application application) {
        if (sSingletonManagerBuilder == null) {
            sSingletonManagerBuilder = ReactInstanceManager.builder()
                    .setApplication(application)
                    .setJSMainModulePath("index")
                    .setBundleAssetName("index.android.bundle")
                    .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setInitialLifecycleState(LifecycleState.RESUMED);

            for (ReactPackage reactPackage : getPackages()) {
                sSingletonManagerBuilder.addPackage(reactPackage);
            }
        }
        return sSingletonManagerBuilder.build();
    }
    private static List<ReactPackage> getPackages() {
        return Arrays.asList(
               new MainReactPackage(),
               new ActivityStarterReactPackage()
        );
    }
}