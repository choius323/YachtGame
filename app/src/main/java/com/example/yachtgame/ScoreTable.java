package com.example.yachtgame;

import android.widget.TextView;

import java.util.Arrays;

public class ScoreTable {
    TextView[] scoreViews;
    public static int scoreNum = 12;

    public ScoreTable(TextView[] scoreViews) {
        this.scoreViews = scoreViews;
        scoresClickable(false);
    }

//     해당하는 칸 점수 계산
    public int calcScore(TextView selectedView, int[] values) {
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

        if (scoreViews[6] == selectedView) {
//            Choice (sum values)
            for (int value : values) {
                sum += value;
            }

        } else if (scoreViews[7] == selectedView) {
//            4 of a Kind (sum values)
            if (values[0] == values[3] || values[1] == values[4]) {
                sum = values[0] + values[1] * 3 + values[4];
            }

        } else if (scoreViews[8] == selectedView) {
//            Full House (sum values)
            if (values[0] == values[1] && values[3] == values[4] && values[0] != values[4]) {
                if (values[2] == values[1] || values[2] == values[3]) {
                    sum = values[0] * 2 + values[2] + values[4] * 2;
                }
            }

        } else if (scoreViews[9] == selectedView) {
//            Small Straight (15)
            int count = 0;
            for (int i = 0; i < values.length - 1; i++) {
                if (values[i] + 1 == values[i + 1]) {
                    count += 1;
                }
            }
            if (count >= 3) {
                sum = 15;
            }

        } else if (scoreViews[10] == selectedView) {
//            Large Straight (30)
            for (int i = 0; i < values.length - 1; i++) {
                if (values[i] + 1 != values[i + 1]) {
                    sum = 0;
                    break;
                }
                sum = 30;
            }

        } else if (scoreViews[11] == selectedView) {
//            Yacht (50)
            int temp = values[0];
            for (int i = 0; i < values.length && temp == values[i]; i++) {
                if (i == 4) {
                    sum = 50;
                    break;
                }
            }
        }

        scoresClickable(false);
        return sum;
    }

//    서브 점수 계산 (>=63 이면 총점 +35점)
    public int getSubScore() {
        int subScore = 0;
        for (TextView view : Arrays.copyOf(scoreViews, 6)) {
            String str = (String) view.getText();
            if (str.equals("")) {
                subScore += 0;
            } else {
                subScore += Integer.parseInt(str);
            }
        }

        return subScore;
    }

//    총점 계산
    public int getTotalScore() {
        int totalScore = 0;
        for(TextView view : Arrays.copyOfRange(scoreViews, 6, 12)){
            String str = (String) view.getText();
            if (str.equals("")) {
                totalScore += 0;
            } else {
                totalScore += Integer.parseInt(str);
            }
        }

        return totalScore;
    }

    public void scoresClickable(boolean clickable){
        for(TextView s : scoreViews){
            s.setClickable(clickable);
        }
    }

    public void resetScoreViews(){
        for(TextView s : scoreViews){
            s.setText("");
        }
        scoresClickable(false);
    }
}
