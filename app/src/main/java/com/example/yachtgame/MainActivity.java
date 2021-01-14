package com.example.yachtgame;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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
    private ScoreTable scoreTable;
    private int fillScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRoll = findViewById(R.id.btnRoll);
        rollTextView = findViewById(R.id.tvRollCount);
        rollTextView.setText("Roll Count : 0 / 3");
        btnReset = findViewById(R.id.btnReset);

//         Dice(주사위 그룹) 클래스 객체 생성
        dices = new Dices(getApplicationContext());
        for (int i = 0; i < Dices.diceNumber; i++) {
            int id = getResources().getIdentifier("dice" + (i + 1), "id", "com.example.yachtgame");
            dices.addDice(i, findViewById(id));
        }

//         ScoreTable(점수판) 객체 생성
        TextView[] scoreViews = new TextView[ScoreTable.scoreNum];
        for (int i = 0; i < ScoreTable.scoreNum; i++) {
            int id = getResources().getIdentifier("score" + (i + 1), "id", "com.example.yachtgame");
            scoreViews[i] = findViewById(id);
        }
        scoreTable = new ScoreTable(scoreViews);

//         주사위 초기 위치, 움직이는 거리 계산
        ImageView dice = findViewById(R.id.dice1);
        dice.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                dice.getViewTreeObserver().removeOnPreDrawListener(this);
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                Dices.beginDiceY = findViewById(R.id.dice1).getY();
                Log.d("Dice Begin Y", "" + Dices.beginDiceY);
                Dices.moveDiceY = (float) (metrics.heightPixels * 0.1);
                return true;
            }
        });
    }

//     점수판 클릭(점수 입력)
    public void onClickScore(View view) {
//        비어있을 때만 작동
        TextView textView = (TextView) view;
        if (textView.getText().equals("")) {
            int score = scoreTable.calcScore((TextView) view, dices.getDiceValues());
            textView.setText("" + score);
            fillScore += 1;

            int subScore = scoreTable.getSubScore();
            int totalScore = scoreTable.getTotalScore() + subScore;
            if (subScore >= 63) {
                totalScore += 35;
            }
            ((TextView) findViewById(R.id.subScore)).setText("" + subScore);
            ((TextView) findViewById(R.id.totalScore)).setText("" + totalScore);

            rollTextView.setText("Roll Count : 0 / 3");
            dices.resetDices();
            dices.dicesClickable(false);
//        점수판 다 채웠는지 확인
            if (fillScore < 12) {
                dices.resetRollCount();
            } else {
                btnReset.setVisibility(View.VISIBLE);
                btnRoll.setClickable(false);
            }
        }
    }

//     주사위 클릭(킵 설정)
    public void onClickDice(View view) {
        dices.keepDice((ImageView) view);
    }

//    주사위 굴리기
    public void rollDices(View view) {
        int rollCount = dices.rollDice();
        rollTextView.setText("Roll Count : " + rollCount + " / 3");
        scoreTable.scoresClickable(true);
    }

//     게임 리셋 버튼 클릭
    public void resetGame(View view) {
        dices.resetDices();
        dices.resetRollCount();
        scoreTable.resetScoreViews();
        ((TextView)findViewById(R.id.subScore)).setText("");
        ((TextView)findViewById(R.id.totalScore)).setText("");
        fillScore = 0;

        btnReset.setVisibility(View.INVISIBLE);
        btnRoll.setClickable(true);
    }

//     전체화면 모드
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