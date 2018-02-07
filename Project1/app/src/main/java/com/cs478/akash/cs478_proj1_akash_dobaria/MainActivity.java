package com.cs478.akash.cs478_proj1_akash_dobaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //button to go to next activity
    protected Button next_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next_activity = (Button) findViewById(R.id.btn_main_act);

        // listener on the button to go on next activity
        next_activity.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contact_activity = new Intent(MainActivity.this, add_contact.class);
                startActivity(contact_activity);
            }
        }));


    }
}
