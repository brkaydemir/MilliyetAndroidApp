package com.example.burakaydemir.milliyetappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by burak.aydemir on 4.1.2016.
 */
public class ArAstroloji extends Observable {
    public ArrayList<AstrolojiItem> item_list;
    public int show_index;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungAstroloji";

    public ArAstroloji()
    {
        item_list= new ArrayList<AstrolojiItem>();
        show_index=6;
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
                        AstrolojiItem temp = new AstrolojiItem();
                        temp.reader(parser);
                        item_list.add(temp);
                    }
                    else if(parser.getName().equals("item"))
                    {
                        AstrolojiItem temp = new AstrolojiItem();
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

    public class AstrolojiItem {

        public String article_id;
        public String burc_name;
        public String burc_title;
        public String burc_spot;
        public String spot_manset;
        public String burc_url;


        public void reader(XmlPullParser parser)
        {
            try {

                parser.nextTag();
                parser.next();
                article_id = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                burc_name = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                burc_title = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                burc_spot = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                spot_manset = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            burc_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHaber&ArticleID=" + article_id ;
        }
    }
}
