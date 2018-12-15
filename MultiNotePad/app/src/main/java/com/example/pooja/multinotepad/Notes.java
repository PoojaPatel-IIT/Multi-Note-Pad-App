package com.example.pooja.multinotepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Notes extends AppCompatActivity {
    private NewNotes new_nt;
    private Calendar cal;
    private static final String TAG = "Notes";
    private EditText Note;
    private EditText Title;

    private String datetime;
    private SimpleDateFormat for_dt_simple;
    private int loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Title = findViewById(R.id.noteTitleEditText);
        Note = findViewById(R.id.desEditText);
        Note.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        if (intent.hasExtra(NewNotes.class.getName())) {
            new_nt = (NewNotes) intent.getSerializableExtra(NewNotes.class.getName());
            Title.setText(new_nt.getTitle());
            Note.setText(new_nt.getNote());
            datetime = (String) intent.getSerializableExtra(new_nt.getDatetime());
        }
        loc = (Integer) intent.getSerializableExtra("");
    }
    public void displaying_dialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage("Note not saved! Do you want to save note '"+Title.getText().toString()+"'?");
        // b.setIcon(R.drawable.ic_note_black_48dp);
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent dd = new Intent();
                //

                if (Title.getText().toString().trim().equals("")){
                    dd.putExtra("MESSAGE", "The un-titled activity was not saved");
                }

                //
                else if (new_nt != null && new_nt.getTitle().equals(Title.getText().toString())
                        && new_nt.getNote().equals(Note.getText().toString())){
                    dd.putExtra("DATETIME", new_nt.getDatetime());
                }
                else{
                    dd.putExtra("DATETIME", date_setup());
                }
                dd.putExtra("INDEX", loc);
                dd.putExtra("TITLE", Title.getText().toString());
                dd.putExtra("NOTES", Note.getText().toString());
                setResult(RESULT_OK, dd);
                finish();
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = b.create();
        dialog.show();
    }

    public String date_setup(){
        String d1;
        Date date  = new Date(System.currentTimeMillis());
        cal = Calendar.getInstance();
       //  SimpleDateFormat("MMM  dd, hh:mm aa").format(Calendar.getInstance().getTime()));
        for_dt_simple = new SimpleDateFormat("EEE MMM  dd, hh:mm:ss aa ");
      // returning formatted date
        return for_dt_simple.format(cal.getTime());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.savenote:
                Intent intent_save = new Intent();

                if (Title.getText().toString().trim().equals("")){
                    intent_save.putExtra("MESSAGE", "The un-titled activity was not saved");
                }
                else
                { if (new_nt != null   && new_nt.getTitle().equals(Title.getText().toString())
                            && new_nt.getNote().equals(Note.getText().toString()) ){
                        intent_save.putExtra("DATETIME", new_nt.getDatetime());
                    }else{ intent_save.putExtra("DATETIME", date_setup());
                    }intent_save.putExtra("TITLE", Title.getText().toString());
                    intent_save.putExtra("NOTES", Note.getText().toString());
                }
                intent_save.putExtra("INDEX", loc);
                setResult(RESULT_OK, intent_save);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newnotepage, menu);
        return true;
    }

// for on backpress
    @Override
    public void onBackPressed() {
        Intent dd = new Intent();
        if (Title.getText().toString().trim().equals("")){
            Log.d(TAG, "onBackPressed: here");
          //  setResult(RESULT_OK, intent_save);
            dd.putExtra("MESSAGE", "The un-titled activity was not saved");
            setResult(RESULT_OK, dd);
            finish();
        }if(new_nt != null &&Title.getText().toString().length() > 0 &&
                (!(new_nt.getTitle().equals(Title.getText().toString())) || !(new_nt.getNote().equals(Note.getText().toString())))){
             displaying_dialog();
        } else if (new_nt == null &&
                (Title.getText().toString().length() > 0 || Note.getText().toString().length() > 0)){
             displaying_dialog();
        }else { super.onBackPressed(); }
    }


}
