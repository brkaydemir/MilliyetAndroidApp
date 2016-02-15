package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.Test;

import net.simonvt.menudrawer.MenuDrawer;
import java.util.Observable;
import java.util.Observer;

public class Anasayfa extends AppCompatActivity implements Observer, IAnasayfa, ArResponse {

    public static TextView textView;

    public static boolean BURC_CREATED;
    public static boolean PARSE_END;
    public static boolean MANSET;
    public static boolean SONDAKIKA;
    public static boolean WEATHER;
    public static boolean STOCK;
    public static boolean APPLICATION_STARTED = false;

    public MenuDrawer mDrawer;
    public ArSonDakika arSonDakika;
    public ArManset arManset;
    public final String TAG = "MilliyetApp";
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
    public ScrollTextView scrolltext;
    public ListView kategoriList;
    public Button menuButton;
    public Thread t;
    public FragmentTransaction tempTrans;
    public LoadingFragment tempFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);



        mDrawer = MenuDrawer.attach(this);
        mDrawer.setMenuView(R.layout.menu_drawer);
        mDrawer.setContentView(R.layout.activity_anasayfa);


        BURC_CREATED = false;
        PARSE_END = false;



        //layout hooking
        mansetLayout = (MansetLayout) findViewById(R.id.customLayout);
        sonDakikaLayout = (SonDakikaLayout) findViewById(R.id.sondakika_layout);
        burcLayout = (BurcLayout) findViewById(R.id.burc_layout);
        havaDurumuLayout1=(HavaDurumuLayout)findViewById(R.id.havadurumu_layout1);
        havaDurumuLayout2=(HavaDurumuLayout)findViewById(R.id.havadurumu_layout2);
        havaDurumuLayout3=(HavaDurumuLayout)findViewById(R.id.havadurumu_layout3);
        havaDurumuLayout4=(HavaDurumuLayout)findViewById(R.id.havadurumu_layout4);
        //piyasalarContent = (TextView) findViewById(R.id.textView6);

        scrolltext = (ScrollTextView) findViewById(R.id.scrolltext);

        kategoriList = (ListView) findViewById(R.id.listView2);

        menuButton = (Button) findViewById(R.id.button13);


        Resources res= getResources();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.kategori_list_layout, res.getStringArray(R.array.kategoriler));

        kategoriList.setAdapter(adapter);

        kategoriList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    ;
                }
                else if(position>0 && position<9)
                {
                    goKategori(position);
                }
                else
                    goYazar();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openMenu();
            }
        });

        //timer threads

        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        if(APPLICATION_STARTED)
                            Thread.sleep(5000);
                        else
                            Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arSonDakika.inc_index();
                                if(MANSET && SONDAKIKA &&BURC_CREATED && WEATHER && STOCK){
                                    FrameLayout tempLayout = (FrameLayout)findViewById(R.id.container);
                                    tempLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        t.start();

        Bundle tempBundle = getIntent().getExtras();

        if(savedInstanceState == null) {

            if (!APPLICATION_STARTED) {
                tempTrans = getSupportFragmentManager().beginTransaction();
                tempFrag = new LoadingFragment();
                tempTrans.add(R.id.container, tempFrag).show(tempFrag).commit();
                APPLICATION_STARTED = true;
            }
            else {
                FrameLayout tempFrame = (FrameLayout) findViewById(R.id.container);
                tempFrame.setVisibility(View.GONE);
            }
        }
        else
        {
            FrameLayout tempFrame = (FrameLayout) findViewById(R.id.container);
            tempFrame.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        t.interrupt();
        arManset = null;
        arPiyasa = null;
        arHavaDurumu = null;
        arSonDakika = null;
        arAstroloji = null;
        APPLICATION_STARTED = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        APPLICATION_STARTED = true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();



        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            // download data
            if(arManset == null)
            {
                ArRequest request1 = new ArRequest(0,1);
                request1.responseHandler = this;
                request1.execute();
            }

            if(arSonDakika == null)
            {
                ArRequest request2 = new ArRequest(0,2);
                request2.responseHandler = this;
                request2.execute();
            }

            if(arAstroloji == null)
            {
                ArRequest request3 = new ArRequest(0,3);
                request3.responseHandler = this;
                request3.execute();

            }

            if(arHavaDurumu == null)
            {
                ArRequest request4 = new ArRequest(0,4);
                request4.responseHandler = this;
                request4.execute();
            }

            if(arPiyasa==null)
            {
                ArRequest request5 = new ArRequest(0,5);
                request5.responseHandler = this;
                request5.execute();
            }
        }
    }


    private void setPiyasalar() {

        String temp="";
        String spacer="    ";

        for(int i = 0; i<arPiyasa.item_list.size()-1;i++)
        {
            if(arPiyasa.item_list.get(i).elements.get(ArPiyasa.PIYASA_NAME).equals("BIST"))
            {
                temp = temp + "BIST: ";
                if(arPiyasa.item_list.get(i).elements.get(ArPiyasa.STATUS).equals("1"))
                {
                    temp = temp + "Borsa bugün %" + arPiyasa.item_list.get(i).elements.get(ArPiyasa.PERCENT) + " değer kazandı ve "
                            + arPiyasa.item_list.get(i).elements.get(ArPiyasa.VALUE) + " puana yükseldi.";
                }
                else
                    temp = temp + "Borsa kan kaybetmeye devam ediyor %" + arPiyasa.item_list.get(i).elements.get(ArPiyasa.PERCENT) + " düşüşle  "
                            + arPiyasa.item_list.get(i).elements.get(ArPiyasa.VALUE) + " puandan kapandı.";
            }
            else if(arPiyasa.item_list.get(i).elements.get(ArPiyasa.PIYASA_NAME).equals("USD") )
            {
                temp = temp + "USD:";
                if(arPiyasa.item_list.get(i).elements.get(ArPiyasa.STATUS).equals("1"))
                {
                    temp = temp + "Dolar bugün can yakmaya devam ediyor ve %" + arPiyasa.item_list.get(i).elements.get(ArPiyasa.PERCENT) + " değer kazanarak  "
                            + arPiyasa.item_list.get(i).elements.get(ArPiyasa.VALUE) + " liradan işlem görüyor.";
                }
                else
                    temp = temp + "Dolar yüreklere su serpti. %" + arPiyasa.item_list.get(i).elements.get(ArPiyasa.PERCENT) + " düşüşle  "
                            + arPiyasa.item_list.get(i).elements.get(ArPiyasa.VALUE) + " liradan kapandı.";
            }
            else if(arPiyasa.item_list.get(i).elements.get(ArPiyasa.PIYASA_NAME).equals("EURO") )
            {
                temp = temp + "EURO:";
                if(arPiyasa.item_list.get(i).elements.get(ArPiyasa.STATUS).equals("1"))
                {
                    temp = temp + "Merkel bombaladı Avro coştu ve %" + arPiyasa.item_list.get(i).elements.get(ArPiyasa.PERCENT) + " değer kazanarak  "
                            + arPiyasa.item_list.get(i).elements.get(ArPiyasa.VALUE) + " lira ile rekorlar kırdı.";
                }
                else
                    temp = temp + "Avro dolar karşısındaki savaşında yara aldı ve %" + arPiyasa.item_list.get(i).elements.get(ArPiyasa.PERCENT) + " düşüşle  "
                            + arPiyasa.item_list.get(i).elements.get(ArPiyasa.VALUE) + " liradan kapandı.";
            }
            else if(arPiyasa.item_list.get(i).elements.get(ArPiyasa.PIYASA_NAME).equals("ALTIN") )
            {
                temp = temp + "ALTIN:";
                if(arPiyasa.item_list.get(i).elements.get(ArPiyasa.STATUS).equals("1"))
                {
                    temp = temp + "Yastıkaltıcılar yaşadı altın %" + arPiyasa.item_list.get(i).elements.get(ArPiyasa.PERCENT) + " değer kazandı  "
                            + arPiyasa.item_list.get(i).elements.get(ArPiyasa.VALUE) + " lira.";
                }
                else
                    temp = temp + "Sarı lira yatırımcıları endişeli %" + arPiyasa.item_list.get(i).elements.get(ArPiyasa.PERCENT) + " düşerek  "
                            + arPiyasa.item_list.get(i).elements.get(ArPiyasa.VALUE) + " liradan satıldı.";
            }
            temp = temp + spacer;
        }
        scrolltext
                .setText(temp);
        scrolltext.setTextColor(Color.BLACK);
        scrolltext.startScroll();
        //piyasalarContent.setText(temp);
    }

    @Override
    public void update(Observable observable, Object data) {


        if(observable instanceof ArManset){


            ArImageRequest request = new ArImageRequest(mansetLayout.image_view);
            request.responseHandler = this;
            request.execute(arManset.item_list.get(arManset.show_index).elements.get(ArManset.BIG_IMAGE_URL));
            mansetLayout.image_view.setScaleType(ImageView.ScaleType.FIT_XY);

        }
        else if(observable instanceof ArSonDakika)
            sonDakikaLayout.button.setText(arSonDakika.item_list.get(arSonDakika.show_index).elements.get(ArSonDakika.ARTICLE_TITLE_DETAIL));
        else if(observable instanceof ArHavaDurumu)
            setHavaDurumu();
        else if(observable instanceof ArPiyasa)
            setPiyasalar();

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
        burcLayout.burc_detay.setText(arAstroloji.item_list.get(i).elements.get(ArAstroloji.BURC_SPOT));
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
        intent.putExtra("article_url",arManset.item_list.get(arManset.show_index).elements.get(ArManset.ARTICLE_ID));

        startActivity(intent);

    }


    public void goKategori(int id)
    {
        Intent intent = new Intent(this,KategoriActivity.class);
        intent.putExtra("kategori_type",Integer.toString(id));

        startActivity(intent);
    }

    public void goYazar() {

        Intent intent = new Intent(this,YazarlarActivity.class);
        startActivity(intent);
    }

    @Override
    public void goSonDakikaArticle() {
        Intent intent = new Intent(this,ArticleActivity.class);
        intent.putExtra("article_url", arSonDakika.item_list.get(arSonDakika.show_index).elements.get(ArSonDakika.ARTICLE_ID));
        startActivity(intent);
    }

    @Override
    public void goBurcArticle() {

    }

    public void setHavaDurumu()
    {
        for(int i = 0 ; i<arHavaDurumu.item_list.size()-1;i++)
        {
            if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.LOCATION).equals("Ankara"))
            {
                havaDurumuLayout1.location.setText("Ankara");
                havaDurumuLayout1.status_text.setText(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS));
                if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("kar"))
                {
                    havaDurumuLayout1.image.setImageResource(R.drawable.snow);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağmur") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("sağanak")
                        || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Yağmur") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Sağanak"))
                {
                    havaDurumuLayout1.image.setImageResource(R.drawable.rain);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("bulut"))
                {
                    havaDurumuLayout1.image.setImageResource(R.drawable.cloud);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("güneş") ||arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Güneş"))
                {
                    havaDurumuLayout1.image.setImageResource(R.drawable.sun);
                }
                else
                {
                    havaDurumuLayout1.image.setImageResource(R.drawable.cloud);
                }
                havaDurumuLayout1.temperature.setText(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.MIN_TEMPRATURE) + " ve " + arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.MAX_TEMPRATURE) + " derece arası");
            }
            else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.LOCATION).equals("istanbul"))
            {
                havaDurumuLayout2.location.setText("İstanbul");
                havaDurumuLayout2.status_text.setText(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS));
                if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("kar"))
                {
                    havaDurumuLayout2.image.setImageResource(R.drawable.snow);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağmur") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("sağanak")
                        || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Yağmur") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Sağanak"))
                {
                    havaDurumuLayout2.image.setImageResource(R.drawable.rain);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("bulut"))
                {
                    havaDurumuLayout2.image.setImageResource(R.drawable.cloud);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("güneş") ||arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Güneş"))
                {
                    havaDurumuLayout2.image.setImageResource(R.drawable.sun);
                }
                else
                {
                    havaDurumuLayout2.image.setImageResource(R.drawable.cloud);
                }

                havaDurumuLayout2.temperature.setText(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.MIN_TEMPRATURE) + " ve " + arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.MAX_TEMPRATURE) + " derece arası");
            }
            else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.LOCATION).equals("izmir"))
            {
                havaDurumuLayout3.location.setText("İzmir");
                havaDurumuLayout3.status_text.setText(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS));
                if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("kar"))
                {
                    havaDurumuLayout3.image.setImageResource(R.drawable.snow);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağmur") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("sağanak")
                        || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Yağmur") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Sağanak"))
                {
                    havaDurumuLayout3.image.setImageResource(R.drawable.rain);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("bulut"))
                {
                    havaDurumuLayout3.image.setImageResource(R.drawable.cloud);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("güneş") ||arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Güneş"))
                {
                    havaDurumuLayout3.image.setImageResource(R.drawable.sun);
                }
                else
                {
                    havaDurumuLayout3.image.setImageResource(R.drawable.cloud);
                }
                havaDurumuLayout3.temperature.setText(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.MIN_TEMPRATURE) + " ve " + arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.MAX_TEMPRATURE) + " derece arası");
            }
            else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.LOCATION).equals("Bursa"))
            {
                havaDurumuLayout4.location.setText("Bursa");
                havaDurumuLayout4.status_text.setText(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS));
                if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("kar"))
                {
                    havaDurumuLayout4.image.setImageResource(R.drawable.snow);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağmur") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("sağanak")
                        || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Yağmur") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Sağanak"))
                {
                    havaDurumuLayout4.image.setImageResource(R.drawable.rain);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("bulut"))
                {
                    havaDurumuLayout4.image.setImageResource(R.drawable.cloud);
                }
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("güneş") ||arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("Güneş"))
                {
                    havaDurumuLayout4.image.setImageResource(R.drawable.sun);
                }
                else
                {
                    havaDurumuLayout4.image.setImageResource(R.drawable.cloud);
                }

                havaDurumuLayout4.temperature.setText(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.MIN_TEMPRATURE) + " ve " + arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.MAX_TEMPRATURE) + " derece arası");
            }
        }
    }


    @Override
    public void finishTask(String str, int type, int id)
    {

        if(id==1)
        {
            arManset = new ArManset(str);
            arManset.addObserver(this);
            arManset.setIndex(0);
            MANSET = true;
        }
        else if(id==2)
        {
            arSonDakika = new ArSonDakika(str);
            arSonDakika.addObserver(this);
            SONDAKIKA = true;
        }
        else if(id==3)
        {
            arAstroloji = new ArAstroloji(str);
            burcLayout.picture.setScaleType(ImageView.ScaleType.FIT_XY);
            burcLayout.spinner.setSelection(0);
            setBurc(0);
            BURC_CREATED = true;
        }
        else if(id==4)
        {
            arHavaDurumu = new ArHavaDurumu();
            arHavaDurumu.addObserver(this);
            arHavaDurumu.parse(str);
            WEATHER = true;
        }
        else if(id==5)
        {
            arPiyasa = new ArPiyasa();
            arPiyasa.addObserver(this);
            arPiyasa.parse(str);
            STOCK = true;
            PARSE_END = true;
        }
    }

    @Override
    public void finishImageTask() {
        mansetLayout.image_view.setVisibility(View.VISIBLE);
        mansetLayout.left_button.setVisibility(View.VISIBLE);
        mansetLayout.right_button.setVisibility(View.VISIBLE);
    }
}

