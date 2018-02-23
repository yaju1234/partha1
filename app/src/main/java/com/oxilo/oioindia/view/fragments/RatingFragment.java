package com.oxilo.oioindia.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.dialog.LoginDlg;
import com.oxilo.oioindia.interfaces.Login_Interface;
import com.oxilo.oioindia.retrofit.restservice.RestService;
import com.oxilo.oioindia.view.adapter.FaviouriteViewAdapter;
import com.oxilo.oioindia.viewmodal.FaviouriteItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RatingFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView ll_top_header;
    private RatingBar ll_rating_bar;
    private EditText ll_comment_write;
    private AppCompatButton submit_btn;


    public RatingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RatingFragment newInstance(String param1,String param2) {

        RatingFragment fragment = new RatingFragment();
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
        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        ll_top_header = (TextView)v.findViewById(R.id.ll_top_header);
        ll_rating_bar = (RatingBar)v.findViewById(R.id.ll_rating_bar);
        ll_comment_write = (EditText)v.findViewById(R.id.ll_comment_write);
        submit_btn = (AppCompatButton)v.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
        ll_top_header.setText(mParam2);

        return v;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_btn:
                break;
        }
    }
}
