package com.jieun.recyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<ListData> list = new ArrayList<ListData>();
    public CustomAdapter(){

    }
    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }


        TextView daytxt = (TextView) convertView.findViewById(R.id.daytxt);
        TextView timetxt = (TextView) convertView.findViewById(R.id.timetxt);
        TextView placetxt = (TextView) convertView.findViewById(R.id.placetxt);

        ListData listViewItem = list.get(position);

        daytxt.setText(listViewItem.getDay());
        timetxt.setText(listViewItem.getTime());
        placetxt.setText(listViewItem.getPlace());

        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position) ;
    }


    public void addItem(String day, String time, String place) {
        ListData item = new ListData();

        item.setDay(day);
        item.setTime(time);
        item.setPlace(place);

        list.add(item);
    }

    public void removeItem(int i){
        list.remove(i);

    }

    public void removeAll(){
        list.clear();
    }

    public String Time(int i){
        return list.get(i).getTime();
    }

    public String Place(int i){
        return list.get(i).getPlace();
    }

    public String Day(int i){
        return list.get(i).getDay();
    }

    public void setListitem(int i, String day, String time, String place){

        list.get(i).setDay(day);
        list.get(i).setTime(time);
        list.get(i).setPlace(place);
    }

}
