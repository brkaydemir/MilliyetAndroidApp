package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by burak.aydemir on 8.1.2016.
 */
public class SonDakikaLayout extends RelativeLayout
{
    Button button;
    IAnasayfa ianasayfa;

    public SonDakikaLayout(Context context) {
        this(context, null, 0);
    }

    public SonDakikaLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SonDakikaLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.sondakika_layout, this);
        if(!this.isInEditMode())
            ianasayfa = (IAnasayfa) context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO son dakika detay activity
            }
        });

    }
}
