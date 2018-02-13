package com.oxilo.oioindia.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.interfaces.Search_Interface;
import com.oxilo.oioindia.modal.search.Result;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    public ArrayList<Result> mResultList;
    Search_Interface search_interface;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView placeName, placeAddress;
        public LinearLayout ll_view;

        public ViewHolder(View v) {
            super(v);
            placeName = (TextView) v.findViewById(R.id.place_name);
            placeAddress = (TextView) v.findViewById(R.id.place_address);
            ll_view = (LinearLayout) v.findViewById(R.id.ll_view);

        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchAdapter(Search_Interface search_interface,ArrayList<Result> mResultList) {
        this.mResultList = mResultList;
        this.search_interface = search_interface;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_place_row, parent, false);

        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.placeName.setText(mResultList.get(position).getBusinessname());
        holder.placeAddress.setText(mResultList.get(position).getAddress1());
        holder.placeName.setTypeface(null, Typeface.BOLD);
        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_interface.select(""+position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mResultList.size();
    }
}
