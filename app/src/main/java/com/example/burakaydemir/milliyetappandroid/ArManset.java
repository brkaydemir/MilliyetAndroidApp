package com.example.burakaydemir.milliyetappandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by burak.aydemir on 4.1.2016.
 */
public class ArManset extends Observable
{
    public static final String ARTICLE_ID = "ArticleID";
    public static final String BIG_IMAGE_URL = "MansetBuyukImage";

    public final String TAG = "ArManset";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=Samsung11liManset";

    public ArrayList<MansetItem> item_list;
    public int show_index;

    public ArManset()
    {
        item_list = new ArrayList<MansetItem>();
        show_index = 0;
    }

    public ArManset(String str)
    {
        item_list = new ArrayList<MansetItem>();
        show_index = 0;


        ArParser myparser = new ArParser(str);
        HashMap<String,String> temp = new HashMap<String,String>();
        while((temp = myparser.reader()) != null)
        {
            MansetItem tempItem = new MansetItem();
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

    public class MansetItem {

        public HashMap<String,String> elements;

        public MansetItem()
        {
            elements = new HashMap<String,String>();
        }
    }
}
