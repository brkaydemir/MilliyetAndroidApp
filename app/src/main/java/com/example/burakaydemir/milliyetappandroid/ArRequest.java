package com.example.burakaydemir.milliyetappandroid;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

    private InputStream httpStream;



    public ArRequest( ) {
    }


    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Anasayfa.textView.setText(result);
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
            int response = conn.getResponseCode();
            //Log.d("ArRequest", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            //Log.d("before return str", "downloadUrl: ");
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        httpStream = stream;
        reader = new InputStreamReader(stream, "UTF-8");
        //parse(stream);

        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line+'\n');
        }

        char[] buffer = new char[len];
        reader.read(buffer);
        //Log.d("read", "readIt: ");
        //return new String(buffer);
        return total.toString();
    }
}

