package com.example.burakaydemir.milliyetappandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by burak.aydemir on 4.1.2016.
 */
public class ArAstroloji extends Observable {

    public static final String BURC_SPOT = "Spot";

    public ArrayList<AstrolojiItem> item_list;
    public int show_index;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungAstroloji";

    public ArAstroloji()
    {
        item_list= new ArrayList<AstrolojiItem>();
        show_index=6;
    }

    public ArAstroloji(String str)
    {
        item_list = new ArrayList<AstrolojiItem>();
        show_index = 0;

        ArParser myparser = new ArParser(str);
        HashMap<String,String> temp = new HashMap<String,String>();
        while((temp = myparser.reader()) != null)
        {
            AstrolojiItem tempItem = new AstrolojiItem();
            tempItem.elements = temp;
            //tempItem.cleanText();
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
        if(show_index<0) show_index=0;
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

    public class AstrolojiItem {

        public HashMap<String, String> elements;

        public AstrolojiItem() {
            elements = new HashMap<String, String>();
        }

    }


}
