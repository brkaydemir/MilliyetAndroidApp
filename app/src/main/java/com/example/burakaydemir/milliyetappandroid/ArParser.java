package com.example.burakaydemir.milliyetappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        } catch (XmlPullParserException ignored) {

        }

    }

    @Deprecated
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
        } catch (XmlPullParserException | IOException ignored) {

        }
        return result;
    }

    public HashMap<String, String> reader() {

        int event = 0;
        HashMap<String,String> result = new HashMap<String,String>();
        String key;
        String value;
        try {
            event = parser.getEventType();
            while(true)
            {
                if(event == XmlPullParser.START_TAG && parser.getName().equals("item")) break;

                if(event == XmlPullParser.END_DOCUMENT)
                    return null;

                event = parser.nextTag();
            }
            event = parser.nextTag();
            while(true){
                if(event== XmlPullParser.END_TAG && parser.getName().equals("item")) break;
                if(event == XmlPullParser.START_TAG)
                {
                    key = parser.getName();
                    //Log.d("parser", "reader: " + parser.getName());
                    parser.next();
                    //Log.d("parser", "reader: " + parser.getText());
                    value = parser.getText().trim();
                    value = cleanText(value);
                    result.put(key,value);
                }
                if(event == XmlPullParser.END_DOCUMENT)
                    return null;
                event = parser.nextTag();
            }
        } catch (XmlPullParserException | IOException ignored) {

        }

        return result;
    }

    public String cleanText(String str)
    {
        String burc_spot=str;

        burc_spot=burc_spot.replaceAll("<p>","");
        burc_spot=burc_spot.replaceAll("&ccedil;","ç");
        burc_spot=burc_spot.replaceAll("&Ccedil;","Ç");
        burc_spot=burc_spot.replaceAll("&nbsp;","");
        burc_spot=burc_spot.replaceAll("&ouml;","ö");
        burc_spot=burc_spot.replaceAll("&Ouml;","Ö");
        burc_spot=burc_spot.replaceAll("&uuml;","ü");
        burc_spot=burc_spot.replaceAll("&Uuml;","Ü");
        burc_spot=burc_spot.replaceAll("</p>","");
        burc_spot=burc_spot.replaceAll("<br />","");

        return burc_spot;
    }
}
