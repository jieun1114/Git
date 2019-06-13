package com.jieun.recyclerview;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.jsoup.select.Evaluator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyTripActivity extends AppCompatActivity{

    Button editTextDay;
    Button editTextTime;
    EditText editTextSche;
    ListView tripList;
    static final int GET_STRING = 1;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    Cursor res;
    CustomAdapter customAdapter;
    int clickPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrip_db);

        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("내 여행 일정") ;

        tripList = (ListView) findViewById(R.id.triplist);
        editTextDay = (Button) findViewById(R.id.Day);
        editTextTime = (Button) findViewById(R.id.Time) ;
        editTextSche = (EditText) findViewById(R.id.Schedule) ;

        customAdapter = new CustomAdapter();

        View header = getLayoutInflater().inflate(R.layout.tripdb_header, null, false) ;
        header.setEnabled(false);
        header.setOnClickListener(null);
        tripList.addHeaderView(header);

        dbHelper = new DatabaseHelper(this);

        load_values();

        Intent intent = getIntent();
        if(intent.getExtras()!= null) {
            String place = intent.getStringExtra("place");

            try {
                db = dbHelper.getWritableDatabase();

                if(place != null) {
                    String day = "";
                    String time = "00:00";
                    String sqlInsert = DBContract.SQL_INSERT + " ("  + "'" + day + "'," + "'" + time + "'," + "'" + place + "'" + ")";
                    db.execSQL(sqlInsert);

                    customAdapter.removeAll();
                    load_values();

                }

            }catch (SQLException ex){
                Log.d("Tag","SQL Insert Exception");
            }

            intent.getExtras().clear();

        }
        tripList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                position -= tripList.getHeaderViewsCount();
                clickPosition = position;
                String selectData = customAdapter.Time(position) + "     " + customAdapter.Place(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MyTripActivity.this);
                dialog.setTitle("일정 수정/삭제")
                        .setMessage("해당 일정을 수정 또는 삭제 하시겠습니까?" + "\n\n" + selectData)
                        .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //clickPosition = position;
                                String time = customAdapter.Time(clickPosition);
                                String place = customAdapter.Place(clickPosition);
                                String day = "";

                                try {
                                    db = dbHelper.getReadableDatabase();

                                    res = db.query(DBContract.TBL_MYTRIP,
                                            new String[] { DBContract.COL_DAY, DBContract.COL_TIME, DBContract.COL_PLACE},
                                            DBContract.COL_TIME + "=? AND "+ DBContract.COL_PLACE + "=?",
                                            new String[] { time,place }, null, null, null, null);

                                    if (res != null)
                                        res.moveToFirst();
                                    day = res.getString(res.getColumnIndex(DBContract.COL_DAY));

                                }catch(SQLException ex){
                                    Log.d("Tag","SQL Exception");
                                }

                                Intent intent = new Intent(MyTripActivity.this, MTripItem.class);
                                intent.putExtra("oldday",day);
                                intent.putExtra("oldtime",time);
                                intent.putExtra("oldsche",place);

                                startActivityForResult(intent, GET_STRING);

                            }
                        })
                        .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MyTripActivity.this, "일정을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                removeData(clickPosition);
                                //removeData(position);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });

        editTextTime.setOnClickListener(new View.OnClickListener() {
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

                            editTextTime.setText(h + ":" + m);
                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(MyTripActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
                timePickerDialog.setTitle("시간 선택");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        editTextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = { "Day1", "Day2", "Day3"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyTripActivity.this);

                alertDialogBuilder.setTitle("여행 일차 선택");
                alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        editTextDay.setText(items[id]);
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog2 = alertDialogBuilder.create();
                alertDialog2.show();

            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == GET_STRING){
            if(resultCode == RESULT_OK){
                String time = data.getStringExtra("TIME"); // 새로 입력한 시간
                String schedule = data.getStringExtra("SCHEDULE"); // 새로 입력한 일정
                String day = data.getStringExtra("DAY");
                update_values(day, time, schedule);
            }
        }
    }

    public void update_values(String newD, String newT, String newS){

        String oldD = customAdapter.Day(clickPosition);
        String oldT = customAdapter.Time(clickPosition);
        String oldS = customAdapter.Place(clickPosition);

        try {
            db = dbHelper.getWritableDatabase();

            String whereClause = DBContract.COL_TIME + "=? AND " + DBContract.COL_PLACE + "=?";
            String[] whereArgs = {oldT,oldS};
            ContentValues args = new ContentValues();
            args.put("DAY",newD);
            args.put("TIME", newT);
            args.put("PLACE", newS);
            db.update(DBContract.TBL_MYTRIP,args,whereClause,whereArgs);

            customAdapter.removeAll();
            load_values();

            Toast.makeText(MyTripActivity.this, "일정을 수정했습니다.", Toast.LENGTH_SHORT).show();

        }catch (SQLException ex){
            Log.d("Tag","SQL Update Exception");
        }


    }
    public void load_values() {

        db = dbHelper.getReadableDatabase();
        res = db.rawQuery(DBContract.SQL_SELECT + " ORDER BY "  + DBContract.COL_DAY + ", " + DBContract.COL_TIME, null);
        startManagingCursor(res);
        setList();
    }


    public void insert_values(View view) {

        try {
            db = dbHelper.getWritableDatabase();

            String day = editTextDay.getText().toString();
            String time = editTextTime.getText().toString();
            String schedule = editTextSche.getText().toString();

            if(!day.isEmpty() && !schedule.isEmpty()) {
                String sqlInsert = DBContract.SQL_INSERT + " ("  + "'" + day + "'," + "'" + time + "'," + "'" + schedule + "'" + ")";
                db.execSQL(sqlInsert);

                customAdapter.removeAll();
                load_values();

                //res.moveToNext();
            }

        }catch (SQLException ex){
            Log.d("Tag","SQL Insert Exception");
        }
        editTextDay.setText("Day1");
        editTextSche.setText("");
        editTextTime.setText("00:00");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextSche.getWindowToken(), 0);
    }

    public void deleteAll(View view){

        AlertDialog.Builder dialog = new AlertDialog.Builder(MyTripActivity.this);
        dialog.setMessage("일정을 모두 삭제 하시겠습니까?" )
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            db = dbHelper.getWritableDatabase();
                            db.execSQL(DBContract.SQL_DELETE);

                            customAdapter.removeAll();
                            customAdapter.notifyDataSetChanged();

                        }catch (SQLException ex){
                            Log.d("Tag","SQL Delete Exception");
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }

    public void removeData(int no){

        try{
            db = dbHelper.getWritableDatabase();

            String time = customAdapter.Time(no);
            String place = customAdapter.Place(no);

            String whereClause = DBContract.COL_TIME + "=? AND " + DBContract.COL_PLACE + "=?";
            String[] whereArgs = {time,place};
            db.delete(DBContract.TBL_MYTRIP, whereClause, whereArgs);

            customAdapter.removeAll();
            load_values();

        }catch (SQLException ex){
            Log.d("Tag","SQL Delete Exception");
        }

    }

    public void showDay1(View view){
        try {
            db = dbHelper.getReadableDatabase();

            res = db.query(DBContract.TBL_MYTRIP,
                    new String[] { DBContract.COL_DAY, DBContract.COL_TIME, DBContract.COL_PLACE},
                    DBContract.COL_DAY + "=?",
                    new String[] { "Day1" }, null, null, DBContract.COL_TIME, null);

            customAdapter.removeAll();
            setList();

        }catch(SQLException ex){
            Log.d("Tag","SQL Exception");
        }
    }

    public void showDay2(View view){
        try {
            db = dbHelper.getReadableDatabase();

            res = db.query(DBContract.TBL_MYTRIP,
                    new String[] { DBContract.COL_DAY, DBContract.COL_TIME, DBContract.COL_PLACE},
                    DBContract.COL_DAY + "=?",
                    new String[] { "Day2" }, null, null, DBContract.COL_TIME, null);

            customAdapter.removeAll();
            setList();

        }catch(SQLException ex){
            Log.d("Tag","SQL Exception");
        }
    }

    public void showDay3(View view){
        try {
            db = dbHelper.getReadableDatabase();

            res = db.query(DBContract.TBL_MYTRIP,
                    new String[] { DBContract.COL_DAY, DBContract.COL_TIME, DBContract.COL_PLACE},
                    DBContract.COL_DAY + "=?",
                    new String[] { "Day3" }, null, null, DBContract.COL_TIME, null);

            customAdapter.removeAll();
            setList();

        }catch(SQLException ex){
            Log.d("Tag","SQL Exception");
        }
    }

    public void showAll(View view){
        customAdapter.removeAll();
        load_values();
    }

    public void setList(){

        res.moveToFirst();
        while (!res.isAfterLast()){
            String day = res.getString(res.getColumnIndex(DBContract.COL_DAY));
            String time = res.getString(res.getColumnIndex(DBContract.COL_TIME));
            String schedule = res.getString(res.getColumnIndex(DBContract.COL_PLACE));

            Boolean flag = false;
            for(int i =0; i< customAdapter.getCount(); i++){
                if(customAdapter.Day(i).equals(day)) {
                    flag = true;
                    break;
                }
            }
            if(flag)
                customAdapter.addItem("",time,schedule);
            else
                customAdapter.addItem(day,time,schedule);

            res.moveToNext();
        }

        tripList.setAdapter(customAdapter);
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
