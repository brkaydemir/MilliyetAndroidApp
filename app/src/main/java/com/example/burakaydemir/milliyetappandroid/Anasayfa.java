package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.simonvt.menudrawer.MenuDrawer;
import java.util.Observable;
import java.util.Observer;

public class Anasayfa extends AppCompatActivity implements Observer, IAnasayfa, ArResponse {

    public static TextView textView;

    public static boolean BURC_CREATED;
    public static boolean PARSE_END;

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
    public Button turkiye;
    public Button dunya;
    public Button ekonomi;
    public Button siyaset;
    public Button yasam;
    public Button spor;
    public Button ege;
    public Button cafe;
    public Button yazarlar;

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

        turkiye = (Button) findViewById(R.id.button4);
        dunya = (Button) findViewById(R.id.button5);
        ekonomi = (Button) findViewById(R.id.button6);
        siyaset = (Button) findViewById(R.id.button7);
        yasam = (Button) findViewById(R.id.button8);
        spor = (Button) findViewById(R.id.button9);
        ege = (Button) findViewById(R.id.button10);
        cafe = (Button) findViewById(R.id.button11);
        yazarlar = (Button) findViewById(R.id.button12);



        turkiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goKategori(1);
                mDrawer.closeMenu();
            }
        });

        dunya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goKategori(2);
                mDrawer.closeMenu();
            }
        });

        ekonomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goKategori(3);
                mDrawer.closeMenu();
            }
        });

        siyaset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goKategori(4);
                mDrawer.closeMenu();
            }
        });

        yasam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goKategori(5);
                mDrawer.closeMenu();
            }
        });

        spor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goKategori(6);
                mDrawer.closeMenu();
            }
        });

        ege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goKategori(7);
                mDrawer.closeMenu();
            }
        });

        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goKategori(8);
                mDrawer.closeMenu();
            }
        });

        yazarlar.setOnClickListener(new View.OnClickListener() {
            //// TODO: 26.1.2016 yazarlar degisecek
            @Override
            public void onClick(View v) {
                goYazar();
                mDrawer.closeMenu();
            }
        });

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
                } catch (InterruptedException ignored) {
                }
            }
        };

        t.start();


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
            ArRequest request1 = new ArRequest(0,1);
            request1.responseHandler = this;
            request1.execute();

            ArRequest request2 = new ArRequest(0,2);
            request2.responseHandler = this;
            request2.execute();

            ArRequest request3 = new ArRequest(0,3);
            request3.responseHandler = this;
            request3.execute();

            ArRequest request4 = new ArRequest(0,4);
            request4.responseHandler = this;
            request4.execute();

            ArRequest request5 = new ArRequest(0,5);
            request5.responseHandler = this;
            request5.execute();
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


        if(observable instanceof ArManset)
            new ArImageRequest(mansetLayout.image_view).execute(arManset.item_list.get(arManset.show_index).elements.get(ArManset.BIG_IMAGE_URL));
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
        intent.putExtra("article_url",arSonDakika.item_list.get(arSonDakika.show_index).elements.get(ArSonDakika.ARTICLE_ID));

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
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağmur"))
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
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağmur"))
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
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağmur"))
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
                else if(arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağış") || arHavaDurumu.item_list.get(i).elements.get(ArHavaDurumu.STATUS).contains("yağmur"))
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
        }
        else if(id==2)
        {
            arSonDakika = new ArSonDakika(str);
            arSonDakika.addObserver(this);
        }
        else if(id==3)
        {
            arAstroloji = new ArAstroloji(str);
            BURC_CREATED = true;
            burcLayout.spinner.setSelection(0);
            setBurc(0);
        }
        else if(id==4)
        {
            arHavaDurumu = new ArHavaDurumu();
            arHavaDurumu.addObserver(this);
            arHavaDurumu.parse(str);
        }
        else if(id==5)
        {
            arPiyasa = new ArPiyasa();
            arPiyasa.addObserver(this);
            arPiyasa.parse(str);
            PARSE_END = true;
        }
    }
}

