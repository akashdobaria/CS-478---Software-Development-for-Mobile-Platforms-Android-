package com.cs478.project3app2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

/******************************************************************************
 * Activity to show the full image when user taps on any cell of the gridveiw *
 ******************************************************************************/
public class ShowImage extends AppCompatActivity {

    // declare the imageview need to show the image
    ImageView imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the layout for the activity
        setContentView(R.layout.activity_show_image);

        //get the extras received
        Integer image_res = this.getIntent().getExtras().getInt("image_resource");

        //find the image view and set the image to be shown
        imageContainer = (ImageView) findViewById(R.id.imageView);
        imageContainer.setImageResource(image_res);
    }

    //finish the activity when user clicks back button to release the resources allocated
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
