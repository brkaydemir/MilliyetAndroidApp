package com.example.burakaydemir.milliyetappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by burak.aydemir on 4.1.2016.
 */
public class ArHavaDurumu {

    public ArrayList<HavaItem> item_list;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHavaDurumuAnasayfa";

    public ArHavaDurumu()
    {
        item_list = new ArrayList<HavaItem>();
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
                        HavaItem temp = new HavaItem();
                        temp.reader(parser);
                        item_list.add(temp);
                    }
                    else if(parser.getName().equals("item"))
                    {
                        HavaItem temp = new HavaItem();
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

    public class HavaItem {

        public String location;
        public String date;
        public String max_temp;
        public String min_temp;
        public String status;
        public String status_int;
        public String date2;
        public String max_temp2;
        public String min_temp2;
        public String status2;
        public String status_int2;
        public String date3;
        public String max_temp3;
        public String min_temp3;
        public String status3;
        public String status_int3;


        public void reader(XmlPullParser parser)
        {
            try {

                parser.nextTag();
                parser.next();
                location = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                date = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                status_int = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                max_temp = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                min_temp = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                status = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                date2 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                status_int2 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                max_temp2 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                min_temp2 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                status2 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                date3 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                status_int3 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                max_temp3 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                min_temp3 = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                status3 = parser.getText().trim();
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
