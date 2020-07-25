package com.shophere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shophere.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter <MainAdapter.MainAdapter_Holder> implements Filterable {
    private static final String LOG_TAG = MainAdapter.class.getSimpleName();

    List<Main_list_item> main_list_items;
    List<Main_list_item> main_list_items_full;

    private LayoutInflater mInflater;
    private static ItemClickListener mClickListener;

    public MainAdapter(Context context, List<Main_list_item> main_list_items) {
        this.mInflater = LayoutInflater.from(context);
        this.main_list_items = main_list_items;
//        to create a new list
        this.main_list_items_full=new ArrayList<>(main_list_items);

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

//    the method which we have to override for implementing the filterable
//    interface

    @Override
    public Filter getFilter() {
        return main_list_filter;
    }
    private Filter main_list_filter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Main_list_item> filteredList=new ArrayList<>();
            if(constraint ==null || constraint.length()==0){
                filteredList.addAll(main_list_items_full);
            }
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for(Main_list_item item :main_list_items_full){
                    if(item.getMain_item_name().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            main_list_items.clear();
            main_list_items.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };
}
