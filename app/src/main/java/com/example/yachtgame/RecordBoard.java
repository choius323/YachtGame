package com.example.yachtgame;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecordBoard extends AppCompatActivity {

    //    점수 저장용 클래스
    static class Record {
        int id;
        String name;
        int score;

        public Record(int id, String name, int score) {
            this.id = id;
            this.name = name;
            this.score = score;
        }
    }

    ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_board);
        doFullScreen();

//        db 객체 생성
        DBHelper helper = new DBHelper(this, "UserScore.db", null, 1);
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            db = helper.getReadableDatabase();
        }

//        DB 데이터 불러오기
        Cursor cursor = db.rawQuery("select * from ScoreBoard", null);
        records = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(DBHelper.ID_COLUMN);
            String name = cursor.getString(DBHelper.NAME_COLUMN);
            int score = cursor.getInt(DBHelper.SCORE_COLUMN);
            records.add(new Record(id, name, score));
        }

//        ListView 데이터 설정
        RecordListViewAdapter adapter = new RecordListViewAdapter(records, getApplicationContext());
        ListView recordListView = findViewById(R.id.recordListView);
        recordListView.setAdapter(adapter);
    }

    public void exitGame(View view) {
        finish();
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