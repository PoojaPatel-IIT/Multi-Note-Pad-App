package com.example.pooja.multinotepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by pooja on 15,September,2018
 */

public class MultiNotesAdapter extends RecyclerView.Adapter<MultiNotesViewHolder>
{
    private static final String TAG = "MultiNotesAdapter";
    @Override
    public MultiNotesViewHolder onCreateViewHolder(ViewGroup view, int i)
    {
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.notes_list_row, view, false);
        v.setOnClickListener(main_ac);
        v.setOnLongClickListener(main_ac);
        return new MultiNotesViewHolder(v);
    }
    private MainActivity main_ac;

    private List<NewNotes> list_note;

    public MultiNotesAdapter(List<NewNotes> nt, MainActivity m)
    {
        this.list_note = nt;
        this.main_ac = m;
    }




    @Override
    public int getItemCount()
    {
        Log.d(TAG, "getItemCount: "+list_note.size());
        return this.list_note.size();

    }

    @Override
    public void onBindViewHolder(MultiNotesViewHolder ReViewHolder, int posi)
    {
        ReViewHolder.note_title.setText(this.list_note.get(posi).getTitle());
        String showing_des = this.list_note.get(posi).getNote();
        String[] lines = showing_des.split("\\r?\\n");
        ReViewHolder.note_date.setText(this.list_note.get(posi).getDatetime());

        if(showing_des.length()>80) {
            showing_des = showing_des.substring(0,79)+"...";
            ReViewHolder.note_des.setText(showing_des);
        }

        else if (lines.length > 2) {

            for (String line : lines) {

                ReViewHolder.note_des.setText(lines[0] + "\n" + lines[1] + "...");

            }

        }

        else {
            ReViewHolder.note_des.setText(showing_des);
        }
    }

    public void refreshing(List<NewNotes> list_note)
    {
        // refreshing the list
        this.list_note.clear();
        Log.d(TAG, "refreshing: "+list_note);
        this.list_note.addAll(list_note);
        Log.d(TAG, "refreshing: After Addall"+list_note);
        notifyDataSetChanged();
    }

}
