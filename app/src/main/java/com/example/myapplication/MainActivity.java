package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;


public class MainActivity extends AppCompatActivity {

    private Button button, button2, button3;
    private final int[] buttonIds = {R.id.button, R.id.button2, R.id.button3};
    private int currentLevel = 1;
    private static final int MAX_LEVEL = 50;
    private int[] currentSequence;
    private int[] expectedSequence = currentSequence;
    private int currentIndex = 0;
    private TextView levelTextView;

    private boolean isPaused = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        levelTextView = findViewById(R.id.levelTextView);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        updateLevelText();
        generateSequence();
        expectedSequence = currentSequence;
        resetColors();


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleButtonClick((Button) v);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleButtonClick((Button) v);
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleButtonClick((Button) v);
            }
        });

    }

    private void resetColors(){
        button.setBackgroundColor(getResources().getColor(R.color.default_button_color));
        button2.setBackgroundColor(getResources().getColor(R.color.default_button_color));
        button3.setBackgroundColor(getResources().getColor(R.color.default_button_color));
    }

    private void updateLevelText(){
        levelTextView.setText("LEVEL " + currentLevel);
    }

    private void generateSequence() {
        currentSequence = new int[3]; // Size of the array will be 3
        Random random = new Random();
        StringBuilder sequenceLog = new StringBuilder("Sequence for Level ").append(currentLevel).append(": ");
        int[] buttonNumbers = {R.id.button, R.id.button2, R.id.button3}; // Array representing the button numbers

        // Shuffle the button numbers array to create a random sequence
        for (int i = 0; i < buttonNumbers.length; i++) {
            int index = random.nextInt(buttonNumbers.length);
            int temp = buttonNumbers[index];
            buttonNumbers[index] = buttonNumbers[i];
            buttonNumbers[i] = temp;
        }

        // Store the sequence and append it to the log
        for (int i = 0; i < 3; i++) {
            currentSequence[i] = buttonNumbers[i];
            sequenceLog.append(buttonNumbers[i]).append(" ");
        }
        Log.d("Sequence", sequenceLog.toString());
        expectedSequence = currentSequence;
    }

    private void randomButtonMovements(Button clickedButton) {
        Random r = new Random();
        int maxX = findViewById(android.R.id.content).getWidth() - clickedButton.getWidth();
        int maxY = findViewById(android.R.id.content).getHeight() - clickedButton.getHeight();

        int newX = r.nextInt(maxX);
        int newY = r.nextInt(maxY);

        clickedButton.setX(newX);
        clickedButton.setY(newY);
    }

    private void handleButtonClick(Button clickedButton){
        int expectedButtonId = expectedSequence[currentIndex];
        int clickedButtonId = clickedButton.getId();
        Log.d("Button IDs", "Clicked Button ID: " + clickedButtonId + ", Expected Button ID: " + expectedButtonId);
        if (clickedButtonId == expectedButtonId) {
            randomButtonMovements(clickedButton);
            Log.d("correct", "correct button clicked!");
            clickedButton.setBackgroundColor(getResources().getColor(R.color.correct_button_color)); // Set background color to green
            currentIndex++;
            if (currentIndex == expectedSequence.length) {
                currentLevel++;
                resetColors();
                updateLevelText();
                generateSequence();
                currentIndex = 0;

            }
        } else {
            currentIndex = 0;
            Log.d("incorrect", "incorrect button clicked");
            clickedButton.setBackgroundColor(getResources().getColor(R.color.incorrect_button_color));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetColors();
                }
            },
            300);
        }
    }

}