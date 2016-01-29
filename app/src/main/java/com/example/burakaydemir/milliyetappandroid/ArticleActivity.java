package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class ArticleActivity extends AppCompatActivity implements ArResponse, Observer {

    public final String detay_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHaber&ArticleID=";
    public final String TAG = "articleActivity";

    public ArArticleDetay article;
    public TextView article_title;
    public TextView article_spot;
    public ImageView article_image;
    public TextView article_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        article_title = (TextView) findViewById(R.id.textView7);
        article_spot = (TextView) findViewById(R.id.textView8);
        article_image = (ImageView) findViewById(R.id.imageView5);
        article_text = (TextView) findViewById(R.id.textView9);




        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("article_url")!= null)
        {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected())
            {
                // fetch data
                ArRequest request = new ArRequest(2,Integer.parseInt(bundle.getString("article_url")));
                request.responseHandler = this;
                request.execute();

                //Log.d(TAG, "onCreate: request gönderildi ");
            }
        }

    }

    @Override
    public void update(Observable observable, Object data) {
        setContent();
        //Log.d(TAG, "update: ");
    }

    public void setContent() {

        article_title.setText(article.content.get(ArArticleDetay.ARTICLE_TITLE_DETAIL));
        article_spot.setText(article.content.get(ArArticleDetay.ARTICLE_SPOT_DETAIL));
        new ArImageRequest(article_image).execute(article.content.get(ArArticleDetay.IMAGE_URL));
        article_text.setText(article.content.get(ArArticleDetay.ARTICLE_TEXT));
    }

    @Override
    public void finishTask(String str, int type, int id) {
        //Log.d(TAG, "finishTask: cevap geldi");
        article = new ArArticleDetay();
        article.addObserver(this);
        article.parse(str);
        //Log.d(TAG, "finishTask: parse bitti");
    }
}
