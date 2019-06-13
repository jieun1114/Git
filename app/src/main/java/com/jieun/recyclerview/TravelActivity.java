package com.jieun.recyclerview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("관광지 둘러보기") ;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,linearLayoutManager.getOrientation()));
        recyclerView.setLayoutManager(linearLayoutManager);

        //List<RecyclerItem> list = new ArrayList<>();
        //adapter = new RecyclerAdapter(this,list);
        RecyclerAdapter adapter = new RecyclerAdapter();

        Intent intent = getIntent();
        int value = intent.getIntExtra("Theme",0);
        if(value != 0){
            switch (value){
                case 1:
                    adapter.setItems(new Data().getItems1());
                    break;
                case 2:
                    adapter.setItems(new Data().getItems2());
                    break;
                case 3:
                    adapter.setItems(new Data().getItems3());
                    break;
                case 4:
                    adapter.setItems(new Data().getItems4());
                    break;
            }
        }
        recyclerView.setAdapter(adapter);

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
