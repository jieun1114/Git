package com.jieun.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter2 extends BaseAdapter {

    private ArrayList<ListData2> list = new ArrayList<ListData2>();
    public CustomAdapter2(){

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
            convertView = inflater.inflate(R.layout.custom_listview2, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView pricetxt = (TextView) convertView.findViewById(R.id.pricetxt);
        TextView placetxt = (TextView) convertView.findViewById(R.id.place2txt);

        ListData2 listViewItem = list.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        placetxt.setText(listViewItem.getPlace());
        pricetxt.setText(listViewItem.getPrice());

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


    public void addItem(String place, String price) {
        ListData2 item = new ListData2();

        item.setPlace(place);
        item.setPrice(price);

        list.add(item);
    }

    public void removeItem(int i){
        list.remove(i);

    }

    public void removeAll(){
        list.clear();
    }

    public String Price(int i){
        return list.get(i).getPrice();
    }

    public String Place(int i){
        return list.get(i).getPlace();
    }

    public void setListitem(int i, String place, String price){

        list.get(i).setPlace(place);
        list.get(i).setPrice(price);
    }

}
