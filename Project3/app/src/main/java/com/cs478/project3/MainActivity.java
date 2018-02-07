package com.cs478.project3;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;

public class MainActivity extends AppCompatActivity implements LandmarkList.ListSelectionListener{

    //declare all objects, variables needed
    public static String[] landmarksArray;
    public static String[] weblinkArray;

    private FragmentManager fragmentManager;

    private FrameLayout titleFragmentLayout;
    private FrameLayout webviewFrameLayout;

    private LandmarkWebsite webviewFragment = new LandmarkWebsite();
    private LandmarkList titlesFragment = new LandmarkList();

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    private static final String TAG_LANDMARK_LIST_FRAGMENT = "LandmarkList";
    private static final String TAG_LANDMARK_WEBSITE_FRAGMENT = "LandmarkWeb";

    private static final String MY_PERMISSION = "com.cs478.project3";
    private static final String GALLERY_ACTION = "com.cs478.project3.gallery";
    private static final int REQUEST_PERMISSION_GALLERY = 0;
    private static final String INTERNET_PERMISSION = "android.permission.INTERNET";
    private static final int REQUEST_PERMISSION_INTERNET = 1;

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        // Get the values from the arrays
        landmarksArray = getResources().getStringArray(R.array.Landmarks);
        weblinkArray = getResources().getStringArray(R.array.Websites);

        // Get the containers for the fragments
        titleFragmentLayout = (FrameLayout) findViewById(R.id.landmark_fragment_container);
        webviewFrameLayout = (FrameLayout) findViewById(R.id.website_fragment_container);

        // Get fragment manager
        fragmentManager = getFragmentManager();

        // Get fragments transaction to avoid multiple local declaration when needed
        FragmentTransaction mTransaction = fragmentManager.beginTransaction();

        // get the fragments if already exists (after configuration change)
        titlesFragment = (LandmarkList) fragmentManager.findFragmentByTag(TAG_LANDMARK_LIST_FRAGMENT);
        webviewFragment = (LandmarkWebsite) fragmentManager.findFragmentByTag(TAG_LANDMARK_WEBSITE_FRAGMENT) ;

        // Check if the fragment was retained. If not, create the fragment and add to respective container.
        if (titlesFragment == null){

            titlesFragment = new LandmarkList();
            mTransaction.replace(R.id.landmark_fragment_container, titlesFragment,TAG_LANDMARK_LIST_FRAGMENT);
            mTransaction.commit();

        }
        else{

            // if fragment was retained, add in the respective container to display the fragment.
            mTransaction.replace(R.id.landmark_fragment_container, titlesFragment, TAG_LANDMARK_LIST_FRAGMENT);
            mTransaction.commit();

            // check if the webview fragment was also retained.
            if (webviewFragment == null) {

                webviewFragment = new LandmarkWebsite();

            }
            else{
                //if retained, add the fragment to display
                if (!webviewFragment.isAdded()) {

                    mTransaction.replace(R.id.website_fragment_container, webviewFragment,TAG_LANDMARK_WEBSITE_FRAGMENT);
                    mTransaction.addToBackStack(null);
                    mTransaction.commit();
                    fragmentManager.executePendingTransactions();

                }
            }
        }

        //if webview fragment was not created, create.
        if(webviewFragment == null){
            webviewFragment = new LandmarkWebsite();
        }

        // OnBackStackChangedListener to change the layout whenever backstack is changed
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {

        //check if the device is in portrait mode or landscape mode
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            // Check if webview fragment was added and change the layout accordingly
            if (!webviewFragment.isAdded()) {

                //if the webview fragment is not added set title bar to default project name
                setActionBarTitle("Project3");

                //hide webview container and show titles fragment container to cover the screen
                titleFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
                webviewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
            }
            else {

                //webview fragment is added --> show webview container to cover full screen, hide title fragment container
                titleFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
                webviewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            }
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            // Check if webview fragment was added and change the layout accordingly
            if (!webviewFragment.isAdded()) {

                //if the webview fragment is not added set title bar to default project name
                setActionBarTitle("Project3");

                //hide webview container and show titles fragment container to cover the screen
                titleFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
                webviewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT));
            }
            else {

                //webview fragment is added --> show webview container to cover 2/3 screen, 1/3 title fragment container
                titleFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT, 1f));
                webviewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT, 2f));

            }
        }

    }

    //function to update title on the action bar
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    //listener to update webview fragment on user selection of item from the title fragment
    @Override
    public void onListSelection(int index) {

        //check if webview was not added, create it
        if (webviewFragment == null || !webviewFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Add the QuoteFragment to the layout
            fragmentTransaction.replace(R.id.website_fragment_container, webviewFragment,TAG_LANDMARK_WEBSITE_FRAGMENT);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            fragmentManager.executePendingTransactions();
        }

        if (webviewFragment.getShownIndex() != index) {

            // Call showWebView() function to show webpage in webview fragment
            webviewFragment.showWebViewIndex(index);

        }

    }

    public void onStart() {
        super.onStart();

        //set layout and show webview when fragments were retained after configuration change
        setLayout();
        webviewFragment.showWebViewIndex(titlesFragment.mCurrIdx);
    }

    // Create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        return true;
    }

    // Listener for options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //check which item was clicked from options menu and execute respective process
        switch (item.getItemId()) {

            //if exit option was clicked
            case R.id.exit:
                //finish();
                System.exit(1);
                return true;

            //if launching the second application option was selected
            case R.id.launchApp:

                //check for the declared permission was granted. If not request the permission.
                if (ContextCompat.checkSelfPermission(context, MY_PERMISSION) !=  PackageManager.PERMISSION_GRANTED) {
                    // Request permission
                    ActivityCompat.requestPermissions((Activity)context, new String[]{MY_PERMISSION}, REQUEST_PERMISSION_GALLERY);
                }
                else{
                    //if application has the permission, send broadcast to launch 2nd app
                    Intent intent = new Intent();
                    intent.setAction(GALLERY_ACTION);
                    intent.setFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    sendOrderedBroadcast(intent, "com.cs478.project3");
                }
                return true;

            default:
                return false;
        }

    }

    //check if permission was granted by user when prompted.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

       if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // if permission was granted, send the broadcast
            if(requestCode == REQUEST_PERMISSION_GALLERY) {
                Intent intent = new Intent(GALLERY_ACTION);
                intent.setFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                sendOrderedBroadcast(intent, null);
            }
            if(requestCode == REQUEST_PERMISSION_INTERNET){
                Toast.makeText(context, "Internet permission granted by user", Toast.LENGTH_LONG);
            }
        }

        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
            if(requestCode == REQUEST_PERMISSION_GALLERY) {
                Toast.makeText(context, "Permission not granted by user", Toast.LENGTH_LONG);
            }
            if(requestCode == REQUEST_PERMISSION_INTERNET){
                Toast.makeText(context, "Internet permission is not granted by user", Toast.LENGTH_LONG);
            }
        }

    }
}
