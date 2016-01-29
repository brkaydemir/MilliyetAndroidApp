package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by burak.aydemir on 8.1.2016.
 */
public class BurcLayout extends RelativeLayout
{

    public IAnasayfa ianasayfa;
    public ImageView picture;
    public TextView burc_detay;
    public Spinner spinner;

    public BurcLayout(Context context) {
        this(context, null, 0);
    }

    public BurcLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BurcLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.burc_layout, this);
        if(!this.isInEditMode())
            ianasayfa = (IAnasayfa) context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        picture = (ImageView) findViewById(R.id.imageView2);
        burc_detay = (TextView) findViewById(R.id.textView);

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                if(Anasayfa.BURC_CREATED)
                    ianasayfa.setBurc(parent.getSelectedItemPosition());
                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getContext(),R.array.burclar, android.R.layout.simple_spinner_item);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

}
