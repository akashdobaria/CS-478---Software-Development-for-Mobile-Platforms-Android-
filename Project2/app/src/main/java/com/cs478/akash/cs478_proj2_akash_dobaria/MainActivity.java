package com.cs478.akash.cs478_proj2_akash_dobaria;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;

/********************************************************
 * Main activity when application is opened             *
 *******************************************************/

public class MainActivity extends AppCompatActivity {

    //declare variable to store gridview and the CarDetails object arraylist that stores the information of cars
    private GridView grid_cars;
    private ArrayList<CarDetails> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the layout for the activity
        setContentView(R.layout.activity_main);

        //Initialise the gridview and also register it to respond to context menu
        grid_cars = (GridView) findViewById(R.id.grid_cars);
        registerForContextMenu(grid_cars);

        //Initialise the cars object
        cars = CarDetails.createArrayList();

        // Create a new custom adaptor (GalleryAdaptor) and set it as the adapter for this gridview
        GalleryAdapter adapter = new GalleryAdapter(this, cars);
        grid_cars.setAdapter(adapter);

        //get activity context
        final Context context = this;

        //define onItemClickListener on the gridview
        grid_cars.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get the object on which the click is performed
                CarDetails selectedCar = cars.get(position);

                //creat a new intent that will take the user to the new activity and will show the image
                Intent showImage = new Intent(context, ShowImage.class);

                //put image resource id, company name and the url of the company in extra to pass thos information to next activity
                showImage.putExtra("image_resource", selectedCar.image_path);
                showImage.putExtra("company_url", selectedCar.url.toString());
                showImage.putExtra("company_name", selectedCar.company_name.toString());

                //start the activity
                startActivity(showImage);
            }

        });
    }

    //define onCreateContextMenu to inflate the menu with the conext_menu defined in menu folder
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //define a menuinflater and inflate the context_menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    //define onContextItemSelected() method to perform the action based on the item selected in the menu by the user
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //get the information of the item user selected
        CarDetails selectedCar = cars.get(info.position);

        //check which item user selected from context menu and start the new activity accordingly
        //for any item selected: create intent, put appropriate extra and start activity

        switch (item.getItemId()) {

            //if user selected to show full image
            case R.id.show_full_image:
                Intent showImage = new Intent(MainActivity.this, ShowImage.class);

                showImage.putExtra("image_resource", selectedCar.image_path);
                showImage.putExtra("company_url", selectedCar.url.toString());
                showImage.putExtra("company_name", selectedCar.company_name.toString());

                startActivity(showImage);

                return true;

            //if user selected to go to website of car manufacturer
            case R.id.website_link:
                Intent goToWeb = new Intent(MainActivity.this, TakeToWeb.class);

                goToWeb.putExtra("url", selectedCar.url.toString());
                goToWeb.putExtra("company_name", selectedCar.company_name.toString());

                startActivity(goToWeb);

                return true;

            //if user selected to see list of dealers
            case R.id.dealers:
                Intent dealers_list = new Intent(MainActivity.this, Dealers.class);

                dealers_list.putExtra("index", info.position);
                dealers_list.putExtra("company_name", selectedCar.company_name.toString());

                startActivity(dealers_list);

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
