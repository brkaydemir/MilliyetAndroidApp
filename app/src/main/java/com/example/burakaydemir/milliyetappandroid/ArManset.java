package com.example.burakaydemir.milliyetappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by burak.aydemir on 4.1.2016.
 */
public class ArManset extends Observable
{
    public final String TAG = "ArManset";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=Samsung11liManset";

    public ArrayList<MansetItem> item_list;
    public int show_index;

    public ArManset()
    {
        item_list = new ArrayList<MansetItem>();
        show_index = 0;
    }

    public void parse(XmlPullParser parser)
    {
        try {
            int event = parser.getEventType();

            while(event!= XmlPullParser.END_DOCUMENT){
                if(event == XmlPullParser.START_TAG)
                {
                    if(parser.getName().equals("root"))
                    {
                        event=parser.nextTag();
                        MansetItem temp = new MansetItem();
                        temp.reader(parser);
                        item_list.add(temp);
                    }
                    else if(parser.getName().equals("item"))
                    {
                        MansetItem temp = new MansetItem();
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

    public class MansetItem {
        public String article_id;
        public String article_title_manset;
        public String big_image_url;
        public String small_image_url;
        public String manset_url;

        public void reader(XmlPullParser parser)
        {

            article_id="";
            try {
                int event=parser.getEventType();

                parser.nextTag();
                parser.next();
                article_id = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                article_title_manset = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                big_image_url = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                small_image_url = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            manset_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHaber&ArticleID=" + article_id ;
        }
    }
}
