package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.simonvt.menudrawer.MenuDrawer;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class KategoriActivity extends AppCompatActivity implements ArResponse,Observer {

    public ArKategori arKategori;

    public TextView kategoriText;
    public Button menuButton;
    public MenuDrawer mDrawer;
    public ListView titleList;
    public ListView kategoriList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawer = MenuDrawer.attach(this);
        mDrawer.setMenuView(R.layout.menu_drawer);
        mDrawer.setContentView(R.layout.activity_kategori);


        kategoriText = (TextView) findViewById(R.id.kategoriText);
        menuButton = (Button) findViewById(R.id.button14);

        titleList = (ListView) findViewById(R.id.article_list);

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
        String title_str[] = new String[arKategori.item_list.size()-1];

        for(int i = 0; i<arKategori.item_list.size()-1;i++)
        {
            title_str[i] = arKategori.item_list.get(i).elements.get(ArKategori.ARTICLE_TITLE_DETAIL);
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
                intent.putExtra("article_url", arKategori.item_list.get(position).elements.get(ArKategori.ARTICLE_ID));
                startActivity(intent);
            }
        });
    }

    @Override
    public void finishTask(String str, int type, int id) {
        arKategori = new ArKategori(Integer.toString(id));
        arKategori.addObserver(this);
        arKategori.parse(str);
    }

    @Override
    public void finishImageTask() {

    }

    @Override
    public void update(Observable observable, Object data) {
        setContent();
    }

    public void goKategori(int id)
    {
        Intent intent = new Intent(this,KategoriActivity.class);
        intent.putExtra("kategori_type", Integer.toString(id));

        startActivity(intent);
    }

    public void goYazar() {

        Intent intent = new Intent(this,YazarlarActivity.class);
        startActivity(intent);
    }

    public void goAnasayfa()
    {
        Intent intent = new Intent(this, Anasayfa.class);
        intent.putExtra("dummy","dummy_str");
        startActivity(intent);
    }
}
