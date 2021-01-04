package com.example.yachtgame;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

public class Dices {

    //주사위 각각의 정보
    class DiceInfo extends MainActivity{
        int value = 1; //주사위 눈 값
        Boolean keep = false; //킵하는지 저장
        int id;

        public DiceInfo(int id) {
            this.id = id;
            value = 1;
            keep = false;
        }
    }

    Context context;

    final Animation anim1;
    final Animation anim2;

    private final int diceNumber = 5;
    public int rollCount = 0;
    static int[] value;
    DiceInfo dice[];

    public Dices(Context context, AttributeSet attrs, int defStyleAttrs) {
        value = new int[diceNumber];

        anim1 = AnimationUtils.loadAnimation(context, R.anim.rotate);
        anim2 = AnimationUtils.loadAnimation(context, R.anim.rotate);
        this.context = context;
        // 메인에 다이스 뷰 등록
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //주사위 5개 객체 생성
        dice = new DiceInfo[diceNumber];
        int id = 0;
        for (int i = 0; i < diceNumber; i++) {
            id = context.getResources().getIdentifier("dice" + (i + 1), "id", "com.example.yachtgame");
            dice[i] = new DiceInfo(id);
        }
    }

    //주사위 굴리기
    public void rollDice(final ImageView imageView, int i) {
        if (dice[i].keep == false) {
            Random rand = new Random();
            int r = rand.nextInt(6) + 1;
            Handler delayHandler = new Handler();

            imageView.startAnimation(anim1);
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageView.startAnimation(anim2);
                }
            }, 250);
            value[i] = r;
            imageView.setImageResource(context.getResources().getIdentifier("dice" + r, "drawable", "com.example.yachtgame"));
        }
        if (rollCount == 3) {
            imageView.setY(870);
            dice[i].keep = false;
        }
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
        return new DiceInfo(0);
    }
}
