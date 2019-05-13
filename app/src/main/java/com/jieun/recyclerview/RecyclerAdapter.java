package com.jieun.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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

    public RecyclerAdapter(){}

    public RecyclerAdapter(Context context, List<RecyclerItem> item) {
        this.context = context;
        this.list = item;
    }

    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        holder.bindData(list.get(position));

        final Button btn = holder.btn;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getFlag() == true) {
                    final Toast toast = Toast.makeText(v.getContext(), "내 여행에서 삭제되었습니다", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    },1000);

                    list.get(position).setFlag(false);
                    btn.setText("Add\n My Trip");
                    btn.setBackground(ContextCompat.getDrawable(btn.getContext(),R.drawable.recyclerbtn));
                }
                else {
                    final Toast toast = Toast.makeText(v.getContext(), "내 여행에 추가되었습니다", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    },1000);
                    list.get(position).setFlag(true);
                    btn.setText("Delete My Trip");
                    btn.setBackground(ContextCompat.getDrawable(btn.getContext(),R.drawable.recyclerbtn2));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<RecyclerItem> item){
        this.list = item;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;
        private TextView address;
        private TextView price;
        public Button btn;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.item_title);
            address = (TextView)itemView.findViewById(R.id.item_address);
            price = (TextView)itemView.findViewById(R.id.item_price);
            image = (ImageView)itemView.findViewById(R.id.item_image);
            btn = (Button)itemView.findViewById(R.id.addbtn);

        }

        public void bindData(RecyclerItem item) {

            title.setText(item.getTitle());
            address.setText(item.getAddress());
            price.setText(item.getPrice());
            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .error(R.mipmap.ic_launcher)
                    .into(image);

        }
    }


}
