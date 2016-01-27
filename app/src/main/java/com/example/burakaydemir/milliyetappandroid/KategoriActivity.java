package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.security.PublicKey;
import java.util.Observable;
import java.util.Observer;

public class KategoriActivity extends AppCompatActivity implements TextWatcher,Observer {

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

        Anasayfa.textView.addTextChangedListener(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("kategori_type")!= null)
        {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected())
            {
                // fetch data
                arKategori = new ArKategori(bundle.getString("kategori_type"));
                new ArRequest().execute(kategori_url+bundle.getString("kategori_type"));
            }
        }
    }

    void parserInit() throws XmlPullParserException
    {
        XmlPullParserFactory XmlProcessor = XmlPullParserFactory.newInstance();
        XmlProcessor.setNamespaceAware(true);
        parser = XmlProcessor.newPullParser();
        parser.setInput(new StringReader(Anasayfa.textView.getText().toString()));
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        try
        {
            parserInit();
            arKategori.parse(parser);
            Anasayfa.textView.removeTextChangedListener(this);
            setContent();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }


    public void setContent()
    {
        kategoriText.setText(arKategori.item_list.get(0).main_category_name);
        Button tempButton;
        TableRow tempRow;
        String tempId;


        for(int i = 0; i<arKategori.item_list.size();i++)
        {

            tempButton = new Button(this);
            tempRow = new TableRow(this);
            tempId = arKategori.item_list.get(i).article_id;


            tempButton.setText(arKategori.item_list.get(i).article_title_detay);
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
}
