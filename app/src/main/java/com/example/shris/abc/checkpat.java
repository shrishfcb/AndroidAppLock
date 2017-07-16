package com.example.shris.abc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.amnix.materiallockview.MaterialLockView;

import java.util.List;

public class checkpat extends AppCompatActivity {
    private MaterialLockView materialLockView;
    private String CorrectPattern="";
    private CheckBox stlth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpat);
        final DatabaseHandler db = new DatabaseHandler(this, null, null, 1);
        boolean ck = db.IsDataInTable(db.getWritableDatabase(), "Main");
        if (ck==false) {
            Intent sb = new Intent(this, enterpat.class);
            startActivity(sb);
        }
        materialLockView = (MaterialLockView) findViewById(R.id.pattern);
        materialLockView.setOnPatternListener(
                new MaterialLockView.OnPatternListener(){
                    @Override
                    public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                        String abc="";
                        abc = db.getPattern("Main");
                        Log.e("SimplePattern", SimplePattern);
                        Log.d("FROM DATABASE", abc);
                        if (!SimplePattern.equals(abc)) {
                            materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Wrong);
                        }

                        else{
                            materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Correct);
                            Intent i = new Intent(getApplicationContext(), enterpat.class);
                            startActivity(i);
                        }
                        super.onPatternDetected(pattern, SimplePattern);
                    }
                }
        );

        stlth = (CheckBox) findViewById(R.id.stealth);
        stlth.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.e("checked", isChecked+"");
                        materialLockView.setInStealthMode(isChecked);
                    }
                }
        );
    }
}
