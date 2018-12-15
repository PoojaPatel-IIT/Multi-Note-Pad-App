package com.example.pooja.multinotepad;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pooja on 21,September,2018
 */

public class AsyncTaskMultiNotes extends AsyncTask<String, Integer, String> {
    private int len_list;
    private static final String TAG = "AsyncTaskMultiNotes";
    // Main activity
    private MainActivity mainact;
    //constructor
        public AsyncTaskMultiNotes(MainActivity mainone)
        {
            mainact = mainone;
        }

    @Override
    protected String doInBackground(String... strings)
    {
        return look_through();
    }        private ArrayList<NewNotes> readingjson(String s) {
            //arraylist notelist
            ArrayList<NewNotes> notelist = new ArrayList<>();
            try {
                // creating sorted list
                List sorted = new ArrayList();
                Log.d(TAG, "readingjson: " + len_list);
                JSONArray list = new JSONArray(s); // making list
                len_list = list.length();
                Log.d(TAG, "readingjson: "+len_list);
                List<JSONObject> jo = new ArrayList<JSONObject>();
                for (int le = 0; le < len_list; le++) {
                    jo.add(list.getJSONObject(le));
                }Collections.sort( jo, new Comparator<JSONObject>() {
                   // private static final String z = "datetime";
                    @Override
                    public int compare(JSONObject json_obj1, JSONObject json_jo) {
                        String str1 = new String();
                        String str2 = new String();
                        try {
                            str1 = (String) json_obj1.get("datetime");
                            str2 = (String) json_jo.get("datetime");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        return -str1.compareTo(str2);
                    }
                });

                for (int p = 0; p < list.length(); p++)
                {
                    sorted.add(jo.get(p));
                }
                // looping for sorted list
                for (int q = 0; q < sorted.size(); q++)
                { JSONObject new_json_object = (JSONObject) sorted.get(q);
                String title = new_json_object.getString("title");

                    String datetime = new_json_object.getString("datetime");
                    String notes = new_json_object.getString("notes");
                    notelist.add(new NewNotes(notes, title, datetime));
                }
        // returning notelist
                return notelist;
            } catch (Exception e) { return null; }
        }private String look_through()
        { try
            {
                // inputStream
                InputStream theInputStream = mainact.getApplicationContext().openFileInput("new_note.json");
                // creating buffer of size available
                byte[] size_b = new byte[theInputStream.available()];
                theInputStream.read(size_b);
                // closing inputStream
                theInputStream.close();
                return new String(size_b, "UTF-8");
            }
            catch (FileNotFoundException e)
            { return null;
            }
            catch (Exception e)
            {
                return null;
            }
        }

    @Override
    protected void onPostExecute(String s) {
        //storing in list
        ArrayList<NewNotes> list = readingjson(s);
        mainact.getasyncdata(list);
    }

}


