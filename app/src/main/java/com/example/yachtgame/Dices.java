package com.example.yachtgame;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

public class Dices {
//    주사위 각각의 정보
    static class DiceInfo {
//        주사위 눈 값
        int value;
//        킵하는지 저장
        Boolean keep;
        int id;
        ImageView imageView;

        public DiceInfo(ImageView imageView) {
            this.imageView = imageView;
            id = imageView.getId();
            value = 1;
            keep = false;
            imageView.setImageResource(R.drawable.dice1);
        }
    }

    Context context;
//     굴리기 애니메이션
    final Animation anim1;
//     주사위 뷰 시작위치, 이동 거리
    static float beginDiceY, moveDiceY;
//     주사위 갯수
    protected static final int diceNumber = 5;
//     굴리는 횟수
    private int rollCount = 0;
//     주사위 값 저장
    static int[] values;
//     주사위 5개의 정보
    DiceInfo[] dice;

    public Dices(Context context) {
        values = new int[diceNumber];
        anim1 = AnimationUtils.loadAnimation(context, R.anim.roll);
        this.context = context;
        dice = new DiceInfo[diceNumber];
    }

//     주사위 5개 정보 객체 생성
    public void addDice(int i, ImageView imageView) {
        dice[i] = new DiceInfo(imageView);
//        시작할 때 주사위 킵 불가
        dice[i].imageView.setClickable(false);
    }

    public int rollDice() {
        if (rollCount < 3){ // 테스트에서 임시 주석
        for (int i = 0; i < diceNumber; i++) {
            if (dice[i].keep == false) {
                Random rand = new Random();
                int r = rand.nextInt(6) + 1;

                dice[i].value = r;
                dice[i].imageView.startAnimation(anim1);
                dice[i].imageView.setImageResource(context.getResources().getIdentifier("dice" + r, "drawable", "com.example.yachtgame"));
            }
            values[i] = dice[i].value;
        }
        rollCount += 1;
//        주사위 굴리는 기회 끝
            if (rollCount == 3) { // 테스트에서 임시 주석
                allDicesSetKeep();
                dicesClickable(false);
            } else {
                dicesClickable(true);
            }
        }

        return rollCount;
    }

//    주사위 킵
    public void keepDice(ImageView diceView) {
        DiceInfo d = getDice(diceView.getId());
        float y = d.imageView.getY();

        if (d.keep == true) {
            d.keep = false;
            d.imageView.setY(y + moveDiceY);
        } else {
            d.keep = true;
            d.imageView.setY(y - moveDiceY);
        }
    }

//    주사위 값 반환
    public int[] getDiceValues() {
        return values;
    }

//     객체 id로 주사위 찾기
    private DiceInfo getDice(int id) {
        for (int i = 0; i < diceNumber; i++) {
            if (dice[i].id == id) {
                return dice[i];
            }
        }
//         찾기 오류
        return new DiceInfo(null);
    }

    public void resetRollCount() {
        rollCount = 0;
    }

    public void allDicesSetKeep() {
        for (int i = 0; i < diceNumber; i++) {
            dice[i].keep = true;
        }
    }

    public void resetDices(){
        for (int i = 0; i < diceNumber; i++) {
            dice[i].keep = false;
            dice[i].imageView.setY(beginDiceY);
        }
    }

    public void dicesClickable(boolean clickable){
        for(DiceInfo d : dice){
            d.imageView.setClickable(clickable);
        }
    }

    public int getRollCount(){
        return rollCount;
    }
}
