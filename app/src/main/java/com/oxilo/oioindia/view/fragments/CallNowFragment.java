package com.oxilo.oioindia.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxilo.oioindia.R;
import com.oxilo.oioindia.permission.Permission;


public class CallNowFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView tv_number1,tv_number2;
    private View view_number2;



    public CallNowFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CallNowFragment newInstance(String param1,String param2) {

        CallNowFragment fragment = new CallNowFragment();
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
        View v = inflater.inflate(R.layout.fragment_callnow1, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        tv_number1 = (TextView)v.findViewById(R.id.tv_number1);
        tv_number2 = (TextView)v.findViewById(R.id.tv_number2);
        view_number2 = (View)v.findViewById(R.id.view_number2);

        tv_number1.setText(mParam1);
        tv_number2.setText(mParam2);

        if(mParam2.length()>0){
            tv_number2.setVisibility(View.VISIBLE);
            view_number2.setVisibility(View.VISIBLE);
        }else{
            tv_number2.setVisibility(View.GONE);
            view_number2.setVisibility(View.GONE);
        }
        tv_number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new Permission().checkPhoneCallPermission(getContext())) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mParam1));
                    startActivity(intent);
                }
            }
        });

        tv_number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new Permission().checkPhoneCallPermission(getContext())) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mParam2));
                    startActivity(intent);
                }
            }
        });

        return v;


    }


}
