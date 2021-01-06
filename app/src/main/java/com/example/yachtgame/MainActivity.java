package com.example.yachtgame;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnRoll;
    private ImageButton btnReset;
    private TextView rollTextView;
    private Dices dices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRoll = findViewById(R.id.btnRoll);
        rollTextView = findViewById(R.id.tvRollCount);
        btnReset = findViewById(R.id.btnReset);

        dices = new Dices(getApplicationContext());
        for (int i = 0; i < Dices.diceNumber; i++) {
            int id = getResources().getIdentifier("dice" + (i + 1), "id", "com.example.yachtgame");
            dices.addDice(i, findViewById(id));
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Dices.beginDiceY = findViewById(R.id.dice1).getY();
        Dices.moveDiceY = (float) (metrics.heightPixels * 0.1);
    }

    // 점수판 클릭(점수 입력)
    public void onClickScore(View view) {
        dices.resetRollCount();
    }

    // 주사위 클릭(킵 설정)
    public void onClickDice(View view) {
        dices.keepDice((ImageView) view);
    }

    public void rollDices(View view) {
        int rollCount = dices.rollDice();
        rollTextView.setText(rollCount + " / 3");
    }

    // 게임 리셋 버튼 클릭
    public void resetGame(View view) {
        // 주사위 초기화
        dices = new Dices(getApplicationContext());
        // 스코어 보드 초기화
    }

    // 전체화면 모드
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
}