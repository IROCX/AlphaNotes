package com.example.alphanotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    TextView textView;
    Button button;
    ArrayList<String> notesData;
    SharedPreferences sharedPreferences;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        textView = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        notesData = new ArrayList<>();

        if (notesData.size() > 0) {
            notesData.clear();
            try {
                notesData = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("data", ObjectSerializer.serialize(new ArrayList<String>())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sharedPreferences = this.getSharedPreferences("com.example.alphanotes", Context.MODE_PRIVATE);
        try {
            notesData = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("data", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notesData);
        listView.setAdapter(arrayAdapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, NoteEditor.class);
                startActivityForResult(intent, 1);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent setterIntent;
                setterIntent = new Intent(MainActivity.this, NoteEditor.class);
                setterIntent.putExtra("savedData", notesData.get(i));
                startActivityForResult(setterIntent, 1);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, final long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int k) {

                                notesData.remove(i);
                                arrayAdapter.notifyDataSetChanged();
                                try {
                                    sharedPreferences.edit().putString("data", ObjectSerializer.serialize(notesData)).apply();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                    }
                }).setMessage("Are you sure to delete this note?").show();
                return true;
            }
        });


//        if (titles.size() > 0) {
//            listView.setVisibility(View.VISIBLE);
//            textView.setVisibility(View.GONE);
//        } else {
//            listView.setVisibility(View.GONE);
//            textView.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAg", requestCode + " " + resultCode);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("addData").matches("")) {
                } else {
                    notesData.add(data.getStringExtra("addData"));
                    arrayAdapter.notifyDataSetChanged();
                    try {
                        sharedPreferences.edit().putString("data", ObjectSerializer.serialize(notesData)).apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
