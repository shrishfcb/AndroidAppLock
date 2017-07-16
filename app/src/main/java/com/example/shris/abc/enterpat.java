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

public class enterpat extends AppCompatActivity {

    private static MaterialLockView materialLockView;
    private static CheckBox stlth;
    public final static String em = "com.example.shris.patternlockfinal";
    private static String msg = "";
    private TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpat);
        materialLockView = (MaterialLockView) findViewById(R.id.pattern);
        materialLockView.setOnPatternListener(
                new MaterialLockView.OnPatternListener(){
                    @Override
                    public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                        msg = SimplePattern;
                        info = (TextView) findViewById(R.id.textView);
                        if(msg.length()<4){
                            info.setText("Please connect at least 4 dots");
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

    public void Next1(View view){
        if (!(msg.length()<4)) {
            Intent intent = new Intent(this, ConfirmPattern.class);
            intent.putExtra(em, msg);
            //Log.d("Message is ", msg);
            startActivity(intent);
        }
    }
}
