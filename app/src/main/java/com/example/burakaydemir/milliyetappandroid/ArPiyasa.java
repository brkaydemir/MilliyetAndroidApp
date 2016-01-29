package com.example.burakaydemir.milliyetappandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by burak.aydemir on 4.1.2016.
 */
public class ArPiyasa extends Observable {

    public static final String PIYASA_NAME = "Name";
    public static final String VALUE = "Value";
    public static final String PERCENT = "Percent";
    public static final String STATUS = "Status";

    public ArrayList<PiyasaItem> item_list;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungPiyasalar";

    public ArPiyasa()
    {
        item_list = new ArrayList<PiyasaItem>();
    }

    public ArPiyasa(String str)
    {
        item_list = new ArrayList<PiyasaItem>();

        ArParser myparser = new ArParser(str);
        HashMap<String,String> temp = new HashMap<String,String>();
        while((temp = myparser.reader()) != null)
        {
            PiyasaItem tempItem = new PiyasaItem();
            tempItem.elements = temp;
            item_list.add(tempItem);
        }
        setChanged();
        notifyObservers();
    }

    public void parse(String str)
    {
        item_list = new ArrayList<PiyasaItem>();


        ArParser myparser = new ArParser(str);
        HashMap<String,String> temp = new HashMap<String,String>();
        PiyasaItem tempItem;
        while((temp = myparser.reader()) != null)
        {

            tempItem = new PiyasaItem();
            tempItem.elements = temp;
            item_list.add(tempItem);
        }
        setChanged();
        notifyObservers();
    }

    public class PiyasaItem
    {
        public HashMap<String, String> elements;

        public PiyasaItem() {
            elements = new HashMap<String, String>();
        }
    }
}
