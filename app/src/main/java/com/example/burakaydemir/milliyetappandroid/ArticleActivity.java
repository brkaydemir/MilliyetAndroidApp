package com.example.burakaydemir.milliyetappandroid;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.Observable;
import java.util.Observer;

public class ArticleActivity extends AppCompatActivity implements TextWatcher, Observer {

    public final String detay_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHaber&ArticleID=";

    public ArArticleDetay article;
    public XmlPullParser parser;
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

        article=new ArArticleDetay();

        Anasayfa.textView.addTextChangedListener(this);


        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("article_url")!= null)
        {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected())
            {
                // fetch data
                new ArRequest().execute(detay_url+bundle.getString("article_url"));
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
            article.parse(parser);
            Anasayfa.textView.removeTextChangedListener(this);
            setContent();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    public void setContent() {


        article_title.setText(article.ArticleTitleDetay);
        article_spot.setText(article.ArticleSpotDetay);
        new ArImageRequest(article_image).execute(article.image_url);
        article_text.setText(article.ArticleText);
    }
}
