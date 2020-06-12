package ir.daap;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private Button btnClickMe;
    private Button btnClickM2;
    private AppBarConfiguration mAppBarConfiguration;
    private final int OVERLAY_PERMISSION_REQ_CODE = 1;  // Choose any value
    private ReactInstanceManager mReactInstanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //Intialization Button

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
        btnClickMe = (Button) findViewById(R.id.button);
        btnClickM2 = (Button) findViewById(R.id.button2);
        btnClickMe.setOnClickListener(MainActivity.this);
        btnClickM2.setOnClickListener(MainActivity.this);
        String qrCode = "876398776";
        if(mReactInstanceManager != null){
            ReactContext reactContext = mReactInstanceManager.getCurrentReactContext();
            if (reactContext != null) {
//            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                    .emit("qrCode", qrCode);
            }
        }

        String msg=getIntent().getStringExtra("message");
        if(msg !=null &&  msg.length()>0){
            Log.e("log:: message in Main",msg);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button:
                // do your code

                Log.e("log:: User Res", "onClick  onClick" );
                Intent intent = new Intent(MainActivity.this, MyReactActivity.class);
                intent.putExtra("message", "EXTRA_SESSION_ID");
                startActivity(intent);
                break;

            case R.id.button2:
                // do your code
                Log.e("log:: User Res", "goToAuthReact  goToAuthReact" );
                intent = new Intent(MainActivity.this, AuthReactActivity.class);
                intent.putExtra("message", "AuthReactActivity");
                startActivity(intent);
                break;


            default:
                break;
        }
    }
    public void goToAuthReact(View v) {

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted
                }
            }
        }
        mReactInstanceManager.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap map) {

    }
    @ReactMethod
    public void saveUser(ReadableMap userData) {
        Log.e("log:: User Res", "" + userData);
    }
}
