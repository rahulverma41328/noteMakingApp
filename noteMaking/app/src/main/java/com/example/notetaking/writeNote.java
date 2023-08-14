package com.example.notetaking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class writeNote extends AppCompatActivity {

    Button saveNote;
    ImageButton backBtn;
    EditText headline,note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        saveNote = findViewById(R.id.save_note);
        backBtn = findViewById(R.id.back_btn);
        headline = findViewById(R.id.title_card);
        note = findViewById(R.id.note_write);

        String headline1 = getIntent().getStringExtra("headline");
        String note1 = getIntent().getStringExtra("note");

        headline.setText(headline1);
        note.setText(note1);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new bgthread().start();
                headline.setText("");
                note.setText("");
            }
        });
    }
    class bgthread extends Thread{
        @Override
        public void run() {
            super.run();

            AddDatabase db = Room.databaseBuilder(getApplicationContext(),AddDatabase.class,
                    "room_db").allowMainThreadQueries().build();
            UserDao userDao = db.userDao();
            userDao.insertRecord(new User(headline.getText().toString(),note.getText().toString()));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}