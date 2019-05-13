package com.jieun.recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyTripActivity extends AppCompatActivity{

    EditText editTextTime;
    EditText editTextSche;
    ListView tripList;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    ArrayList<String> DBarr =  new ArrayList<>();
    Cursor res;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrip_db);

        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("내 여행 일정") ;

        tripList = (ListView) findViewById(R.id.triplist);
        editTextTime = (EditText) findViewById(R.id.Time) ;
        editTextSche = (EditText) findViewById(R.id.Schedule) ;

        dbHelper = new DatabaseHelper(this);

        load_values();

        tripList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("Long Click", "position = " + position);
                String selectData = DBarr.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyTripActivity.this);
                dialog.setTitle("일정 삭제")
                        .setMessage("해당 일정을 삭제 하시겠습니까?" + "\n\n" + selectData)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MyTripActivity.this, "일정을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                removeData(position);
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MyTripActivity.this, "삭제를 취소 했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });

    }

    public void load_values() {

        db = dbHelper.getReadableDatabase();
        res = db.rawQuery(DBContract.SQL_SELECT, null);

        res.moveToFirst();

        while (!res.isAfterLast()){
            String time = res.getString(res.getColumnIndex(DBContract.COL_TIME));
            String schedule = res.getString(res.getColumnIndex(DBContract.COL_PLACE));
            DBarr.add(time + "         " + schedule);
            res.moveToNext();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DBarr);
        tripList.setAdapter(adapter);
    }


    public void insert_values(View view) {

        try {
            db = dbHelper.getWritableDatabase();

            String time = editTextTime.getText().toString();
            String schedule = editTextSche.getText().toString();

            if(!schedule.isEmpty()) {
                String sqlInsert = DBContract.SQL_INSERT + " (" + "'" + time + "'," + "'" + schedule + "'" + ")";
                db.execSQL(sqlInsert);

                DBarr.add(time + "         " + schedule);
                adapter.notifyDataSetChanged();
                res.moveToNext();
            }

        }catch (SQLException ex){
            Log.d("Tag","SQL Insert Exception");
        }

        editTextSche.setText("");
        editTextTime.setText("");
    }

    public void deleteAll(View view){

        try{
            db = dbHelper.getWritableDatabase();
            db.execSQL(DBContract.SQL_DELETE);

            adapter.clear();
            adapter.notifyDataSetChanged();

        }catch (SQLException ex){
            Log.d("Tag","SQL Delete Exception");
        }
    }

    public void removeData(int id){

        try{
            db = dbHelper.getWritableDatabase();
            //db.delete(DBContract.TBL_MYTRIP,DBContract._ID + "=" + id, null);

            String sql = DBContract.SQL_DELETE + " WHERE id = " + id;
            db.execSQL(sql);

            adapter.remove(DBarr.get(id)); //꼭 필요함
            adapter.notifyDataSetChanged();

        }catch (SQLException ex){
            Log.d("Tag","SQL Delete Exception");
        }

    }
}
