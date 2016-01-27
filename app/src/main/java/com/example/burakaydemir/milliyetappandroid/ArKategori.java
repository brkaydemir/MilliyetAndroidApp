package com.example.burakaydemir.milliyetappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by burak.aydemir on 26.1.2016.
 */
public class ArKategori extends Observable {

    public ArrayList<KategoriItem> item_list;
    public int id;

    public final String TAG = "ArAstroloji";
    public final String url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungKategoriListe&CategoryID=";

    public ArKategori(String id)
    {
        item_list= new ArrayList<KategoriItem>();
        this.id = Integer.parseInt(id);
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
                        KategoriItem temp = new KategoriItem();
                        temp.reader(parser);
                        item_list.add(temp);
                    }
                    else if(parser.getName().equals("item"))
                    {
                        KategoriItem temp = new KategoriItem();
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

    public class KategoriItem {

        public String category_id;
        public String subcategory_name;
        public String main_category_name;
        public String article_publish_date;
        public String article_id;
        public String article_title_detay;
        public String rowno;
        public String file_url;
        public String article_url;

        public void reader(XmlPullParser parser)
        {
            try {

                parser.nextTag();
                parser.next();
                category_id = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                subcategory_name = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                main_category_name = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                article_publish_date = parser.getText().trim();
                parser.nextTag();
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
                rowno = parser.getText().trim();
                parser.nextTag();
                parser.nextTag();
                parser.next();
                file_url = parser.getText().trim();
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
