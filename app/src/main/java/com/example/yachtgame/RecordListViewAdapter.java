package com.example.yachtgame;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordListViewAdapter extends BaseAdapter {
    ArrayList<RecordBoard.Record> records;
    LayoutInflater inflater;
    Context context;

    public RecordListViewAdapter(ArrayList<RecordBoard.Record> records, Context context) {
        this.records = records;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // 생성된 적 없는 convertView 는 null 반환
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_record_list_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvId.setText(Integer.toString(records.get(position).id));
        holder.tvName.setText((records.get(position).name));
        holder.tvScore.setText(Integer.toString((records.get(position).score)));

        return convertView;
    }

    static class ViewHolder {
        private final TextView tvId;
        private final TextView tvName;
        private final TextView tvScore;

        public ViewHolder(View convertView) {
            this.tvId = (TextView) convertView.findViewById(R.id.idTextView);
            this.tvName = (TextView) convertView.findViewById(R.id.nameTextView);
            this.tvScore = (TextView) convertView.findViewById(R.id.scoreTextView);
        }
    }

}
