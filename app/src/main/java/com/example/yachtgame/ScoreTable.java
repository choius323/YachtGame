package com.example.yachtgame;

import android.widget.TextView;

public class ScoreTable {
    TextView[] scoreViews;
    private int totalScore = 0;
    private int subScore = 0;
    public static int scoreNum = 12;

    public ScoreTable(TextView[] scoreViews){
        this.scoreViews = scoreViews;
    }

    // 해당하는 칸 점수 계산
    public void calcScore(TextView view, int[] values){
        subScore = 0;
        totalScore = 0;
        int sum = 0;

        // Ones ~ Sixes
        for(int i = 0; i<6; i++){
            if(scoreViews[i] == view){
                for (int value : values){
                    if (value == i +1){
                        sum += i + 1;
                    }
                }
                break;
            }
        }

        view.setText("" + sum);
    }
}
