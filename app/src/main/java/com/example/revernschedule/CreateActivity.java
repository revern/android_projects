package com.example.revernschedule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;


public class CreateActivity extends ActionBarActivity {

    public static final String EXTRA_CREATE_D = "EXTRA CREATE_D";
    public static final String EXTRA_CREATE_S = "EXTRA CREATE_S";
    public static final String EXTRA_CREATE_UPDATE = "EXTRA CREATE UPDATE";

    ListView mActionList;
    Button mAddActionButton;
    Button mOkButton;
    Button mCancelButton;
    TextView mNameView;
    DayPattern day;
    String act;
    ArrayAdapter<String> adapter;
    ArrayList<String> actions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        String json=getIntent().getStringExtra(EXTRA_CREATE_UPDATE);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actions);
        mActionList = (ListView)findViewById(R.id.action_list);
        mAddActionButton = (Button)findViewById(R.id.add_action_button);
        mOkButton = (Button)findViewById(R.id.create_ok_button);
        mCancelButton =(Button)findViewById(R.id.create_cancel_button);
        mNameView = (TextView)findViewById(R.id.create_name_view);
        if(!json.equals("creating from start")) {
            day = new Gson().fromJson(json, DayPattern.class);
            mNameView.setText(day.getName());
            actionsUpdate();
            updateList();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateActivity.this);
            builder.setTitle("Creating").setMessage("Enter name of pattern");
            final EditText input = new EditText(CreateActivity.this);
            builder.setView(input);
            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    day = new DayPattern(input.getText().toString());
                    mNameView.setText(day.getName());
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        //Changing action

    /*    mActionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CreateActivity.this, AddingActionActivity.class);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                String json = gson.toJson(day.get(position));
                intent.putExtra(AddingActionActivity.EXTRA_ADDING_UPDATE, json);
                startActivityForResult(intent,0);
            }
        });*/

        mActionList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder deleting = new AlertDialog.Builder(CreateActivity.this);
                deleting.setTitle("Action deleting").setMessage("Are you sure?");
                final int pos=position;
                deleting.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        day.remove(pos);
                        actionsUpdate();
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
                return false;
            }
        });
        mAddActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateActivity.this, AddingActionActivity.class);
                i.putExtra(AddingActionActivity.EXTRA_ADDING_UPDATE, "adding new action");
                startActivityForResult(i, 0);
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                String json = gson.toJson(day);
                Intent data = new Intent();
                data.putExtra(EXTRA_CREATE_D, day.getName());
                data.putExtra(EXTRA_CREATE_S,json);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        act = data.getStringExtra(AddingActionActivity.EXTRA_ACTION);
        Action action = new Gson().fromJson(act, Action.class);
        day.addAction(action);
        actionsUpdate();
        updateList();
    }
    public void actionsUpdate(){
        actions=new ArrayList<>();
        for(int i=0;i<day.size();i++){
            actions.add(day.get(i).getMainAction() + " " + day.get(i).getStartHour() + ":" + day.get(i).getStartMinute() + "-" + day.get(i).getFinishHour() + ":" + day.get(i).getFinishMinute() + " " + day.get(i).getSubAction());
        }
    }
    public void updateList(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actions);
        mActionList.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
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
