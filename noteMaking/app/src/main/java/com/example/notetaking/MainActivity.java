package com.example.notetaking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView notesRecyclerView;
    FloatingActionButton floatingActionButton;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRecyclerView = findViewById(R.id.notes_card_view);
        floatingActionButton = findViewById(R.id.add_new_card);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,writeNote.class);
                startActivity(intent);
                finish();
            }
        });

        notesRecyclerView();
    }
    private void notesRecyclerView(){
        String title = getIntent().getStringExtra("title");
        String notesContext = getIntent().getStringExtra("notesContext");
        int spaceCount = 2;
        int spacingPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        boolean includeEdge = true;
        notesRecyclerView.setHasFixedSize(true);
        notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        notesRecyclerView.addItemDecoration(new NotesCardDecoration(this,spaceCount,spacingPixels,includeEdge));

        AddDatabase db = Room.databaseBuilder(getApplicationContext(),AddDatabase.class,"room_db")
                .allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
        List<User> users = userDao.getAllData();

        NotesCardAdapter adapter = new NotesCardAdapter(this,users);
        notesRecyclerView.setAdapter(adapter);
    }
}