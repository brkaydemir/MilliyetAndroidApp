package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.Observable;
import java.util.Observer;

public class KategoriActivity extends AppCompatActivity implements ArResponse,Observer {

    public ArKategori arKategori;
    public final String kategori_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungKategoriListe&CategoryID=";

    public XmlPullParser parser;
    public TableLayout table;
    public TextView kategoriText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        table = (TableLayout) findViewById(R.id.tableLayout);
        kategoriText = (TextView) findViewById(R.id.kategoriText);


        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("kategori_type")!= null)
        {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected())
            {
                // fetch data
                ArRequest request = new ArRequest(1,Integer.parseInt(bundle.getString("kategori_type")));
                request.responseHandler=this;
                request.execute();
            }
        }
    }


    public void setContent()
    {
        kategoriText.setText(arKategori.item_list.get(0).elements.get(ArKategori.MAIN_CATEGORY_NAME));
        Button tempButton;
        TableRow tempRow;
        String tempId;


        for(int i = 0; i<arKategori.item_list.size()-1;i++)
        {

            tempButton = new Button(this);
            tempRow = new TableRow(this);
            tempId = arKategori.item_list.get(i).elements.get(ArKategori.ARTICLE_ID);


            tempButton.setText(arKategori.item_list.get(i).elements.get(ArKategori.ARTICLE_TITLE_DETAIL));
            tempButton.setGravity(Gravity.LEFT);
            tempButton.setBackgroundColor(Color.TRANSPARENT);

            final String finalTempId = tempId;
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                    intent.putExtra("article_url", finalTempId);
                    startActivity(intent);
                }
            });

            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.FILL_PARENT);
            tempRow.setLayoutParams(lp);
            tempRow.addView(tempButton);
            table.addView(tempRow);
        }
    }

    @Override
    public void finishTask(String str, int type, int id) {
        arKategori = new ArKategori(Integer.toString(id));
        arKategori.addObserver(this);
        arKategori.parse(str);
    }

    @Override
    public void update(Observable observable, Object data) {
        setContent();
    }
}
