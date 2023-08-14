package com.example.notetaking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.Update;

import java.util.List;

public class NotesCardAdapter extends RecyclerView.Adapter<NotesCardAdapter.myViewHolder> {
    List<User> users;
    Context context;
    ContextMenu menu;
    public NotesCardAdapter(Context context,List<User> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_card,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.note.setText(users.get(position).getLastName());
        holder.headline.setText(users.get(position).getFirstName());
        holder.notesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UdateNote.class);
                intent.putExtra("headline",users.get(position).getFirstName());
                intent.putExtra("note",users.get(position).getLastName());
                context.startActivity(intent);

                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
       holder.notesCard.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
               popupMenu.inflate(R.menu.delete_btn);
               popupMenu.show();
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       int id = item.getItemId();
                       if (id == R.id.delete_note){
                           AddDatabase db = Room.databaseBuilder(holder.note.getContext(),AddDatabase.class,
                                   "room_db").allowMainThreadQueries().build();
                           UserDao userDao = db.userDao();
                           userDao.deleteById(users.get(position).getUid());
                           users.remove(position);
                           notifyDataSetChanged();
                       }
                       return false;
                   }
               });
               return false;
           }
       });
        AddDatabase db = Room.databaseBuilder(holder.note.getContext(),AddDatabase.class,
                "room_db").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView headline, note;
        CardView notesCard;
        int id;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            headline = itemView.findViewById(R.id.heading);
            note = itemView.findViewById(R.id.note);
            notesCard = itemView.findViewById(R.id.notes_card);
        }
    }
}
