package com.example.shris.abc;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class WindowChangeDetection extends AccessibilityService {

    public boolean sb = true;
    @Override
    protected void onServiceConnected(){
        super.onServiceConnected();

        //for less than API 13
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        if (Build.VERSION.SDK_INT >= 16)
            config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
    }

    @Override
    public void onInterrupt(){

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event){
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.e("sb", String.valueOf(sb));
            DatabaseHandler dbha = new DatabaseHandler(this, null, null, 1);
            ComponentName c = null;
            if (event.getPackageName() != null && event.getClassName() != null) {
                 c = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );
            }
                String n = getAppName(c);
                boolean is = dbha.CheckRecord(n);
                if (n.equals("Abc"))
                    is = true;
            Log.e("is", String.valueOf(is));
            if ((sb==false) && (is==true)) {
                sb = true;
                Log.e("jad", "khgih");
            }
            else {
                Log.e("jad", "khgacscscih");
                if (event.getPackageName() != null && event.getClassName() != null) {
                    ComponentName componentName = new ComponentName(
                            event.getPackageName().toString(),
                            event.getClassName().toString()
                    );

                    ActivityInfo activityInfo = tryGetActivity(componentName);
                    String nam = getAppName(componentName);
                    DatabaseHandler dbh = new DatabaseHandler(this, null, null, 1);
                    boolean isthere;
                    boolean isActivity = activityInfo != null;
                    if (isActivity) {
                        Log.i("CurrentActivity", componentName.flattenToShortString());
                        Log.i("AppName", nam);
                        if (componentName.getClassName().equals("com.example.shris.abc.EnterPattern"))
                            sb = false;
                        isthere = dbh.CheckRecord(nam);
                        if (isthere) {
                            Intent i = new Intent(this, EnterPattern.class);
                            i.putExtra("Appname", nam);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    }
                }
            }
        }
    }

    private ActivityInfo tryGetActivity(ComponentName componentName){
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        }
        catch (PackageManager.NameNotFoundException e){
            return null;
        }
    }

    private String getAppName(ComponentName componentName){
        try {
            return (getPackageManager().getApplicationLabel(getPackageManager().getApplicationInfo(componentName.getPackageName(),0))).toString();
        }
        catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
