package com.oxilo.oioindia.view.adapter;

/**
 * Created by root on 8/2/18.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.viewmodal.FaviouriteItem;

import java.util.ArrayList;

public class FaviouriteViewAdapter extends RecyclerView.Adapter {

    ArrayList<FaviouriteItem> faviouriteItems;
    Context mContext;
//    protected ItemListener mListener;
    public FaviouriteViewAdapter(Context context, ArrayList<FaviouriteItem> faviouriteItems) {
        this.faviouriteItems =faviouriteItems;
        mContext = context;
//        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
//        DataModel item;

        public ViewHolder(View v) {

            super(v);
//
//            v.setOnClickListener(this);
//            textView = (TextView) v.findViewById(R.id.textView);
//            imageView = (ImageView) v.findViewById(R.id.imageView);
//            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }

        public void setData(FaviouriteItem faviouriteItems) {//DataModel item
//            this.item = item;
//            textView.setText(item.text);
//            imageView.setImageResource(item.drawable);
//            relativeLayout.setBackgroundColor(Color.parseColor(item.color));

        }


        @Override
        public void onClick(View view) {
//            if (mListener != null) {
//                mListener.onItemClick(item);
//            }
        }
    }

    @Override
    public FaviouriteViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_faviourite_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(faviouriteItems.get(position));
    }


    @Override
    public int getItemCount() {

        return faviouriteItems.size();
    }

//    public interface ItemListener {
////        void onItemClick(DataModel item);
//    }
}