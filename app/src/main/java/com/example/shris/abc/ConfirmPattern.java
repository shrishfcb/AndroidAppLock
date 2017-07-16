package com.example.shris.abc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.amnix.materiallockview.MaterialLockView;

import java.util.List;
public class ConfirmPattern extends AppCompatActivity {

    private static MaterialLockView materialLockView;
    private static CheckBox stlth;
    public final static String eme = "com.example.shris.patternlockfinal.confirm";
    private static String msg = "";
    private TextView info;
    private static Button b1;
    private static String msgr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pattern);
        Log.d("ConfirmActivity", eme);
        Intent intent = getIntent();
        msgr = intent.getStringExtra(enterpat.em);
        materialLockView = (MaterialLockView) findViewById(R.id.pattern);
        materialLockView.setOnPatternListener(
                new MaterialLockView.OnPatternListener(){
                    @Override
                    public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                        msg = SimplePattern;
                        info = (TextView) findViewById(R.id.textView);
                        if(!(msg.equals(msgr))){
                            materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Wrong);
                            info.setText("Pattern is not the same");
                        }
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

    public void Next(View view){
        final DatabaseHandler dbh = new DatabaseHandler(this,null,null,1);
        if (msg.equals(msgr)) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    dbh.addNewVal("Pattern", msg, "Main");
                }
            };
            Thread dbt = new Thread(r);
            dbt.start();
            Intent intents = new Intent(this, MainActivity.class);
            startActivity(intents);
        }
    }
}
