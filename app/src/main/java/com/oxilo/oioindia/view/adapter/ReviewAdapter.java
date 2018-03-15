package com.oxilo.oioindia.view.adapter;

/**
 * Created by root on 8/2/18.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.oxilo.oioindia.R;
import com.oxilo.oioindia.view.activity.MainActivity;
import com.oxilo.oioindia.view.common.NavigationController;
import com.oxilo.oioindia.viewmodal.FaviouriteItem;
import com.oxilo.oioindia.viewmodal.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {


     private Context mContext;
     private ArrayList<Review> faviouriteItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView review;
        public RatingBar rating;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            review = (TextView) v.findViewById(R.id.review);
            rating = (RatingBar) v.findViewById(R.id.rating);

        }
    }


    public ReviewAdapter(Context mContext, ArrayList<Review> faviouriteItems) {
        this.faviouriteItems = faviouriteItems;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_review, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Review item = faviouriteItems.get(position);
        holder.name.setText(item.getUsername());
        holder.review.setText(item.getReview());
        holder.rating.setRating(Float.parseFloat(item.getRating()));



    }

    @Override
    public int getItemCount() {
        return faviouriteItems.size();
    }



}