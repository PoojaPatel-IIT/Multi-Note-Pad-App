package com.example.pooja.multinotepad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    // declaration here
    private List<NewNotes> lOFNotes = new ArrayList<>();
    private RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    private MultiNotesAdapter adapter_mulNotes;

    private static final int REQ = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.re);
        adapter_mulNotes = new MultiNotesAdapter(lOFNotes, this);
        recyclerView.setAdapter(adapter_mulNotes);
        // setting layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // load the notes present
        loadfiles();
        new AsyncTaskMultiNotes(this).execute();
    }


    @Override
    protected void onPause() {
        super.onPause();
        json_note();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainnotespage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutinfo:
                Intent intent_about = new Intent(this, aboutinfo.class);
                startActivity(intent_about);
                return true;
            case R.id.newnote:
                Intent intent_add = new Intent(this, Notes.class);
                intent_add.putExtra("", -1);
                startActivityForResult(intent_add, REQ);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void loadfiles() {
        File doc_case = this.getFileStreamPath("new_note.json");
        if (!doc_case.exists()) {
            try { FileOutputStream out =
                    getApplicationContext().openFileOutput("new_note.json", Context.MODE_PRIVATE);
                OutputStreamWriter w = new OutputStreamWriter(out);
                w.write(" ");
                w.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onClick(View v) {
        int positions = recyclerView.getChildAdapterPosition(v);
        NewNotes n = lOFNotes.get(positions);
        Intent i = new Intent(MainActivity.this, Notes.class);
        i.putExtra("", positions);
        i.putExtra(NewNotes.class.getName(), n);
        startActivityForResult(i, REQ);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // loading files on resume
        loadfiles();
        // executing AsyncTask on resume
        new AsyncTaskMultiNotes(this).execute();
    }


    @Override
    public void onActivityResult(int request, int result, Intent d) {
        if (request == REQ && result == RESULT_OK) {
            if (d.hasExtra("MESSAGE")) {
              //  Log.d(TAG, "onActivityResult: here");
                Toast.makeText(this, d.getStringExtra("MESSAGE"), Toast.LENGTH_SHORT).show();
            } else {
                String title = d.getStringExtra("TITLE");
                String datetime = d.getStringExtra("DATETIME");
                String note = d.getStringExtra("NOTES");
                int position = d.getIntExtra("INDEX", -1);
                if (position != -1 && lOFNotes.size() > 0) {
                    NewNotes n = lOFNotes.get(position);
                    n.setTitle(title);
                    n.setDatetime(datetime);
                    n.setNote(note);
                } else lOFNotes.add(new NewNotes(note, title, datetime));
                json_note();
            }
        }
    }



    private void json_note() {
        try {
            FileOutputStream file_out_stream = getApplicationContext().openFileOutput("new_note.json",
                    Context.MODE_PRIVATE);
            JsonWriter jsn_wrtr =
                    new JsonWriter(new OutputStreamWriter(file_out_stream, "UTF-8"));
            jsn_wrtr.setIndent("  ");
            jsn_wrtr.beginArray();
            // looping for listnote
            for (NewNotes n : this.lOFNotes)
            {
                jsn_wrtr.beginObject();
                jsn_wrtr.name("title").value(n.getTitle());
                jsn_wrtr.name("datetime").value(n.getDatetime());
                jsn_wrtr.name("notes").value(n.getNote());
                jsn_wrtr.endObject();
            }
            jsn_wrtr.endArray();
            jsn_wrtr.close();
            
            
            
            //  json start

            Log.d(TAG, "saveNotes: file created");

            StringWriter stringwriter = new StringWriter();
            jsn_wrtr = new JsonWriter(stringwriter);
            jsn_wrtr.setIndent("  ");
            jsn_wrtr.beginArray();

            for (int i = 0; i < lOFNotes.size(); i++) {
                jsn_wrtr.beginObject();
                jsn_wrtr.name("title").value((lOFNotes.get(i).getTitle()));
                jsn_wrtr.name("date").value((lOFNotes.get(i).getDatetime()));
                jsn_wrtr.name("description").value(lOFNotes.get(i).getNote());
                jsn_wrtr.endObject();

            }
            Log.d(TAG, "json_note: "+lOFNotes.size());
            jsn_wrtr.endArray();
            jsn_wrtr.close();
            Log.d(TAG, "saveNotes: JSon created" + stringwriter.toString());
            
            
            // json end
        } catch (Exception e) {
        }
    }

    public void getasyncdata(ArrayList<NewNotes> new_list) {
        if (new_list == null) return;
        // updating list
        adapter_mulNotes.refreshing(new_list);
        Log.d(TAG, "getasyncdata: "+new_list);
    }


    @Override
    // for deleting note on long click
    public boolean onLongClick(View v) {
        final int loc = recyclerView.getChildLayoutPosition(v);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // Showing message for deleting the note
        alert.setMessage("Delete Note '" + lOFNotes.get(loc).getTitle() + "'?");
        alert.setIcon(R.drawable.delete_icon);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                lOFNotes.remove(loc);
                // notify change as user clicks YES
                adapter_mulNotes.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                // if user clicks no then do nothing
            }
        });AlertDialog dialog = alert.create();
        dialog.show();
        return false;
    }




}


