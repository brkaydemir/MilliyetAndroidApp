package com.example.burakaydemir.milliyetappandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Created by burak.aydemir on 7.1.2016.
 */
public class MansetLayout extends RelativeLayout {

    public ImageButton right_button;
    public ImageButton left_button;
    public ImageButton image_view;
    public IAnasayfa ianasayfa;

    public MansetLayout(Context context) {
        this(context, null, 0);
    }

    public MansetLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MansetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.manset_layout, this);
        //ianasayfa = (IAnasayfa) context;
        if(!this.isInEditMode())
            ianasayfa = (IAnasayfa) context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        left_button = (ImageButton) findViewById(R.id.button);
        right_button = (ImageButton) findViewById(R.id.button2);
        image_view = (ImageButton) findViewById(R.id.imageView);

        left_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ianasayfa.dec_index_manset();
            }
        });

        right_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ianasayfa.inc_index_manset();
            }
        });

        image_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ianasayfa.goMansetArticle();
            }
        });

    }

    public void setBitmap(Bitmap bitmap)
    {
        image_view.setImageBitmap(bitmap);
    }
}
