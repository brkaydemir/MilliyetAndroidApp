package com.example.burakaydemir.milliyetappandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by burak.aydemir on 29.1.2016.
 */
public class LoadingFragment extends Fragment {


    public LoadingFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.loading_fragment_layout, null);

        Log.d("asdasd", "onCreateView:  oramakomaburamako ");
        return rootView;
    }
}
