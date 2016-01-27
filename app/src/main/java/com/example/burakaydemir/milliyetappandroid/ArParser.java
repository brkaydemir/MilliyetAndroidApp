package com.example.burakaydemir.milliyetappandroid;

import android.support.annotation.NonNull;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by burak.aydemir on 27.1.2016.
 */
public class ArParser {

    public XmlPullParser parser;

    public ArParser(String data)
    {
        try {

            XmlPullParserFactory XmlProcessor = XmlPullParserFactory.newInstance();
            XmlProcessor.setNamespaceAware(true);
            parser = XmlProcessor.newPullParser();
            parser.setInput(new StringReader(data));

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Map<String,String>> parse()
    {
        ArrayList<Map<String,String>> result = new ArrayList<Map<String,String>>();
        Map<String,String> temp;
        try {
            int event = parser.getEventType();

            while(event!= XmlPullParser.END_DOCUMENT){
                if(event == XmlPullParser.START_TAG)
                {
                    if(parser.getName().equals("item"))
                    {
                        temp = new HashMap<String,String>();
                        temp = reader();
                        result.add(temp);
                    }
                }
                event = parser.nextTag();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<String, String> reader() {

        int event = 0;
        Map<String,String> result = new HashMap<String,String>();
        String key;
        String value;
        try {
            event = parser.getEventType();

            while(event!= XmlPullParser.END_TAG && !parser.getName().equals("item")){
                if(event == XmlPullParser.START_TAG)
                {
                    key = parser.getName();
                    parser.next();
                    value = parser.getText().toString().trim();
                    result.put(key,value);
                }
                event = parser.nextTag();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
