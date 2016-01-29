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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class YazarlarActivity extends AppCompatActivity implements Observer,ArResponse {

    public ArYazar arYazar;

    public TextView kategoriText;
    public TableLayout table;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        table = (TableLayout) findViewById(R.id.tableLayout);
        kategoriText = (TextView) findViewById(R.id.kategoriText);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            // fetch data
            ArRequest request = new ArRequest(3,0);
            request.responseHandler=this;
            request.execute();
        }
    }

    @Override
    public void finishTask(String str, int type, int id) {
        arYazar = new ArYazar(Integer.toString(id));
        arYazar.addObserver(this);
        arYazar.parse(str);
    }

    @Override
    public void update(Observable observable, Object data) {
        setContent();
    }

    public void setContent()
    {
        kategoriText.setText("Yazarlar");
        Button tempButton;
        TableRow tempRow1;
        TableRow tempRow2;
        TextView tempText1;
        TextView tempText2;
        String tempId;


        for(int i = 0; i<arYazar.item_list.size()-1;i++)
        {

            tempButton = new Button(this);
            tempRow1 = new TableRow(this);
            tempRow2 = new TableRow(this);
            tempText1 = new TextView(this);
            tempText2 = new TextView(this);

            tempId = arYazar.item_list.get(i).elements.get(ArYazar.ARTICLE_ID);


            tempButton.setText(arYazar.item_list.get(i).elements.get(ArYazar.ARTICLE_TITLE_DETAIL));
            tempButton.setGravity(Gravity.LEFT);
            tempButton.setBackgroundColor(Color.TRANSPARENT);

            tempText1.setText(arYazar.item_list.get(i).elements.get(ArYazar.AUTHOR_NAME) + " " + arYazar.item_list.get(i).elements.get(ArYazar.AUTHOR_SURNAME));
            tempText2.setText(arYazar.item_list.get(i).elements.get(ArYazar.CORNER_NAME));

            tempText2.setGravity(Gravity.RIGHT);

            //Log.d("yazarlarActivity", "setContent: " + tempText2.getText().toString());

            final String finalTempId = tempId;
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                    intent.putExtra("article_url", finalTempId);
                    startActivity(intent);
                }
            });



            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT);
            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT);
            tempRow1.setLayoutParams(lp1);
            tempRow1.addView(tempText1);
            tempRow1.addView(tempText2);
            tempRow2.setLayoutParams(lp2);
            tempRow2.addView(tempButton);
            table.addView(tempRow1);
            table.addView(tempRow2);
        }
    }

}
