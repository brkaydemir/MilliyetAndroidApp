package com.example.burakaydemir.milliyetappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by burak.aydemir on 4.1.2016.
 */
public class ArPiyasa {

    public ArrayList<PiyasaItem> item_list;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungPiyasalar";

    public ArPiyasa()
    {
        item_list = new ArrayList<PiyasaItem>();
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
                        PiyasaItem temp = new PiyasaItem();
                        temp.reader(parser);
                        item_list.add(temp);
                    }
                    else if(parser.getName().equals("item"))
                    {
                        PiyasaItem temp = new PiyasaItem();
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

    public class PiyasaItem
    {
        public String piyasa_name;
        public String value;
        public String percent;
        public String status;

        public void reader(XmlPullParser parser)
        {
            try {

                parser.nextTag();
                parser.next();
                piyasa_name = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                value = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                percent = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                status = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //burc_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHaber&ArticleID=" + article_id ;
        }
    }
}
