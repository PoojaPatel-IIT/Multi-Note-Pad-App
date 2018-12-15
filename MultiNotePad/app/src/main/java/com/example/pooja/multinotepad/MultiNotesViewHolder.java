package com.example.pooja.multinotepad;


import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by pooja on 15,September,2018
 */
public class MultiNotesViewHolder extends RecyclerView.ViewHolder
{
    TextView note_title;
    TextView note_date;
    TextView note_des;
    MultiNotesViewHolder(View item)
    {
        super(item);
        note_title = item.findViewById(R.id.NoteTitle);
        note_date = item.findViewById(R.id.NoteDate);
        note_des = item.findViewById(R.id.notedes);
    }
}
