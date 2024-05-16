package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    private Button playButton;
    private Button exitButton;

    private int[] buttonIds = {R.id.playButton, R.id.idiotButton, R.id.exitButton};


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        playButton = findViewById(R.id.playButton);
        exitButton = findViewById(R.id.exitButton);

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startMainActivity(v);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
        }
    });
}

    private void startMainActivity(View view){
        Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
