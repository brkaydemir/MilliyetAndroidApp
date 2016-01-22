package com.example.burakaydemir.milliyetappandroid;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by burak.aydemir on 30.12.2015.
 */
public class ArSonDakika extends Observable
{
    public ArrayList<SonDakikaItem> item_list;
    public int show_index;

    public final String TAG = "ArSonDakika";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungSonDakika";

    public ArSonDakika()
    {
        item_list = new ArrayList<SonDakikaItem>();
        show_index = 0;
    }

    public void parse (XmlPullParser parser)
    {

        try {
            int event = parser.getEventType();

            while(event!= XmlPullParser.END_DOCUMENT){
                if(event == XmlPullParser.START_TAG)
                {
                    if(parser.getName().equals("root"))
                    {
                        event=parser.nextTag();
                        SonDakikaItem temp = new SonDakikaItem();
                        temp.reader(parser);
                        item_list.add(temp);
                    }
                    else if(parser.getName().equals("item"))
                    {
                        SonDakikaItem temp = new SonDakikaItem();
                        temp.reader(parser);
                        item_list.add(temp);
                    }
                }
                event = parser.nextTag();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

        public String article_id;
        public String article_title_detay;
        public String publish_hour;
        public String publish_minute;
        public String article_url;


        public void reader(XmlPullParser parser)
        {
            try {
                parser.nextTag();
                parser.next();
                article_id = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                article_title_detay = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                publish_hour = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                publish_minute = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            article_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHaber&ArticleID=" + article_id ;
        }
    }
}
