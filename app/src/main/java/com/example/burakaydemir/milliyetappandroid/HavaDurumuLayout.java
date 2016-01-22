package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.sax.TextElementListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by burak.aydemir on 20.1.2016.
 */
public class HavaDurumuLayout extends RelativeLayout {

    public IAnasayfa ianasayfa;
    public TextView location;
    public TextView status_text;
    public TextView temperature;

    public HavaDurumuLayout(Context context) {
        this(context, null, 0);
    }

    public HavaDurumuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HavaDurumuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.havadurumu_layout, this);
        if(!this.isInEditMode())
            ianasayfa = (IAnasayfa) context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        location = (TextView) findViewById(R.id.textView2);
        status_text = (TextView) findViewById(R.id.textView3);
        temperature = (TextView) findViewById(R.id.textView4);

    }
}
