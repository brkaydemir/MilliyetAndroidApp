package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.Observable;
import java.util.Observer;

public class Anasayfa extends AppCompatActivity implements TextWatcher, Observer, IAnasayfa {

    private final String son_dakika_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungSonDakika";
    private final String hava_durumu_ana_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHavaDurumuAnasayfa";
    private final String piyasalar_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungPiyasalar";
    private final String yazarlar_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungYazar";
    private final String son_dakika_liste_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungSonDakikaListe";
    private final String astroloji_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungAstroloji";
    private final String hava_durumu_detay_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHavaDurumu";
    private final String manset_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=Samsung11liManset";
    public static boolean BURC_CREATED;
    public static boolean PARSE_END;
    public static TextView textView;

    public XmlPullParser parser;
    public ArSonDakika arSonDakika;
    public ArManset arManset;
    public final String TAG = "MilliyetApp";
    public int counter;
    public ArAstroloji arAstroloji;
    public ArHavaDurumu arHavaDurumu;
    public ArPiyasa arPiyasa;
    public MansetLayout mansetLayout;
    public SonDakikaLayout sonDakikaLayout;
    public BurcLayout burcLayout;
    public HavaDurumuLayout havaDurumuLayout1;
    public HavaDurumuLayout havaDurumuLayout2;
    public HavaDurumuLayout havaDurumuLayout3;
    public HavaDurumuLayout havaDurumuLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        BURC_CREATED = false;
        PARSE_END = false;

        arSonDakika = new ArSonDakika();
        arManset = new ArManset();
        arAstroloji = new ArAstroloji();
        arHavaDurumu = new ArHavaDurumu();
        arPiyasa = new ArPiyasa();


        //adding observer to observables
        arManset.addObserver(this);
        arSonDakika.addObserver(this);

        //layout hooking
        mansetLayout = (MansetLayout) findViewById(R.id.customLayout);
        sonDakikaLayout = (SonDakikaLayout) findViewById(R.id.sondakika_layout);
        textView = (TextView) findViewById(R.id.textview);
        burcLayout = (BurcLayout) findViewById(R.id.burc_layout);
        havaDurumuLayout1=(HavaDurumuLayout)findViewById(R.id.havadurumu_layout1);
        havaDurumuLayout2=(HavaDurumuLayout)findViewById(R.id.havadurumu_layout2);
        havaDurumuLayout3=(HavaDurumuLayout)findViewById(R.id.havadurumu_layout3);
        havaDurumuLayout4=(HavaDurumuLayout)findViewById(R.id.havadurumu_layout4);


        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/





        //customize layout items
        textView.addTextChangedListener(this);

