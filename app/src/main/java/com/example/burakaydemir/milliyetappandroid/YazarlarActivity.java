package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.simonvt.menudrawer.MenuDrawer;

import java.util.Observable;
import java.util.Observer;

public class YazarlarActivity extends AppCompatActivity implements Observer,ArResponse {

    public ArYazar arYazar;

    public TextView kategoriText;
    public MenuDrawer mDrawer;
    public Button menuButton;
    public ListView titleList;
    public ListView kategoriList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mDrawer = MenuDrawer.attach(this);
        mDrawer.setMenuView(R.layout.menu_drawer);
        mDrawer.setContentView(R.layout.activity_kategori);


        kategoriText = (TextView) findViewById(R.id.kategoriText);
        menuButton = (Button) findViewById(R.id.button14);
        titleList = (ListView) findViewById(R.id.listView1);


        kategoriList = (ListView) findViewById(R.id.listView2);



        Resources res= getResources();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.kategori_list_layout, res.getStringArray(R.array.kategoriler));

        kategoriList.setAdapter(adapter);

        kategoriList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    goAnasayfa();
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
    public void finishImageTask() {

    }

    @Override
    public void update(Observable observable, Object data) {
        setContent();
    }

    public void setContent()
    {
        kategoriText.setText("Yazarlar");
        String title_str[] = new String[arYazar.item_list.size()-1];

        for(int i = 0; i<arYazar.item_list.size()-1;i++)
        {
            title_str[i] = arYazar.item_list.get(i).elements.get(ArYazar.AUTHOR_NAME)+' '+arYazar.item_list.get(i).elements.get(ArYazar.AUTHOR_SURNAME) + '\n' + arYazar.item_list.get(i).elements.get(ArYazar.ARTICLE_TITLE_DETAIL);
            //Log.d("oramakomaburamako", "setContent: "+ arKategori.item_list.get(i).elements.get(ArKategori.ARTICLE_TITLE_DETAIL));
        }

        //String[] mobileArray = {"Android","IPhone","WindowsMobile ANGARADA HİC YOK LUMIA WİNDOWS PHONE OLALI COK BOZDU","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.kategori_list_layout, title_str);

        titleList=(ListView) findViewById(R.id.listView1);

        titleList.setAdapter(adapter);

        titleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                intent.putExtra("article_url", arYazar.item_list.get(position).elements.get(ArYazar.ARTICLE_ID));
                startActivity(intent);
            }
        });

    }

    public void goKategori(int id) {
        Intent intent = new Intent(this,KategoriActivity.class);
        intent.putExtra("kategori_type", Integer.toString(id));

        startActivity(intent);
    }

    public void goYazar() {

        Intent intent = new Intent(this,YazarlarActivity.class);
        startActivity(intent);
    }

    public void goAnasayfa() {
        Intent intent = new Intent(this, Anasayfa.class);
        startActivity(intent);
    }
}
