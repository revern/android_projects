package com.example.revernschedule;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ScheduleActivity extends ActionBarActivity {

    Button mCreateButton;
    ListView mPatternList;
    String dayName;
    String schedule;
    ArrayAdapter<String> adapter;
    ArrayList<String> patterns = new ArrayList<>();
    Set<String> patternsSET;
    DbSchedule dbHelper;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        sPref = getPreferences(MODE_PRIVATE);
        mCreateButton = (Button)findViewById(R.id.create_button);
        mPatternList = (ListView)findViewById(R.id.pattern_list);
        dbHelper = new DbSchedule(this);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScheduleActivity.this, CreateActivity.class);
                i.putExtra(CreateActivity.EXTRA_CREATE_UPDATE, "creating from start");
                startActivityForResult(i, 0);
            }
        });
        mPatternList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ScheduleActivity.this, CreateActivity.class);
                intent.putExtra(CreateActivity.EXTRA_CREATE_UPDATE, sPref.getString(patterns.get(position),""));
                startActivityForResult(intent,0);
            }
        });
        mPatternList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder deleting = new AlertDialog.Builder(ScheduleActivity.this);
                deleting.setTitle("Pattern deleting").setMessage("Are you sure?");
                final int pos=position;
                deleting.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.remove(patterns.get(pos));
                        ed.commit();
                        updatePatterns();
                        updateList();
                    }
                });
                deleting.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog delete=deleting.create();
                delete.show();
                return true;
            }
        });
        updatePatterns();
        updateList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        dayName = data.getStringExtra(CreateActivity.EXTRA_CREATE_D);
        schedule = data.getStringExtra(CreateActivity.EXTRA_CREATE_S);

        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(dayName, schedule);
        ed.commit();
        updateList();
    }
    public void updatePatterns(){
        Map<String,?> map;
        map=sPref.getAll();
        patternsSET=map.keySet();
        patterns.clear();
        Iterator iterator = patternsSET.iterator();
        while(iterator.hasNext()){
            patterns.add(iterator.next().toString());
        }
    }
    public void updateList(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patterns);
        mPatternList.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
