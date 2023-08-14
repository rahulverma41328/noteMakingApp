package com.example.notetaking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UdateNote extends AppCompatActivity {

    Button saveNote;
    ImageButton backBtn;
    EditText headline, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udate_note);

        saveNote = findViewById(R.id.save_note);
        backBtn = findViewById(R.id.back_btn);
        headline = findViewById(R.id.title_card);
        note = findViewById(R.id.note_write);

        String headline1 = getIntent().getStringExtra("headline");
        String note1 = getIntent().getStringExtra("note");

        headline.setText(headline1);
        note.setText(note1);
        AddDatabase db = Room.databaseBuilder(getApplicationContext(), AddDatabase.class,
                "room_db").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDatabase db = Room.databaseBuilder(getApplicationContext(), AddDatabase.class,
                        "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                int Uid = (userDao.getUidByNames(headline1,note1));
                userDao.updateById(headline.getText().toString(),note.getText().toString(),Uid);
                Toast.makeText(getApplicationContext(),"Note Updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}