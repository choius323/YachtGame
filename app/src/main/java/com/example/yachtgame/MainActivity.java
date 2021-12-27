package com.example.yachtgame;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnRoll;
    private ImageButton btnReset;
    private TextView rollTextView;
    private Dices dices;
    private ScoreTable scoreTable;
    private int fillScore = 0;
    private SQLiteDatabase db;
//    int[] scores = new int[12];

    static int LIGHT_TEXT_COLOR;
    static int DARK_TEXT_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doFullScreen();

        LIGHT_TEXT_COLOR = getResources().getColor(R.color.light_text, this.getTheme());
        DARK_TEXT_COLOR = getResources().getColor(R.color.dark_text, this.getTheme());

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
            scoreViews[i].setTextColor(LIGHT_TEXT_COLOR);
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
                Dices.moveDiceY = (float) (metrics.heightPixels * 0.1);
                return true;
            }
        });

        btnRoll = findViewById(R.id.btnRoll);
        rollTextView = findViewById(R.id.tvRollCount);
        setRollCountText(dices.getRollCount());
        btnReset = findViewById(R.id.btnReset);

//        점수 기록용 db 객체 생성
        DBHelper helper = new DBHelper(this, "UserScore.db", null, 1);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            db = helper.getReadableDatabase();
        }
    }

    public void previewScores() {
        int[] scores = scoreTable.calcScore(dices.getDiceValues());
        Log.i("scores length", String.valueOf(scores.length));
        for (int i = 0; i < scores.length; i++) {
            TextView tv = scoreTable.scoreViews[i];
            Log.i("textView Clickable", String.valueOf(tv.isClickable()));
            if (tv.getCurrentTextColor() == LIGHT_TEXT_COLOR) {
                tv.setText("" + scores[i]);
//                tv.setTextColor(LIGHT_TEXT_COLOR);
            }
        }
    }

    //     점수판 클릭(점수 입력)
    public void onClickScore(View view) {
//        비어있을 때만 작동
        TextView textView = (TextView) view;
        Log.i("view equal", String.valueOf(view == textView));
        if (textView.getCurrentTextColor() == LIGHT_TEXT_COLOR) {
            Log.i("textView Color", String.valueOf(textView.getCurrentTextColor()));
            Log.i("textView Color2", String.valueOf(getResources().getColor(R.color.light_text, this.getTheme())));
//            int[] scores = scoreTable.calcScore(dices.getDiceValues());
//            textView.setText("" + scores);
            textView.setTextColor(DARK_TEXT_COLOR);
            fillScore += 1;

            int subScore = scoreTable.getSubScore();
            int totalScore = scoreTable.getTotalScore() + subScore;
            if (subScore >= 63) {
                totalScore += 35;
            }
            ((TextView) findViewById(R.id.subScore)).setText("" + subScore);
            ((TextView) findViewById(R.id.totalScore)).setText("" + totalScore);

            setRollCountText(dices.resetRollCount());
            Log.i("textView Color3", String.valueOf(textView.getCurrentTextColor()));
            for (TextView tv : scoreTable.scoreViews) {
                Log.i("textView Color", String.valueOf(tv.getCurrentTextColor()));
                if(tv.getCurrentTextColor() == LIGHT_TEXT_COLOR){
                    tv.setText("");
                }
            }

            dices.resetDices();
            dices.dicesClickable(false);
//        점수판 다 채웠는지 확인
            if (fillScore < 12) {
//                setRollCountText(dices.resetRollCount());
            } else {
                endGame(totalScore);
            }
        }
    }

    //     주사위 클릭(킵 설정)
    public void onClickDice(View view) {
        dices.keepDice((ImageView) view);
    }

    //    주사위 굴리기
    public void rollDices(View view) {
        scoreTable.scoresClickable(true);
        int rollCount = dices.rollDice();
        setRollCountText(rollCount);
        previewScores();
    }

    //    모든 칸을 채운 후 게임 끝
    public void endGame(int totalScore) {
        btnReset.setVisibility(View.VISIBLE);
        btnRoll.setClickable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("finish!!").setMessage("Total Score : " + totalScore);
        builder.setCancelable(false);

        EditText userName = new EditText(this);
        userName.setHint("User Name");
        builder.setView(userName);

//        Ok 버튼 클릭
        builder.setPositiveButton("Record", (dialog, which) -> {
//                DB에 데이터 저장
            db.execSQL("Insert Into ScoreBoard (Name, Score) values ('" + userName.getText().toString() + "', '" + totalScore + "')");
        });

//        Cancel 버튼 클릭
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        });

        AlertDialog editDialog = builder.create();
        editDialog.show();
    }

    //     게임 리셋 버튼 클릭
    public void resetGame(View view) {
        dices.resetDices();
        dices.resetRollCount();
        scoreTable.resetScoreViews();
        setRollCountText(dices.getRollCount());
        ((TextView) findViewById(R.id.subScore)).setText("");
        ((TextView) findViewById(R.id.totalScore)).setText("");
        fillScore = 0;

//        btnReset.setVisibility(View.INVISIBLE);
        btnRoll.setClickable(true);
    }

    public void exitGame(View view) {
        finish();
    }

    //    roll Count 텍스트 설정
    public void setRollCountText(int count) {
        rollTextView.setText(String.format(getResources().getString(R.string.rollCountText), count));
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

    private void doFullScreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}