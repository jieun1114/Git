package com.jieun.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class BudgetItem extends AppCompatActivity {

    EditText price;
    EditText sche2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifybudget);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("경비 수정");

        price = (EditText) findViewById(R.id.editPrice2);
        sche2 = (EditText) findViewById(R.id.editSche2);

        Intent intent1 = getIntent();
        price.setText(intent1.getStringExtra("oldprice"));
        sche2.setText(intent1.getStringExtra("oldsche2"));

        Button mbtn = (Button) findViewById(R.id.okbtn);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                String inputPrice = price.getText().toString().replaceAll("[^0-9]",""); //1,000 이든 1000이든 숫자만 받아오기
                int inV = Integer.parseInt(inputPrice);
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String resultP = (String)decimalFormat.format(inV);

                String inputSche = sche2.getText().toString();

                if (!inputPrice.isEmpty() && !inputSche.isEmpty()) {
                    intent.putExtra("PRICE", resultP);
                    intent.putExtra("SCHE2", inputSche);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    public void cancel2(View v){
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