package com.example.burakaydemir.milliyetappandroid;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by burak.aydemir on 25.12.2015.
 */
public class ArRequest extends AsyncTask<String, Void, String>
{
    private final String son_dakika_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungSonDakika";
    private final String hava_durumu_ana_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHavaDurumuAnasayfa";
    private final String piyasalar_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungPiyasalar";
    private final String yazarlar_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungYazar";
    private final String son_dakika_liste_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungSonDakikaListe";
    private final String astroloji_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungAstroloji";
    private final String hava_durumu_detay_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHavaDurumu";
    private final String manset_url = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=Samsung11liManset";
    private final String kategori_detay = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungKategoriListe&CategoryID=";
    private final String haber_detay = "http://mw.milliyet.com.tr/ashx/Milliyet.ashx?aType=SamsungHaber&ArticleID=";


    public ArResponse responseHandler;
    public String request_url;
    public int type;
    public int id;


    public ArRequest( ) {
    }

    //type = 0(anasayfa), id=layoutID (manset,sondakika,burc,havadurumu,piyasa)
    //type = 1(kategori), id=kategoriID
    //type = 2(article),  id=articleID
    //type = 3(yazarlar)  id=not important
    public ArRequest( int type , int id)
    {
        this.type = type;
        this.id = id;
        if(type == 0)
        {
            //anasayfa request
            if(id==1)
            {
                request_url = manset_url;
            }
            else if(id==2)
            {
                request_url = son_dakika_url;
            }
            else if(id==3)
            {
                request_url = astroloji_url;
            }
            else if(id==4)
            {
                request_url = hava_durumu_detay_url;
            }
            else if(id==5)
            {
                request_url = piyasalar_url;
            }
        }
        else if(type == 1)
        {
            //kategori request
            request_url = kategori_detay + Integer.toString(id);
        }
        else if(type == 2)
        {
            request_url = haber_detay + Integer.toString(id);
        }
        else if(type == 3)
        {
            request_url = yazarlar_url;
        }
    }


    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(request_url);
        } catch (IOException ignored) {

        }
        return null;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        responseHandler.finishTask(result, type, id);
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            //Log.d("ArRequest", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            //String contentAsString = readIt(is, len);
            //Log.d("before return str", "downloadUrl: ");
            return readIt(is, len);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader;
        reader = new InputStreamReader(stream, "UTF-8");
        //parse(stream);

        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line).append('\n');
        }

        char[] buffer = new char[len];
        reader.read(buffer);
        return total.toString();
    }
}

