package com.example.pooja.multinotepad;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by pooja on 15,September,2018
 */


public class NewNotes implements Serializable
{
    public NewNotes(String nt,String nti, String nd)
    {
        des = nt;
        title = nti;
        datetime = nd;
    }
    private String des;
    private String title;
    private String datetime;

    public String getNote() {
        return des;
    }

    public void setNote(String note)
    {
        this.des = note;
    }
    public String getDatetime()
    { return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}

