package com.example.yachtgame;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

public class Dices {
    //주사위 각각의 정보
    class DiceInfo {
        int value; //주사위 눈 값
        Boolean keep; //킵하는지 저장
        int id;
        ImageView imageView;

        public DiceInfo(ImageView imageView) {
            this.imageView = imageView;
            id = imageView.getId();
            value = 1;
            keep = false;
        }
    }

    Context context;

    final Animation anim1;
    final Animation anim2;

    protected static final int diceNumber = 5;
    private int rollCount = 0;
    // 주사위 값 저장
    static int[] value;
    DiceInfo dice[];

    public Dices(Context context) {
        value = new int[diceNumber];

        anim1 = AnimationUtils.loadAnimation(context, R.anim.rotate);
        anim2 = AnimationUtils.loadAnimation(context, R.anim.rotate);
        this.context = context;
        // 메인에 다이스 뷰 등록
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dice = new DiceInfo[diceNumber];
    }

    // 주사위 5개 정보 객체 생성
    public void addDice(int i, ImageView imageView) {
        dice[i] = new DiceInfo(imageView);
    }

    public void rollDice() {
        int i;
        for (i = 0; i < diceNumber; i++) {
            if (dice[i].keep == false) {
                Random rand = new Random();
                int r = rand.nextInt(6) + 1;
                Handler delayHandler = new Handler();

                dice[i].imageView.startAnimation(anim1);
                int finalI = i;
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dice[finalI].imageView.startAnimation(anim2);
                    }
                }, 250);
                value[i] = r;
                dice[i].imageView.setImageResource(context.getResources().getIdentifier("dice" + r, "drawable", "com.example.yachtgame"));
            }
        }
//        if (rollCount == 3) {
//            imageView.setY(870);
//            dice[i].keep = false;
//        }
    }

    //주사위 킵
    public void keepDice(ImageView diceView) {
        DiceInfo d = getDice(diceView.getId());
        if (d.keep == true) {
            d.keep = false;
            diceView.setY(870);
        } else {
            d.keep = true;
            diceView.setY(180);
        }
    }

    //주사위 값 반환
    public int[] getDiceValues() {
        return value;
    }

    // 객체 id로 주사위 찾기
    public DiceInfo getDice(int id) {
        for (int i = 0; i < diceNumber; i++) {
            if (dice[i].id == id) {
                return dice[i];
            }
        }
        // 찾기 오류
        return new DiceInfo(null);
    }

    public void addRollCount() {
        rollCount += 1;
    }

    public void resetRollCount() {
        rollCount = 0;
    }
}
