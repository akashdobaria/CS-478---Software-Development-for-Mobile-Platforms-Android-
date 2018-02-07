package com.cs478.project3app2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private GridView grid_landmarks;

    ArrayList<Integer> images = new ArrayList<Integer>(
            Arrays.asList(R.drawable.art_institute_chicago, R.drawable.field_mueseum, R.drawable.millenium_park,
                    R.drawable.navy_pier, R.drawable.shedd_aquarium, R.drawable.willis_tower));


    private static final String MY_PERMISSION = "com.cs478.project3";

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //Check if the app has the permission defined, if not request for the permission. Load the gridview otherwise.
        if (ContextCompat.checkSelfPermission(context, MY_PERMISSION) !=  PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context, new String[]{MY_PERMISSION}, 0);
        }
        else{
            //Get the gridview
            grid_landmarks = (GridView) findViewById(R.id.grid_landmarks);

            //declare the adapter and set it to the gridview
            final GalleryAdapter adapter = new GalleryAdapter(this, images);
            grid_landmarks.setAdapter(adapter);

            //Listener to show image on user click
            grid_landmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //get the object on which the click is performed
                    Integer selected_landmark = images.get(position);

                    //creat a new intent that will take the user to the new activity and will show the image
                    Intent showImage = new Intent(context, ShowImage.class);

                    //put image resource id, company name and the url of the company in extra to pass thos information to next activity
                    showImage.putExtra("image_resource", selected_landmark);

                    //start the activity
                    startActivity(showImage);
                }
            });
        }
    }

    //check if user approved or deniend the permission when requested.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check if permission is granted
        if(grantResults[0] != PackageManager.PERMISSION_GRANTED){

            Toast.makeText(context, "Permission not granted by user. Exiting the app.", Toast.LENGTH_LONG);
            finish();

        }
        else{
            //Get the gridview
            grid_landmarks = (GridView) findViewById(R.id.grid_landmarks);

            //declare the adapter and set it to the gridview
            final GalleryAdapter adapter = new GalleryAdapter(this, images);
            grid_landmarks.setAdapter(adapter);

            //Listener to show image on user click
            grid_landmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //get the object on which the click is performed
                    Integer selected_landmark = images.get(position);

                    //creat a new intent that will take the user to the new activity and will show the image
                    Intent showImage = new Intent(context, ShowImage.class);

                    //put image resource id, company name and the url of the company in extra to pass thos information to next activity
                    showImage.putExtra("image_resource", selected_landmark);

                    //start the activity
                    startActivity(showImage);
                }
            });
        }
    }


}
