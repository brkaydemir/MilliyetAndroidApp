package com.example.burakaydemir.milliyetappandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by burak.aydemir on 26.1.2016.
 */
public class ArKategori extends Observable {

    public static final String MAIN_CATEGORY_NAME = "MainCategoryName";
    public static final String ARTICLE_ID = "ArticleID";
    public static final String ARTICLE_TITLE_DETAIL = "ArticleTitleDetay";

    public ArrayList<KategoriItem> item_list;
    public int id;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungKategoriListe&CategoryID=";

    public HashMap<String,String> content;

    public ArKategori(String id)
    {
        item_list= new ArrayList<KategoriItem>();
        this.id = Integer.parseInt(id);
    }

    public void parse(String str)
    {
        item_list = new ArrayList<KategoriItem>();

        ArParser myparser = new ArParser(str);
        HashMap<String,String> temp = new HashMap<String,String>();
        KategoriItem tempItem;
        while((temp = myparser.reader()) != null)
        {
            tempItem = new KategoriItem();
            tempItem.elements = temp;
            item_list.add(tempItem);
        }
        setChanged();
        notifyObservers();
    }


    public class KategoriItem {

        public HashMap<String, String> elements;

        public KategoriItem() {
            elements = new HashMap<String, String>();
        }
    }

}
