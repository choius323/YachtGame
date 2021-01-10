package com.example.yachtgame;

import android.widget.TextView;

import java.util.Arrays;

public class ScoreTable {
    TextView[] scoreViews;
    private int totalScore = 0;
    private int subScore = 0;
    public static int scoreNum = 12;

    public ScoreTable(TextView[] scoreViews) {
        this.scoreViews = scoreViews;
    }

    // 해당하는 칸 점수 계산
    public void calcScore(TextView selectedView, int[] values) {
        subScore = 0;
        totalScore = 0;
        int sum = 0;

//        Ones ~ Sixes (selected number * count)
        for (int i = 0; i < 6; i++) {
            if (scoreViews[i] == selectedView) {
                for (int value : values) {
                    if (value == i + 1) {
                        sum += i + 1;
                    }
                }
                break;
            }
        }
        Arrays.sort(values);

//         Choice (sum values)
        if (scoreViews[6] == selectedView) {
            for (int value : values) {
                sum += value;
            }
//             4 of a Kind (sum values)
        } else if (scoreViews[7] == selectedView) {
            if (values[0] == values[3] || values[1] == values[4]) {
                sum = values[0] + values[1] * 3 + values[4];
            }
//            Full House (sum values)
        } else if (scoreViews[8] == selectedView) {
            if (values[0] == values[1] && values[3] == values[4] && values[0] != values[4]) {
                if (values[2] == values[1] || values[2] == values[3]) {
                    sum = values[0] * 2 + values[2] + values[4] * 2;
                }
            }
//            Small Straight (15)
        } else if (scoreViews[9] == selectedView) {
            int count = 0;
            for (int i = 0; i < values.length - 2; i++) {
                if (values[i] + 1 != values[i + 1]) {
                    if ( count == 0){
                        count += 1;
                    } else {
                        sum = 0;
                        break;
                    }
                }
                sum = 15;
            }
            if (sum != 15) {
                for (int i = 1; i < values.length - 1; i++) {
                    if (values[i] + 1 != values[i + 1]) {
                        if ( count == 0){
                            count += 1;
                        } else {
                            sum = 0;
                            break;
                        }
                    }
                    sum = 15;
                }
            }

//            Large Straight (30)
        } else if (scoreViews[10] == selectedView) {
            for (int i = 0; i < values.length - 1; i++) {
                if (values[i] + 1 != values[i + 1]) {
                    sum = 0;
                    break;
                }
                sum = 30;
            }
//            Yacht (50)
        } else if (scoreViews[11] == selectedView) {
            int temp = values[0];
            for (int i = 0; i < values.length && temp == values[i]; i++) {
                if (i == 4) {
                    sum = 50;
                }
            }
//            Error
        }

        selectedView.setText("" + sum);
    }
}
