package com.example.burakaydemir.milliyetappandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by burak.aydemir on 29.1.2016.
 */
public class ArYazar extends Observable {

    public static final String ARTICLE_ID = "ArticleID";
    public static final String ARTICLE_TITLE_DETAIL = "ArticleTitleDetay";
    public static final String AUTHOR_NAME = "AuthorName";
    public static final String CORNER_NAME = "CornerName";
    public static final String AUTHOR_SURNAME = "AuthorSurname";

    public ArrayList<YazarItem> item_list;
    public int id;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungKategoriListe&CategoryID=";

    public HashMap<String,String> content;

    public ArYazar(String id)
    {
        item_list= new ArrayList<YazarItem>();
        this.id = Integer.parseInt(id);
    }

    public void parse(String str)
    {
        item_list = new ArrayList<YazarItem>();

        ArParser myparser = new ArParser(str);
        HashMap<String,String> temp = new HashMap<String,String>();
        YazarItem tempItem;
        while((temp = myparser.reader()) != null)
        {
            tempItem = new YazarItem();
            tempItem.elements = temp;
            item_list.add(tempItem);
        }
        setChanged();
        notifyObservers();
    }


    public class YazarItem {

        public HashMap<String, String> elements;

        public YazarItem() {
            elements = new HashMap<String, String>();
        }
    }
}
