package com.jieun.recyclerview;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        recyclerView.setAdapter(adapter);
        adapter.setItems(new Data().getItems());
    }
}
