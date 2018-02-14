package com.oxilo.oioindia.view.fragments;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.GeoSearchModel;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.constant.Constant;
import com.oxilo.oioindia.view.activity.SplashActivity;
import com.oxilo.oioindia.view.adapter.PlaceSearchAutoAdapter;
import com.oxilo.oioindia.view.common.BaseFragment;

import static com.oxilo.oioindia.view.adapter.PlaceSearchAutoAdapter.STYLE_BOLD;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PlaceSearchAutoAdapter placeSearchAutoAdapter;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    private RecyclerView recyclerView;
    private TextView header;
    private TextView tv_location;


    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onConnected(Location location) {
        double lat = location.getLatitude();
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().
                setTypeFilter(Place.TYPE_COUNTRY).setCountry("IN").build();
        placeSearchAutoAdapter = new PlaceSearchAutoAdapter(getContext(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY, typeFilter);
        placeSearchAutoAdapter.setOnItemClickListener(new PlaceSearchAutoAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String loc = placeSearchAutoAdapter.mResultList.get(position).getPrimaryText(STYLE_BOLD).toString();
                AppController.getInstance().getAppPrefs().putObject("LOCATION", loc);
                AppController.getInstance().getAppPrefs().commit();
                header.setText(loc);
                Constant.locaTextView.setText(loc);
                getActivity().onBackPressed();
            }
        });
        recyclerView.setAdapter(placeSearchAutoAdapter);
        stopLocationUpdates();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        header = (TextView) view.findViewById(R.id.location);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        EditText search_location = (EditText) view.findViewById(R.id.search_location);
        String LOCATION = AppController.getInstance().getAppPrefs().getObject("LOCATION", String.class);
//        System.out.println("#### LOCATION"+LOCATION);
        if (LOCATION != null && LOCATION.trim().length() > 0)
            header.setText(LOCATION);
        else
            header.setText(mParam2);


        search_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                placeSearchAutoAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat=0.00;
                double lon=0.00;
                if (AppController.getInstance().getAppPrefs().getObject("LOCATION_LAT", Double.class) != null) {
                    lat =AppController.getInstance().getAppPrefs().getObject("LOCATION_LAT", Double.class);

                }
                if (AppController.getInstance().getAppPrefs().getObject("LOCATION_LON", Double.class) != null) {
                    lon =AppController.getInstance().getAppPrefs().getObject("LOCATION_LON", Double.class);

                }
                String loc =  GeoSearchModel.getCityInfo(lat, lon, getContext());
                AppController.getInstance().getAppPrefs().putObject("LOCATION", loc);
                AppController.getInstance().getAppPrefs().commit();
                header.setText(loc);
                Constant.locaTextView.setText(loc);
//                getActivity().onBackPressed();
            }
        });


    }
}