        //timer threads
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arSonDakika.inc_index();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();


    }

    @Override
    protected void onStart()
    {
        super.onStart();


        counter = 0;

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            // fetch data
            new ArRequest().execute(manset_url);
        }
        else
        {
            ;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anasayfa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void parserInit() throws XmlPullParserException
    {
        XmlPullParserFactory XmlProcessor = XmlPullParserFactory.newInstance();
        XmlProcessor.setNamespaceAware(true);
        parser = XmlProcessor.newPullParser();
        parser.setInput(new StringReader(textView.getText().toString()));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        //PARSING XML Files
        // Create an object of XmlPullParserFactory
        try {

            parserInit();
            if(counter==0 && !PARSE_END)
            {
                arManset.parse(parser);
                new ArRequest().execute(son_dakika_url);
                counter++;
            }
            else if(counter==1 && !PARSE_END) {
                arSonDakika.parse(parser);
                new ArRequest().execute(astroloji_url);
                counter++;
            }
            else if(counter==2 && !PARSE_END)
            {
                arAstroloji.parse(parser);
                BURC_CREATED = true;
                burcLayout.spinner.setSelection(10);
                setBurc(10);
                new ArRequest().execute(hava_durumu_detay_url);
                counter++;
            }
            else if(counter==3 && !PARSE_END)
            {
                arHavaDurumu.parse(parser);
                setHavaDurumu();
                new ArRequest().execute(piyasalar_url);
                counter++;
            }
            else if(counter==4 && !PARSE_END)
            {
                arPiyasa.parse(parser);
                arManset.setIndex(0);
                counter++;
                textView.removeTextChangedListener(this);
                PARSE_END = true;
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object data) {

        if(observable instanceof ArManset)
            new ArImageRequest(mansetLayout.image_view).execute(arManset.item_list.get(arManset.show_index).big_image_url);
        if(observable instanceof ArSonDakika)
            sonDakikaLayout.button.setText(arSonDakika.item_list.get(arSonDakika.show_index).article_title_detay);

    }

    @Override
    public void inc_index_manset() {
        arManset.inc_index();
    }

    @Override
    public void dec_index_manset() {
        arManset.dec_index();
    }

    @Override
    public void setIndex_manset(int i) {
        arManset.setIndex(i);
    }

    @Override
    public void inc_index_sondakika() {
        arSonDakika.inc_index();
    }

    @Override
    public void dec_index_sondakika() {
        arSonDakika.dec_index();
    }

    @Override
    public void setIndex_sondakika(int i) {
        arSonDakika.setIndex(i);
    }

    @Override
    public void setBurc(int i) {
        burcLayout.burc_detay.setText(arAstroloji.item_list.get(i).burc_spot);
        if(i==0)
            burcLayout.picture.setImageResource(R.drawable.oglak);
        else if(i==1)
            burcLayout.picture.setImageResource(R.drawable.balik);
        else if(i==2)
            burcLayout.picture.setImageResource(R.drawable.kova);
        else if(i==3)
            burcLayout.picture.setImageResource(R.drawable.koc);
        else if(i==4)
            burcLayout.picture.setImageResource(R.drawable.yay);
        else if(i==5)
            burcLayout.picture.setImageResource(R.drawable.akrep);
        else if(i==6)
            burcLayout.picture.setImageResource(R.drawable.terazi);
        else if(i==7)
            burcLayout.picture.setImageResource(R.drawable.basak);
        else if(i==8)
            burcLayout.picture.setImageResource(R.drawable.aslan);
        else if(i==9)
            burcLayout.picture.setImageResource(R.drawable.yengec);
        else if(i==10)
            burcLayout.picture.setImageResource(R.drawable.ikizler);
        else if(i==11)
            burcLayout.picture.setImageResource(R.drawable.boga);


    }

    @Override
    public void goMansetArticle() {

        Intent intent = new Intent(this,ArticleActivity.class);
        intent.putExtra("article_url",arManset.item_list.get(arManset.show_index).article_id);

        startActivity(intent);

    }

    @Override
    public void goSonDakikaArticle() {

    }

    @Override
    public void goBurcArticle() {

    }

    public void setHavaDurumu()
    {
        for(int i = 0 ; i<arHavaDurumu.item_list.size();i++)
        {
            if(arHavaDurumu.item_list.get(i).location.equals("Ankara"))
            {
                havaDurumuLayout1.location.setText("Ankara");
                havaDurumuLayout1.status_text.setText(arHavaDurumu.item_list.get(i).status);
                havaDurumuLayout1.temperature.setText(arHavaDurumu.item_list.get(i).min_temp + " ve " + arHavaDurumu.item_list.get(i).max_temp + " derece arası");
            }
            else if(arHavaDurumu.item_list.get(i).location.equals("istanbul"))
            {
                havaDurumuLayout2.location.setText("İstanbul");
                havaDurumuLayout2.status_text.setText(arHavaDurumu.item_list.get(i).status);
                havaDurumuLayout2.temperature.setText(arHavaDurumu.item_list.get(i).min_temp + " ve " + arHavaDurumu.item_list.get(i).max_temp + " derece arası");
            }
            else if(arHavaDurumu.item_list.get(i).location.equals("izmir"))
            {
                havaDurumuLayout3.location.setText("İzmir");
                havaDurumuLayout3.status_text.setText(arHavaDurumu.item_list.get(i).status);
                havaDurumuLayout3.temperature.setText(arHavaDurumu.item_list.get(i).min_temp + " ve " + arHavaDurumu.item_list.get(i).max_temp + " derece arası");
            }
            else if(arHavaDurumu.item_list.get(i).location.equals("Bursa"))
            {
                havaDurumuLayout4.location.setText("Bursa");
                havaDurumuLayout4.status_text.setText(arHavaDurumu.item_list.get(i).status);
                havaDurumuLayout4.temperature.setText(arHavaDurumu.item_list.get(i).min_temp + " ve " + arHavaDurumu.item_list.get(i).max_temp + " derece arası");
            }
        }
    }
}

