package com.cs478.akash.cs478_proj2_akash_dobaria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/******************************************************************************
 * Activity to show the website when user taps on the full image of the car   *
 ******************************************************************************/

public class TakeToWeb extends AppCompatActivity {

    //declare the webview needed for the activity
    WebView webContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the view for the activity
        setContentView(R.layout.content_take_to_web);

        //get the extras received
        String company_url = this.getIntent().getExtras().getString("url");
        String company_name = this.getIntent().getExtras().getString("company_name");

        //set company name as title on the action bar
        setTitle(company_name);

        // get the webcontainer
        webContainer = (WebView) findViewById(R.id.web_browser);

        Toast.makeText(this, "Loading... Please Wait", Toast.LENGTH_SHORT).show();

        // enable webview to load javascript as well
        // reference https://developer.android.com/guide/webapps/webview.html
        WebSettings webSettings = webContainer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebInterface new_web_interface = new WebInterface();
        webContainer.addJavascriptInterface(new_web_interface,"WebActivity");
        webContainer.setWebViewClient(new WebViewClient());
        webContainer.loadUrl(company_url);
    }

    //override onKeyDown() to provide back functionality in webview
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == android.view.KeyEvent.KEYCODE_BACK) && webContainer.canGoBack()) {
            webContainer.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
