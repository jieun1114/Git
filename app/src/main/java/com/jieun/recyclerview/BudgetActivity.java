package com.jieun.recyclerview;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity{

    EditText editSche;
    EditText editPrice;
    EditText editBudget;
    ListView budgetList;
    TextView sumExpense;
    static final int GET_STRING = 1;

    DatabaseHelper2 dbHelper;
    SQLiteDatabase db;

    Cursor res;
    CustomAdapter2 customAdapter;
    int clickPosition = -1;
    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_db);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("여행 경비");

        budgetList = (ListView) findViewById(R.id.budgetlist);
        editPrice = (EditText) findViewById(R.id.Price);
        editSche = (EditText) findViewById(R.id.Schedule2);
        editBudget = (EditText) findViewById(R.id.editBudget);
        sumExpense = (TextView) findViewById(R.id.textView17);
        sumExpense.setText("0");

        customAdapter = new CustomAdapter2();

        View header = getLayoutInflater().inflate(R.layout.budgetdb_header, null, false) ;
        header.setEnabled(false);
        header.setOnClickListener(null);
        budgetList.addHeaderView(header);

        dbHelper = new DatabaseHelper2(this);

        load_values();

        SharedPreferences sf = getSharedPreferences("BUDGET", 0);
        String str = sf.getString("bud", "");
        String str2 = sf.getString("expense","0");
        Log.v("tag",str2);
        editBudget.setText(str);
        sumExpense.setText(str2);

        SharedPreferences shared = getSharedPreferences("TitlePrice",0);
        String title = shared.getString("title","null");
        String price = shared.getString("price","null");

        if(!title.equals("null") && !price.equals("0")){
            try {
                db = dbHelper.getWritableDatabase();

                String sqlInsert = DBContract2.SQL_INSERT + " ("  + "'" + title + "'," + "'" + price  + "'" + ")";
                db.execSQL(sqlInsert);

                customAdapter.addItem(title,price);
                customAdapter.notifyDataSetChanged();
                res.moveToNext();

                int budget = Integer.parseInt(editBudget.getText().toString().replaceAll("[^0-9]",""));
                int result = budget - Integer.parseInt(price.replaceAll("[^0-9]",""));
                editBudget.setText((String)decimalFormat.format(result));
                String sum = sumExpense.getText().toString();

                if(!sum.isEmpty() && sum != null) {
                    sumExpense.setText((String) decimalFormat.format(Integer.parseInt(sum.replaceAll("[^0-9]", ""))
                            + Integer.parseInt(price.replaceAll("[^0-9]", ""))));
                    Log.v("Tag", sum);
                }
            }catch (SQLException ex){
                Log.d("Tag","SQL Insert Exception");
            }
        }

        SharedPreferences.Editor editor = shared.edit();
        editor.clear().commit();


        budgetList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                position -= budgetList.getHeaderViewsCount();
                clickPosition = position;
                String selectData = customAdapter.Place(position) + "     " + customAdapter.Price(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(BudgetActivity.this);
                dialog.setTitle("경비 수정/삭제")
                        .setMessage("해당 경비를 수정 또는 삭제 하시겠습니까?" + "\n\n" + selectData)
                        .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(BudgetActivity.this, BudgetItem.class);
                                intent.putExtra("oldprice",customAdapter.Price(clickPosition));
                                intent.putExtra("oldsche2",customAdapter.Place(clickPosition));
                                startActivityForResult(intent, GET_STRING);
                            }
                        })
                        .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(BudgetActivity.this, "경비를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                removeData(clickPosition);

                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });

    }

    protected void onStop(){
        super.onStop();

        SharedPreferences budget = getSharedPreferences("BUDGET",MODE_PRIVATE);
        SharedPreferences.Editor editor = budget.edit();
        String data = editBudget.getText().toString();
        String data2 = sumExpense.getText().toString();
        editor.putString("expense",data2);
        editor.putString("bud", data);
        editor.commit();
    }

    public void load_values() {

        db = dbHelper.getReadableDatabase();
        res = db.rawQuery(DBContract2.SQL_SELECT , null);
        startManagingCursor(res);

        res.moveToFirst();

        while (!res.isAfterLast()){
            String schedule = res.getString(res.getColumnIndex(DBContract2.COL_PLACE));
            String price = res.getString(res.getColumnIndex(DBContract2.COL_PRICE));
            customAdapter.addItem(schedule,price);
            res.moveToNext();
        }

        budgetList.setAdapter(customAdapter);
    }

    public void insert(View view) {

        try {
            db = dbHelper.getWritableDatabase();

            int value = 0;
            String price = "";

            String temp = editPrice.getText().toString().replaceAll("[^0-9]","");
            if(!temp.isEmpty()) {
                value = Integer.parseInt(temp);
                price = (String) decimalFormat.format(value);
            }
            String schedule = editSche.getText().toString();

            if(!schedule.isEmpty() && !temp.isEmpty()) {
                String sqlInsert = DBContract2.SQL_INSERT + " ("  + "'" + schedule + "'," + "'" + price  + "'" + ")";
                db.execSQL(sqlInsert);

                customAdapter.addItem(schedule,price);
                customAdapter.notifyDataSetChanged();
                res.moveToNext();

                int budget = Integer.parseInt(editBudget.getText().toString().replaceAll("[^0-9]",""));
                int result = budget - value;
                editBudget.setText((String)decimalFormat.format(result));
                sumExpense.setText((String)decimalFormat.format(Integer.parseInt(sumExpense.getText().toString().replaceAll("[^0-9]","")) + value));
            }

        }catch (SQLException ex){
            Log.d("Tag","SQL Insert Exception");
        }

        editPrice.setText("");
        editSche.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editSche.getWindowToken(), 0);



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == GET_STRING){
            if(resultCode == RESULT_OK){
                String price = data.getStringExtra("PRICE"); // 새로 입력한 지출
                String schedule = data.getStringExtra("SCHE2"); // 새로 입력한 일정
                update_values(schedule, price);
            }
        }
    }
    public void update_values(String newS, String newP){

        String oldP = customAdapter.Price(clickPosition);
        String oldS = customAdapter.Place(clickPosition);

        try {
            db = dbHelper.getWritableDatabase();

            String whereClause = DBContract2.COL_PLACE + "=? AND " + DBContract2.COL_PRICE + "=?";
            String[] whereArgs = {oldS, oldP};
            ContentValues args = new ContentValues();
            args.put("PRICE", newP);
            args.put("PLACE", newS);
            db.update(DBContract2.TBL_BUDGET,args,whereClause,whereArgs);

            customAdapter.removeAll();
            load_values();

            Toast.makeText(BudgetActivity.this, "지출을 수정했습니다.", Toast.LENGTH_SHORT).show();

            int budget = Integer.parseInt(editBudget.getText().toString().replaceAll("[^0-9]",""));
            budget += Integer.parseInt(oldP.replaceAll("[^0-9]",""));
            int result = budget - Integer.parseInt(newP.replaceAll("[^0-9]",""));
            editBudget.setText((String)decimalFormat.format(result));
            sumExpense.setText((String)decimalFormat.format(Integer.parseInt(sumExpense.getText().toString().replaceAll("[^0-9]",""))
                    - Integer.parseInt(oldP.replaceAll("[^0-9]",""))
                    + Integer.parseInt(newP.replaceAll("[^0-9]",""))));

        }catch (SQLException ex){
            Log.d("Tag","SQL Update Exception");
        }


    }

    public void delete(View view){

        AlertDialog.Builder dialog = new AlertDialog.Builder(BudgetActivity.this);
        dialog.setMessage("경비를 모두 삭제 하시겠습니까?" )
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            db = dbHelper.getWritableDatabase();
                            db.execSQL(DBContract2.SQL_DELETE);

                            int sum = 0;
                            for(int i = 0; i < customAdapter.getCount(); i++){
                                sum += Integer.parseInt(customAdapter.Price(i).replaceAll("[^0-9]",""));
                            }

                            customAdapter.removeAll();
                            customAdapter.notifyDataSetChanged();

                            int budget = Integer.parseInt(editBudget.getText().toString().replaceAll("[^0-9]",""));
                            budget += sum;
                            editBudget.setText((String)decimalFormat.format(budget));
                            sumExpense.setText("0");

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
            String price = customAdapter.Price(no);
            String place = customAdapter.Place(no);

            String whereClause = DBContract2.COL_PLACE + "=? AND " + DBContract2.COL_PRICE + "=?";
            String[] whereArgs = {place,price};
            db.delete(DBContract2.TBL_BUDGET, whereClause, whereArgs);

            customAdapter.removeItem(no);
            customAdapter.notifyDataSetChanged();

            int p = Integer.parseInt(price.replaceAll("[^0-9]",""));
            int budget = Integer.parseInt(editBudget.getText().toString().replaceAll("[^0-9]",""));
            budget += p;
            editBudget.setText((String)decimalFormat.format(budget));
            sumExpense.setText((String)decimalFormat.format(Integer.parseInt(sumExpense.getText().toString().replaceAll("[^0-9]","")) - p));

        }catch (SQLException ex){
            Log.d("Tag","SQL Delete Exception");
        }

    }

    public void Okbtn(View view){
        int budget = Integer.parseInt(editBudget.getText().toString().replaceAll("[^0-9]",""));
        editBudget.setText((String)decimalFormat.format(budget));
        //editBudget.setCursorVisible(false);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editBudget.getWindowToken(), 0);

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

