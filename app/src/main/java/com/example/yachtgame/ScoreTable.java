package com.example.yachtgame;

import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class ScoreTable {
    TextView[] scoreViews;
    public static int SCORE_NUM = 12;

//    public ScoreTable(TextView[] scoreViews) {
//        this.scoreViews = scoreViews;
//    }

    public ScoreTable() {
    }

    //     해당하는 칸 점수 계산
    public int[] calcScore(int[] values) {
        int[] scores = new int[12];

//        Ones ~ Sixes (selected number * count)
        for (int i = 0; i < 6; i++) {
            for (int value : values) {
                if (value == i + 1) {
                    scores[i] += i + 1;
                }
            }
        }
        Arrays.sort(values);

//            Choice (sum values)
        for (int value : values) {
            scores[6] += value;
        }

//          4 of a Kind (sum values)
        if (values[0] == values[3] || values[1] == values[4]) {
            scores[7] = values[0] + values[1] * 3 + values[4];
        }

//            Full House (sum values)
        if (values[0] == values[1] && values[3] == values[4] && values[0] != values[4]) {
            if (values[2] == values[1] || values[2] == values[3]) {
                scores[8] = values[0] * 2 + values[2] + values[4] * 2;
            }
        }

//            Small Straight (15)
        int count = 0;
        for (int i = 0; i < values.length - 1; i++) {
            if (values[i] + 1 == values[i + 1]) {
                count += 1;
            }
        }
        if (count >= 3) {
            scores[9] = 15;
        }

//            Large Straight (30)
        for (int i = 0; i < values.length - 1; i++) {
            if (values[i] + 1 != values[i + 1]) {
                scores[10] = 0;
                break;
            }
            scores[10] = 30;
        }

//            Yacht (50)
        int temp = values[0];
        for (int i = 0; i < values.length && temp == values[i]; i++) {
            if (i == 4) {
                scores[11] = 50;
                break;
            }
        }
//        }

        return scores;
    }

    //    서브 점수 계산 (>=63 이면 총점 +35점)
    public int getSubScore() {
        int subScore = 0;
        for (TextView view : Arrays.copyOf(scoreViews, 6)) {
            if (view.getCurrentTextColor() == MainActivity.DARK_TEXT_COLOR) {
                String str = (String) view.getText();
                subScore += Integer.parseInt(str);
            }
        }

        return subScore;
    }

    //    서브 점수 계산 (>=63 이면 총점 +35점)
    public int getSubScore(int[] scores) {
        int subScore = 0;
        for (int score : Arrays.copyOf(scores, 6)) {
            subScore += score;
        }

        return subScore;
    }

    //    총점 계산
    public int getTotalScore() {
        int totalScore = 0;
        for (TextView view : Arrays.copyOfRange(scoreViews, 6, 12)) {
            if (view.getCurrentTextColor() == MainActivity.DARK_TEXT_COLOR) {
                String str = (String) view.getText();
                totalScore += Integer.parseInt(str);
            }
        }

        return totalScore;
    }

    //    총점 계산
    public int getTotalScore(int[] scores) {
        int totalScore = 0;
        for (int score : Arrays.copyOfRange(scores, 6, 12)) {
            totalScore += score;
        }

        return totalScore;
    }
}
