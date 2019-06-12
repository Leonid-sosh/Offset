package com.example.offset;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class Edit_Activity extends AppCompatActivity {

    EditText title;
    EditText note;
    Button save;
    SharedPreferences sPref;
    String name_edit;
    String name;
    String cur_note;
    boolean par;

    public final static String THIEF = "new_note";
    final String TAG = "States";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        title = (EditText) findViewById(R.id.title);
        note = (EditText) findViewById(R.id.editText);
        save = (Button) findViewById(R.id.btn1);

        setTitle("Редактирование");
    }

    public void onClick_edit(View view) {
        name_edit = "";
        name_edit = title.getText().toString();

        Intent answerIntent_new = new Intent();
        answerIntent_new.putExtra(THIEF, name_edit);
        setResult(RESULT_OK, answerIntent_new);
        save_txt();
        finish();
    }

    private void save_txt(){
        sPref = getSharedPreferences(title.getText().toString(), MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(TAG, note.getText().toString());
        ed.commit();
    }

    private void load_txt(){

        Bundle arguments = getIntent().getExtras();

        name = arguments.get("edit").toString();
        title.setText(name);

        sPref = getSharedPreferences(name, MODE_PRIVATE);
        String saved_txt = sPref.getString(TAG, "");

        if(par){
            note.setText(cur_note);
        }
        else {
            note.setText(saved_txt);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
        load_txt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
        cur_note = note.getText().toString();
        par = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity: onRestart()");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

