package com.example.burakaydemir.milliyetappandroid;

/**
 * Created by burak.aydemir on 7.1.2016.
 */
public interface IAnasayfa {
    void inc_index_manset();
    void dec_index_manset();
    void setIndex_manset(int i);
    void inc_index_sondakika();
    void dec_index_sondakika();
    void setIndex_sondakika(int i);
    void setBurc(int i);
    void goMansetArticle();
    void goSonDakikaArticle();
    void goBurcArticle();
}
