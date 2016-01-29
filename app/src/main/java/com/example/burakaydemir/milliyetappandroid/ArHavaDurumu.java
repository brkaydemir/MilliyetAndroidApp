package com.example.burakaydemir.milliyetappandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by burak.aydemir on 4.1.2016.
 */
public class ArHavaDurumu extends Observable {

    public static final String LOCATION = "Location";
    public static final String STATUS = "StatusText";
    public static final String MAX_TEMPRATURE = "HighestTemp";
    public static final String MIN_TEMPRATURE = "MinTemp";

    public ArrayList<HavaItem> item_list;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHavaDurumu";

    public ArHavaDurumu()
    {
        item_list = new ArrayList<HavaItem>();
    }


    public void parse(String str)
    {
        item_list = new ArrayList<HavaItem>();

        ArParser myparser = new ArParser(str);
        HashMap<String,String> temp = new HashMap<String,String>();
        HavaItem tempItem;
        while((temp = myparser.reader()) != null)
        {
            tempItem = new HavaItem();
            tempItem.elements = temp;
            item_list.add(tempItem);
        }
        setChanged();
        notifyObservers();
    }

    public class HavaItem {

        public HashMap<String, String> elements;

        public HavaItem() {
            elements = new HashMap<String, String>();
        }
    }
}
