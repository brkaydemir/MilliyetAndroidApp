package com.example.burakaydemir.milliyetappandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by burak.aydemir on 30.12.2015.
 */
public class ArSonDakika extends Observable
{
    public static final String ARTICLE_ID = "ArticleID";
    public static final String ARTICLE_TITLE_DETAIL = "ArticleTitleDetay";

    public ArrayList<SonDakikaItem> item_list;
    public int show_index;

    public final String TAG = "ArSonDakika";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungSonDakika";

    public ArSonDakika()
    {
        item_list = new ArrayList<SonDakikaItem>();
        show_index = 0;
    }

    public ArSonDakika(String str)
    {
        item_list = new ArrayList<SonDakikaItem>();
        show_index = 0;

        ArParser myparser = new ArParser(str);
        HashMap<String,String> temp = new HashMap<String,String>();
        while((temp = myparser.reader()) != null)
        {
            SonDakikaItem tempItem = new SonDakikaItem();
            tempItem.elements = temp;
            item_list.add(tempItem);
        }
    }

    public void inc_index()
    {
        show_index++;
        if(show_index>=item_list.size()) show_index=0;
        setChanged();
        notifyObservers();
    }
    public void dec_index()
    {
        show_index--;
        if(show_index<0) show_index=item_list.size()-1;
        setChanged();
        notifyObservers();
    }
    public void setIndex(int i)
    {
        show_index = i;
        if(show_index>=item_list.size()) show_index=item_list.size()-1;
        if(show_index<0) show_index=0;
        setChanged();
        notifyObservers();
    }

    public class SonDakikaItem {

        public HashMap<String, String> elements;

        public SonDakikaItem() {
            elements = new HashMap<String, String>();
        }
    }
}
