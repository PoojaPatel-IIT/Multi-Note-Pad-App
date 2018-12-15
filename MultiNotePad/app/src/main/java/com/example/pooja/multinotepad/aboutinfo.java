package com.example.pooja.multinotepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
public class aboutinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // setting the view for activity_aboutinfo
        setContentView(R.layout.activity_aboutinfo);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // if info menu is selected
        if(item.getItemId() == R.id.aboutinfo)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // inflating info menu
        getMenuInflater().inflate(R.menu.aboutinfo,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
