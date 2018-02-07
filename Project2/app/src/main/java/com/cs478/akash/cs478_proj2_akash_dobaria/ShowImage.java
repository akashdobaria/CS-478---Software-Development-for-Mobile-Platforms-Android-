package com.cs478.akash.cs478_proj2_akash_dobaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        final String company_url = this.getIntent().getExtras().getString("company_url");
        final String company_name = this.getIntent().getExtras().getString("company_name");

        //set company name as title on the action bar
        setTitle(company_name);

        //find the image view and set the image to be shown
        imageContainer = (ImageView) findViewById(R.id.imageView);
        imageContainer.setImageResource(image_res);

        //add listener for click
        imageContainer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // create intent, put url in extra and start activity
                Intent goToWeb = new Intent(ShowImage.this, TakeToWeb.class);
                goToWeb.putExtra("url", company_url);
                Log.i ("image id: ",""+company_url);
                goToWeb.putExtra("company_name", company_name);
                startActivity(goToWeb);
            }
        });
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
