package com.shophere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shophere.R;

import java.util.ArrayList;
import java.util.List;

public class CatagoriesAdapter extends RecyclerView.Adapter<CatagoriesAdapter.Categories_Holder> {

    private static final String LOG_TAG = CatagoriesAdapter.class.getSimpleName();

    List<Catagories_items> catagories_items;

    private LayoutInflater mInflater;
    private static ItemClickListener mClickListener;



    public CatagoriesAdapter(Context context, ArrayList<Catagories_items> catagories_items) {
        this.mInflater = LayoutInflater.from(context);
        this.catagories_items = catagories_items;
    }

    @NonNull
    @Override
    public Categories_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.categaries_item, parent, false);
        return new CatagoriesAdapter.Categories_Holder(listItem);
    }
    @Override
    public void onBindViewHolder(@NonNull Categories_Holder holder, int position) {

            holder.cat_name.setText(catagories_items.get(position).getCat_item_name());
            holder.cat_image.setImageResource(catagories_items.get(position).getCat_item_image());
            holder.catButton.setText("Add");
        }

    @Override
    public int getItemCount() {
        return catagories_items.size();
    }

    public static class Categories_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
            public ImageView cat_image;
            public TextView cat_name;
            public TextView catButton;


        public Categories_Holder(@NonNull View itemView) {
                super(itemView);
                this.cat_image=itemView.findViewById(R.id.cat_item_image);
                this.cat_name=itemView.findViewById(R.id.cat_item_name);
                this.catButton=itemView.findViewById(R.id.cat_button);
                itemView.setOnClickListener(this);
            }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

      }
    Catagories_items getItem(int id) {
        return catagories_items.get(id);
    }

    public void setmClickListener(ItemClickListener mClickListener) {

       CatagoriesAdapter.mClickListener = mClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}




