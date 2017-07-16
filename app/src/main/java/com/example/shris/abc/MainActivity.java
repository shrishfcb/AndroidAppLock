package com.example.shris.abc;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list;
    List<String> webs = new ArrayList<String>();
    List<Drawable> img = new ArrayList<Drawable>();
    public static boolean hope=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, WindowChangeDetection.class);
        startService(i);
        int flags = PackageManager.GET_META_DATA |
                PackageManager.GET_SHARED_LIBRARY_FILES;
        String temp="";
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> applications = pm.getInstalledApplications(flags);
        for (ApplicationInfo appInfo : applications){
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1) {
                try {
                    temp = (String) pm.getApplicationLabel(pm.getApplicationInfo(appInfo.packageName, flags));
                }
                catch (Exception e){

                }
                webs.add(temp);
                img.add(appInfo.loadIcon(pm));
            }
        }
        CustomAdapter adapter = new CustomAdapter(MainActivity.this, webs, img);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        Log.d("Hop", String.valueOf(hope));
        if (hope==false)
        {
            //hope = false;
            finish();
        }
    }

    public void Next(View v)
    {
        Intent intent = new Intent(this, checkpat.class);
        startActivity(intent);
    }
}
