package com.example.burakaydemir.milliyetappandroid;

import java.util.HashMap;
import java.util.Observable;


/**
 * Created by burak.aydemir on 22.1.2016.
 */
public class ArArticleDetay extends Observable {

    public static final String ARTICLE_TITLE_DETAIL = "ArticleTitleDetay";
    public static final String ARTICLE_SPOT_DETAIL = "ArticleSpotDetay";
    public static final String ARTICLE_TEXT = "ArticleText";
    public static final String IMAGE_URL = "FilePathAndName";

    public HashMap<String,String> content;
    public void parse(String str)
    {
        content = new HashMap<String,String>();

        ArParser myparser = new ArParser(str);
        content = myparser.reader();

        setChanged();
        notifyObservers();
    }
}
