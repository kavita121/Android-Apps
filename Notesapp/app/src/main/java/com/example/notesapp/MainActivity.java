package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;
    ArrayList<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);

        ListView notesList = findViewById(R.id.listView);

        try {
            set = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(set.size()<1)
        {
            notes.add("Example Note");
        }
        else
        {
            notes = new ArrayList<String>(set);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);
        notesList.setAdapter(adapter);

        final AlertDialog.Builder show = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Delete the note?")
                .setMessage("Do you really want to delete this note?")
                .setNegativeButton("No", null);


        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NotesEditor.class);

                intent.putExtra("noteId",position);

                startActivity(intent);
            }
        });

        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Log.i("position", String.valueOf(position));

                try{
                    show.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            notes.remove(position);
                            adapter.notifyDataSetChanged();

                            try {
                                sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    show.show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.newNote)
        {
            Intent intent = new Intent(this,NotesEditor.class);
            startActivity(intent);
            return true;
        }

        return false;

    }

}