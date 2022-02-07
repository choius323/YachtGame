package com.example.yachtgame;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ScoreTableTest {

    int[] scores;
    private ScoreTable scoreTable;

    @org.junit.Before
    public void setUp() throws Exception {
        scoreTable = new ScoreTable();
    }

    @org.junit.Test
    public void calcScore() {
        int[] answerScores = new int[]{5, 0, 0, 0, 0, 0, 5, 5, 0, 0, 0, 50};
        int[] result = scoreTable.calcScore(new int[]{1, 1, 1, 1, 1});
        assertArrayEquals(answerScores, result);
//        assertEquals(answerScores, result);

        answerScores = new int[]{0, 0, 6, 4, 5, 6, 21, 0, 0, 15, 0, 0};
        result = scoreTable.calcScore(new int[]{3, 3, 5, 6, 4});
        assertArrayEquals(answerScores, result);

        answerScores = new int[]{0, 4, 3, 4, 0, 6, 17, 0, 0, 0, 0, 0};
        result = scoreTable.calcScore(new int[]{6, 4, 2, 2, 3});
        assertArrayEquals(answerScores, result);

        answerScores = new int[]{1, 2, 3, 4, 5, 0, 15, 0, 0, 15, 30, 0};
        result = scoreTable.calcScore(new int[]{5, 4, 1, 2, 3});
        assertArrayEquals(answerScores, result);

        answerScores = new int[]{1, 0, 0, 16, 0, 0, 17, 17, 0, 0, 0, 0};
        result = scoreTable.calcScore(new int[]{4, 4, 1, 4, 4});
        assertArrayEquals(answerScores, result);

        answerScores = new int[]{0, 4, 0, 12, 0, 0, 16, 0, 16, 0, 0, 0};
        result = scoreTable.calcScore(new int[]{4, 2, 4, 2, 4});
        assertArrayEquals(answerScores, result);

        answerScores = new int[]{0, 2, 6, 4, 5, 0, 17, 0, 0, 15, 0, 0};
        result = scoreTable.calcScore(new int[]{2, 3, 5, 3, 4});
        assertArrayEquals(answerScores, result);

        answerScores = new int[]{1, 2, 0, 0, 10, 6, 19, 0, 0, 0, 0, 0};
        result = scoreTable.calcScore(new int[]{2, 5, 5, 1, 6});
        assertArrayEquals(answerScores, result);
    }

    @org.junit.Test
    public void getSubScore() {
        int[] scores = new int[]{1, 4, 6, 4, 10, 18, 15, 0, 17, 15, 30, 0};
        int expected = 43;
        int result = scoreTable.getSubScore(scores);
        assertEquals(expected, result);

        scores = new int[]{2, 6, 9, 8, 20, 18, 15, 5, 17, 0, 30, 50};
        expected = 63;
        result = scoreTable.getSubScore(scores);
        assertEquals(expected, result);

        scores = new int[]{2, 6, 12, 4, 20, 24, 0, 0, 17, 15, 0, 0};
        expected = 68;
        result = scoreTable.getSubScore(scores);
        assertEquals(expected, result);
    }

    @org.junit.Test
    public void getTotalScore() {
        int[] scores = new int[]{1, 4, 6, 4, 10, 18, 15, 0, 17, 15, 30, 0};
        int expected = 77;
        int result = scoreTable.getTotalScore(scores);
        assertEquals(expected, result);

        scores = new int[]{2, 6, 9, 8, 20, 18, 15, 5, 17, 0, 30, 50};
        expected = 117;
        result = scoreTable.getTotalScore(scores);
        assertEquals(expected, result);

        scores = new int[]{2, 6, 12, 4, 20, 24, 0, 0, 17, 15, 0, 0};
        expected = 32;
        result = scoreTable.getTotalScore(scores);
        assertEquals(expected, result);
    }
}