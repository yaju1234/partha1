package com.oxilo.oioindia.view.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oxilo.oioindia.R;

/**
 * Created by kamal on 02/15/2018.
 */

public class AddBusinessFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    FragmentBusinessDetailBinding binding;
//    private Map<String, String> param = new HashMap<>();
//    public ProgressDialog prsDlg;
//    private TextView tvName;
//    private TextView tvNumber;
//    private TextView tvEmail;
//    private CardView cardAboutUs;
//    private CardView cardHelpSupport;
//    private CardView cardTramsCondition;
//    private CardView cardPrivacyPolicy;
//    private CardView cardShare;

    public AddBusinessFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static AddBusinessFragment newInstance() {
        AddBusinessFragment fragment = new AddBusinessFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_add_business, null, false);
        Toolbar toolbar=(Toolbar)v.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
//        cardAboutUs=(CardView)v.findViewById(R.id.cardAboutUs);
//        cardHelpSupport=(CardView)v.findViewById(R.id.cardHelpSupport);
//        cardTramsCondition=(CardView)v.findViewById(R.id.cardTramsCondition);
//        cardPrivacyPolicy=(CardView)v.findViewById(R.id.cardPrivacyPolicy);
//        cardShare=(CardView)v.findViewById(R.id.cardShare);
//        tvName=(TextView)v.findViewById(R.id.tvName);
//        tvNumber=(TextView)v.findViewById(R.id.tvNumber);
//        tvEmail=(TextView)v.findViewById(R.id.tvEmail);
//        tvName.setText("Ajay Sodhi");
//        tvNumber.setText("(9087678899");
//        tvEmail.setText("test@gmail.com");
//     AppController.getInstance().getAppPrefs().getObject("USER_ID",String.class);
//        AppController.getInstance().getAppPrefs().getObject("FNAME", String.class);
//        AppController.getInstance().getAppPrefs().getObject("LNAME", String.class);
//        AppController.getInstance().getAppPrefs().getObject("EMAIL",String.class);
//        AppController.getInstance().getAppPrefs().getObject("MOBILE", String.class);

//        cardAboutUs.setOnClickListener(this);
//        cardHelpSupport.setOnClickListener(this);
//        cardTramsCondition.setOnClickListener(this);
//        cardPrivacyPolicy.setOnClickListener(this);
//        cardShare.setOnClickListener(this);
        //toolbar
        return v;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.cardAboutUs:
//                Intent in=new Intent(getActivity(), CommonActivity.class);
//                in.putExtra("key","aboutUs");
//                startActivity(in);
//
//                break;
//            case R.id.cardHelpSupport:
//                Intent in1=new Intent(getActivity(), CommonActivity.class);
//                in1.putExtra("key","HelpSupport");
//                startActivity(in1);
//                break;
//            case R.id.cardTramsCondition:
//                Intent in2=new Intent(getActivity(), CommonActivity.class);
//                in2.putExtra("key","TramsCondition");
//                startActivity(in2);
//                break;
//            case R.id.cardPrivacyPolicy:
//                Intent in3=new Intent(getActivity(), CommonActivity.class);
//                in3.putExtra("key","PrivacyPolicy");
//                startActivity(in3);
//                break;
//            case R.id.cardShare:
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_PACKAGE_NAME,"https://www.oioindia.com");
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "OIO INDIA is a leading national online search directory that features listings for millions of businesses.");
//                sendIntent.setType("text/plain");
//                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
//                break;
        }

    }
}
