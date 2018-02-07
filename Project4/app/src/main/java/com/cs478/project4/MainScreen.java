package com.cs478.project4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    Button playerVsBot;
    Button playerVsPlayer;
    Button botVsbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        playerVsBot = (Button) findViewById(R.id.button_1player);
        playerVsPlayer = (Button) findViewById(R.id.button_2player);
        botVsbot = (Button) findViewById(R.id.button_Bots);

        playerVsBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayerVsBot.class);
                startActivity(intent);
            }
        });

        playerVsPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayerVsPlayer.class);
                startActivity(intent);
            }
        });

        botVsbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
