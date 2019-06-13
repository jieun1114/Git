package com.jieun.recyclerview;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MTripItem extends AppCompatActivity {

    Button day;
    Button time;
    EditText sche;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifyitem);

        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("일정 수정") ;

        day = (Button) findViewById(R.id.Day1);
        time = (Button) findViewById(R.id.Time1) ;
        sche = (EditText) findViewById(R.id.Sche1) ;

        Intent intent1 = getIntent();

        day.setText(intent1.getStringExtra("oldday"));
        time.setText(intent1.getStringExtra("oldtime"));
        sche.setText(intent1.getStringExtra("oldsche"));

        if(intent1.getStringExtra("oldday").isEmpty())
            day.setText("날짜 선택");

        Button mbtn = (Button) findViewById(R.id.modifybtn);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                String inputDay = day.getText().toString();
                String inputTime = time.getText().toString();
                String inputSche = sche.getText().toString();
                if(!inputTime.isEmpty() && !inputSche.isEmpty() && !inputDay.equals("날짜 선택")){
                    intent.putExtra("DAY",inputDay);
                    intent.putExtra("TIME", inputTime);
                    intent.putExtra("SCHEDULE", inputSche);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalender = Calendar.getInstance();
                int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                int minute = myCalender.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        if (view.isShown()) {
                            String h;
                            String m;
                            if(selectedHour < 10)
                                h = "0" + Integer.toString(selectedHour);
                            else
                                h = Integer.toString(selectedHour);

                            if(selectedMinute < 10)
                                m = "0" + Integer.toString(selectedMinute);
                            else
                                m = Integer.toString(selectedMinute);

                            time.setText(h + ":" + m);
                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(MTripItem.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
                timePickerDialog.setTitle("시간 선택");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = { "Day1", "Day2", "Day3"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MTripItem.this);

                alertDialogBuilder.setTitle("여행 일차 선택");
                alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //Toast.makeText(getApplicationContext(), items[id] + " 선택했습니다.", Toast.LENGTH_SHORT).show();
                        day.setText(items[id]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog2 = alertDialogBuilder.create();
                alertDialog2.show();

            }
        });

    }


    public void cancel(View v){
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home :
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default :
                return false;
        }
    }


}
