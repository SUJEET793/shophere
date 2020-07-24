package com.shareindia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter <MainAdapter.MainAdapter_Holder> {
    private static final String LOG_TAG = MainAdapter.class.getSimpleName();

    ArrayList<Main_list_item> main_list_items;
    private LayoutInflater mInflater;
    private static ItemClickListener mClickListener;

    public MainAdapter(Context context, ArrayList<Main_list_item> main_list_items) {
        this.mInflater = LayoutInflater.from(context);
        this.main_list_items = main_list_items;
    }

    @NonNull
    @Override
    public MainAdapter_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.main_list_item, parent, false);
        return new MainAdapter.MainAdapter_Holder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter_Holder holder, int position) {

        holder.main_name.setText(main_list_items.get(position).getMain_item_name());
        holder.main_image.setImageResource(main_list_items.get(position).getMain_item_image());
    }

    @Override
    public int getItemCount() {
        return main_list_items.size();
    }


    public static class MainAdapter_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView main_image;
        public TextView main_name;

        public MainAdapter_Holder(@NonNull View itemView) {
            super(itemView);
            this.main_image = itemView.findViewById(R.id.main_item_image);
            this.main_name = itemView.findViewById(R.id.main_item_name);
            this.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }
    Main_list_item getItem(int id) {
        return main_list_items.get(id);
    }

    public void setmClickListener(ItemClickListener mClickListener) {

        MainAdapter.mClickListener =  mClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
