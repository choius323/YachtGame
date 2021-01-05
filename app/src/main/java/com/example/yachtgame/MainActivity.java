package com.example.yachtgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnRoll;
    private ImageButton btnReset;
    private Dices dices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dices = new Dices(getApplicationContext());
        for(int i=0;i<Dices.diceNumber;i++) {
            int id = getResources().getIdentifier("dice" + (i + 1), "id", "com.example.yachtgame");
            dices.addDice(i, findViewById(id));
        }

        btnRoll =  findViewById(R.id.btnRoll);
        btnReset =  findViewById(R.id.btnReset);
    }

    // 하단바 제거
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    // 점수판 클릭(점수 입력)
    public void onClickScore(View view) {
        
    }

    // 주사위 클릭(킵 설정)
    public void onClickDice(View view) {

    }

    public void rollDices(View view) {
        dices.rollDice();
    }

    public void resetGame(View view){

    }
}