package com.example.offset;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.pm.ActivityInfo;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{

    final String TAG = "States";
    static final private int CHOOSE_THIEF = 0;

    ListView listView;
    ArrayList nameList = new ArrayList();
    ArrayAdapter adapter;
    Button add;
    String name_main;
    String name;
    AlertDialog.Builder ad;
    Context context;
    int pos;
    String opp;

    private final static String FILE_NAME = "Note_list.txt";

    private boolean isShort = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = MainActivity.this;
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity: onCreate()");
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);
        listView = findViewById(R.id.listview);

        listView.setAdapter(adapter);
        add = (Button) findViewById(R.id.btn1);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        setTitle("Заметки");

        try {
            FileInputStream fstream = new FileInputStream("/data/data/com.example.offset/files/Note_list.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                //if (!strLine.startsWith("\uD83C\uDF81")) {
                nameList.add(strLine);
                adapter.notifyDataSetChanged();
            }
        }
        catch (IOException e) {
            System.out.println("Ошибка");
        }

    }

    public void onClick_new(View view) {
        opp = "add";
        Intent questionIntent_new = new Intent(MainActivity.this,
                note_activity.class);
        startActivityForResult(questionIntent_new, CHOOSE_THIEF);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_THIEF) {
            if (resultCode == RESULT_OK) {
                name_main = data.getStringExtra("new_note");
                if(nameList.contains(name_main)){
                    //nothing to do
                }
                else {
                    if(opp == "add"){
                        nameList.add(name_main);
                    }
                    else {
                        String filename1 = name;
                        final File file1 = new File("/data/data/" + getPackageName() + "/shared_prefs/" + filename1 + ".xml");
                        file1.delete();

                        nameList.set(pos, name_main);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            else {
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isShort = true;
        name = nameList.get(position).toString();

        Intent questionIntent = new Intent(this, Edit_Activity.class);
        questionIntent.putExtra("edit", name);
        startActivityForResult(questionIntent, CHOOSE_THIEF);
        pos = position;
        opp = "edit";

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        String title = "Удалить заметку?";
        String button1String = "Да";
        String button2String = "Отменить";

        String filename = nameList.get(position).toString();

        final File f1 = new File("/data/data/" + getPackageName() + "/shared_prefs/" + filename + ".xml");

        ad = new AlertDialog.Builder(context);
        ad.setTitle(title);  // заголовок
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                nameList.remove(position);
                f1.delete();
                adapter.notifyDataSetChanged();
                Toast.makeText(context, "Заметка удалена", Toast.LENGTH_LONG).show();
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });

        ad.show();

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");

        FileOutputStream fos = null;
            try {
                int count = 0;
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                for(Object prs : nameList){
                    String text = prs+"\n";
                    fos.write(text.getBytes());
                    count += 1;
                }
            }
            catch(IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finally{
                try{
                    if(fos!=null)
                        fos.close();
                }
                catch(IOException ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


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
}
