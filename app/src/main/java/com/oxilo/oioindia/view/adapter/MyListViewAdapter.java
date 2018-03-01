package com.oxilo.oioindia.view.adapter;

/**
 * Created by root on 8/2/18.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxilo.oioindia.R;
import com.oxilo.oioindia.view.activity.MainActivity;
import com.oxilo.oioindia.view.common.NavigationController;
import com.oxilo.oioindia.viewmodal.FaviouriteItem;
import com.oxilo.oioindia.viewmodal.MyList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyListViewAdapter extends RecyclerView.Adapter<MyListViewAdapter.MyViewHolder> {


     private Activity mContext;
     private ArrayList<MyList> faviouriteItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView2;
        public RelativeLayout ll_main;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            imageView2 = (ImageView) v.findViewById(R.id.imageView2);
            ll_main = (RelativeLayout) v.findViewById(R.id.ll_main);
        }
    }


    public MyListViewAdapter(Activity mContext, ArrayList<MyList> faviouriteItems) {
        this.faviouriteItems = faviouriteItems;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyList item = faviouriteItems.get(position);
        holder.name.setText(item.getName());
        String image = item.getImage();
        Picasso.with(mContext)
                .load(item.getImage())
                .error(R.drawable.ic_launcher)
                .into(holder.imageView2);
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationController navigationController = new NavigationController((MainActivity) mContext);
                navigationController.navigateToBusinessDetails1(item.getProduct_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return faviouriteItems.size();
    }



}