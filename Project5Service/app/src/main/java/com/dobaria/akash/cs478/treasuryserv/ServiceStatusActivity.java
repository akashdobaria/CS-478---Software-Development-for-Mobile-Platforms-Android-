package com.dobaria.akash.cs478.treasuryserv;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ServiceStatusActivity extends AppCompatActivity {

    private static final String TAG = "TreasuryServe";
    TextView textView_ServiceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status);

        //get the textview
        textView_ServiceStatus = (TextView) findViewById(R.id.textView_ServiceStatus);

        //get sharedpreferences to read values
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //listener to update status when value is changed
        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                String status = sharedPreferences.getString(TAG, "Not Created !");
                textView_ServiceStatus.setText("Service status : "+status);
            }
        });
    }

}
