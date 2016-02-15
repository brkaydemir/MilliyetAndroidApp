package com.example.burakaydemir.milliyetappandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by burak.aydemir on 7.1.2016.
 */
public class ArImageRequest extends AsyncTask<String, Void, Bitmap> {
    public ImageView bmImage;
    public ArResponse responseHandler;

    public ArImageRequest(ImageView bmImage) {
        //Log.d(TAG, "ArImageRequest: ");
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception ignored) {

        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {


        if(result!=null)
            bmImage.setImageBitmap(result);
        if(responseHandler!=null)
            responseHandler.finishImageTask();

    }
}

