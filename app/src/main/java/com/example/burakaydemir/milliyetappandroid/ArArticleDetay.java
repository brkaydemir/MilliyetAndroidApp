package com.example.burakaydemir.milliyetappandroid;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Observable;


/**
 * Created by burak.aydemir on 22.1.2016.
 */
public class ArArticleDetay extends Observable {

    public String CategoryID;
    public String ArticleID;
    public String ArticleTitleDetay;
    public String ArticleSpotDetay;
    public String ArticleText;
    public String ArticleHour;
    public String image_url;
    public String AuthorID;
    public String AuthorName;
    public String AuthorSurname;
    public String CornerName;

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

                        reader(parser);

                    }
                    else if(parser.getName().equals("item"))
                    {

                        reader(parser);
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

    public void reader(XmlPullParser parser)
    {
        try {

            parser.nextTag();
            parser.next();
            CategoryID = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            ArticleID = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            ArticleTitleDetay = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            ArticleSpotDetay = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            ArticleText = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            ArticleHour = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            image_url = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            AuthorID = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            AuthorName = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            AuthorSurname = parser.getText().trim();
            parser.nextTag();
            parser.nextTag();
            parser.next();
            CornerName = parser.getText().trim();
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
