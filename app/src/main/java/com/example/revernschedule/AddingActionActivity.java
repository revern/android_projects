package com.example.revernschedule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class AddingActionActivity extends ActionBarActivity {

    public static final String EXTRA_ACTION = "EXTRA ACTION";
    public static final String EXTRA_ADDING_UPDATE = "EXTRA ADDING UPDATE";

    Button mCancelButton;
    Button mAddButton;
    Button mStartTime;
    Button mFinishTime;
    EditText mMainAction;
    EditText mSubAction;
    Action action;
    boolean time1;
    boolean time2;
    int myHour;
    int myMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_action);
        mCancelButton = (Button)findViewById(R.id.add_action_cancel_button);
        mAddButton = (Button)findViewById(R.id.add_action_add_button);
        mStartTime = (Button)findViewById(R.id.start_time);
        mFinishTime = (Button)findViewById(R.id.finish_time);
        mMainAction = (EditText)findViewById(R.id.main_action);
        mSubAction = (EditText)findViewById(R.id.sub_action);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        action = new Action();
        mStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        mFinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!mMainAction.getText().toString().equals("") && time1 && time2 && (action.getStartHour()<action.getFinishHour() || action.getStartMinute()<action.getFinishMinute() && action.getStartHour()==action.getFinishHour()) ){
                   action.setMainAction(mMainAction.getText().toString());
                   if(!mSubAction.getText().toString().equals("")){
                       action.setSubAction(mSubAction.getText().toString());
                   }
                   Gson gson = new GsonBuilder()
                           .setPrettyPrinting()
                           .create();
                   String json = gson.toJson(action);
                   Intent data = new Intent();
                   data.putExtra(EXTRA_ACTION,json);
                   setResult(RESULT_OK, data);
                   finish();
               }
            }
        });

        //Changing action

        /*String json = getIntent().getStringExtra(EXTRA_ADDING_UPDATE);
        if(!json.equals("adding new action")){
            action = new Gson().fromJson(json, Action.class);
            time1=true;
            setTime(mStartTime,action.getStartHour(),action.getStartMinute());
            time2=true;
            setTime(mFinishTime,action.getFinishHour(),action.getFinishMinute());
            mMainAction.setText(action.getMainAction());
            mSubAction.setText(action.getSubAction());
        }*/
    }

    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack1, myHour, myMinute, true);
            return tpd;
        }else if(id == 2){
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack2, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }
    public  void setTime(Button button,int hour,int minute){
        if(minute>=10) {
            button.setText(hour + ":" + minute);
        }else{
            button.setText(hour + ":0" + minute);
        }
    }
    TimePickerDialog.OnTimeSetListener myCallBack1 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            action.setStartHour(hourOfDay);
            action.setStartMinute(minute);
            time1=true;
            setTime(mStartTime,hourOfDay,minute);
        }
    };
    TimePickerDialog.OnTimeSetListener myCallBack2 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            action.setFinishHour(hourOfDay);
            action.setFinishMinute(minute);
            time2=true;
            setTime(mFinishTime,hourOfDay,minute);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adding_action, menu);
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
