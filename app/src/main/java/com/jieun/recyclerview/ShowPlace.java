package com.jieun.recyclerview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ShowPlace extends AppCompatActivity {


    private String htmlPageUrl; //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private TextView textviewHtmlDocument2;
    private String htmlContentInStringFormat="";
    private String htmlContentInStringFormat2="";
    String imgUrl;
    ImageView image;

    int cnt=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_item);

        ActionBar ab = getSupportActionBar();

        Intent intent = getIntent();
        final String place = intent.getStringExtra("TITLE");
        final String fee = intent.getStringExtra("PRICE");
        imgUrl = intent.getStringExtra("URL");
        String pageNO = intent.getStringExtra("PAGENO");

        ab.setTitle(place);

        image = (ImageView) findViewById(R.id.img1);

        htmlPageUrl = "http://korean.visitseoul.net/attractions/" + place + "_/" + pageNO + "?curPage=1";
        textviewHtmlDocument = (TextView)findViewById(R.id.loadText);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());

        textviewHtmlDocument2 = (TextView)findViewById(R.id.loadText2);
        textviewHtmlDocument2.setMovementMethod(new ScrollingMovementMethod());

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();


        Glide.with(this)
                .load(imgUrl)
                .error(R.mipmap.ic_launcher)
                .into(image);


        final Button addTrip = (Button) findViewById(R.id.addTrip);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ShowPlace.this, MyTripActivity.class);
                intent1.putExtra("place", place);
                startActivity(intent1);

                SharedPreferences price = getSharedPreferences("TitlePrice",MODE_PRIVATE);
                SharedPreferences.Editor editor = price.edit();
                editor.putString("title",place);
                editor.putString("price",fee);
                editor.commit();

            }
        });
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

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();

                Elements titles= doc.select("div.detail-cont");
                System.out.println("-------------------------------------------------------------");
                for (Element e : titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }

                Elements tags = doc.select("div.sub-detail-contents .tag-cont-wrap .tag-lst a");
                for (Element e : tags) {
                    System.out.println("tag: " + e.text());
                    htmlContentInStringFormat2 += e.text().trim() + " ";
                }

                //Elements image = doc.select("div.dx-viewport");

            }catch (IOException ex){
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            textviewHtmlDocument.setText(htmlContentInStringFormat);
            textviewHtmlDocument2.setText(htmlContentInStringFormat2);
        }

    }



}
