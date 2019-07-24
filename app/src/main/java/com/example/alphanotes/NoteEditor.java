package com.example.alphanotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NoteEditor extends AppCompatActivity {

    EditText body;
    Button saver;

    @Override
    public void onBackPressed() {

        Log.i("Note", body.getText().toString());
        Intent dataPass = new Intent();
        dataPass.putExtra("addData", body.getText().toString());
        setResult(RESULT_OK, dataPass);
        finish();
        super.onBackPressed();

//        SharedPreferences sharedPreferences;
//        sharedPreferences = this.getSharedPreferences("com.example.alphanotes", Context.MODE_PRIVATE);

//        MainActivity.data.add(body.getText().toString());
//        MainActivity.arrayAdapter.notifyDataSetChanged();

//        try {
//            sharedPreferences.edit().putString("data",ObjectSerializer.serialize(MainActivity.data)).apply();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        body = findViewById(R.id.editText4);

        Intent reciever = getIntent();
        body.setText(reciever.getStringExtra("savedData"));
        body.setSelection(body.getText().length());
//
    }
}
