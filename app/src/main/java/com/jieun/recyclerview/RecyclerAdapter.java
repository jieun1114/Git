package com.jieun.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private List<RecyclerItem> list;


    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }




    public RecyclerAdapter(Context context, List<RecyclerItem> item) {
        this.context = context;
        this.list = item;
    }

    public RecyclerAdapter(){}

    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        holder.bindData(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<RecyclerItem> item){
        this.list = item;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private ImageView image;
        private TextView address;
        private TextView price;

        String str1 = "http://korean.visitseoul.net/comm/getImage?srvcId=MEDIA&parentSn=";
        String str2 = "&fileTy=MEDIA&fileNo=1&thumbTy=L";

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            title = (TextView)itemView.findViewById(R.id.item_title);
            address = (TextView)itemView.findViewById(R.id.item_address);
            price = (TextView)itemView.findViewById(R.id.item_price);
            image = (ImageView)itemView.findViewById(R.id.item_image);


        }

        public void bindData(RecyclerItem item) {

            title.setText(item.getTitle());
            address.setText(item.getAddress());
            price.setText(item.getPrice());

            Glide.with(itemView.getContext())
                    .load(str1 + item.getImageUrl() + str2)
                    .error(R.mipmap.ic_launcher)
                    .into(image);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Log.v("TAG", "" + position);
            Intent intent = new Intent(v.getContext(), ShowPlace.class);
            intent.putExtra("TITLE",list.get(position).getTitle());
            intent.putExtra("URL",str1 + list.get(position).getImageUrl() + str2);
            intent.putExtra("PAGENO",list.get(position).getPageNum());
            intent.putExtra("PRICE",list.get(position).getPrice());
            v.getContext().startActivity(intent);
        }
    }


}
