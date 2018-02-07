package com.cs478.akash.cs478_proj2_akash_dobaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/******************************************************************************
 * Activity to show the list of dealers. It uses ListView to show all dealers *
 ******************************************************************************/
public class Dealers extends AppCompatActivity {

    //declare the listview and CarDeatails obejct arraylist
    private ListView dealers;
    private ArrayList<CarDetails> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set view for the activity
        setContentView(R.layout.activity_dealers);

        //get the extras received
        String company_name = this.getIntent().getExtras().getString("company_name");
        Integer index = this.getIntent().getExtras().getInt("index");

        //set title on the action bar
        setTitle(company_name + " Dealers in Chicago");

        //get the listview
        dealers = (ListView) findViewById(R.id.listview_dealers);

        //create object to get all deatils
        cars = CarDetails.createArrayList();

        //create custom adapter and set for the listview
        DealerAdapter adapter = new DealerAdapter(this, cars.get(index).dealers);
        dealers.setAdapter(adapter);

    }
}
