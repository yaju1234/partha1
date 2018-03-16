package com.oxilo.oioindia.view.fragments;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.databinding.FragmentBusinessDetailBinding;
import com.oxilo.oioindia.dialog.LoginDlg;
import com.oxilo.oioindia.dialog.ProfileEditDlg;
import com.oxilo.oioindia.view.activity.CommonActivity;
import com.oxilo.oioindia.view.activity.LoginActivity;
import com.oxilo.oioindia.view.activity.MainActivity;
import com.oxilo.oioindia.view.common.NavigationController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamal on 02/15/2018.
 */

public class AccountFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentBusinessDetailBinding binding;
    private Map<String, String> param = new HashMap<>();
    public ProgressDialog prsDlg;
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvEmail;
    private CardView cardAboutUs;
    private CardView cardHelpSupport;
    private CardView cardTramsCondition;
    private CardView cardPrivacyPolicy;
    private CardView cardShare;
    private Button btn_edit;
    String userid;
    String fname;
    String lname ;

    String name;
    String email;
    String mobile;

    String address;
    String pincode;

    private LinearLayout ll_login;
    private LinearLayout ll_edit;
    private LinearLayout llNotLogin;
    private AppCompatButton register_btn;
    private TextView help;

    public AccountFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_account, null, false);
        /*Toolbar toolbar=(Toolbar)v.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });*/
        cardAboutUs=(CardView)v.findViewById(R.id.cardAboutUs);
        cardHelpSupport=(CardView)v.findViewById(R.id.cardHelpSupport);
        cardTramsCondition=(CardView)v.findViewById(R.id.cardTramsCondition);
        cardPrivacyPolicy=(CardView)v.findViewById(R.id.cardPrivacyPolicy);
        cardShare=(CardView)v.findViewById(R.id.cardShare);
        tvName=(TextView)v.findViewById(R.id.tvName);
        tvNumber=(TextView)v.findViewById(R.id.tvNumber);
        tvEmail=(TextView)v.findViewById(R.id.tvEmail);
        btn_edit=(Button) v.findViewById(R.id.btn_edit);
        ll_login=(LinearLayout) v.findViewById(R.id.ll_login);
        ll_edit=(LinearLayout) v.findViewById(R.id.ll_edit);
        llNotLogin=(LinearLayout) v.findViewById(R.id.llNotLogin);
        register_btn=(AppCompatButton) v.findViewById(R.id.register_btn);
        help=(TextView) v.findViewById(R.id.help);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationController navigationController4 = new NavigationController((MainActivity) getActivity());
                navigationController4.navigateToHelp();
            }
        });

        userid = AppController.getInstance().getAppPrefs().getObject("USER_ID",String.class);
        fname =  AppController.getInstance().getAppPrefs().getObject("FNAME", String.class);
        lname =  AppController.getInstance().getAppPrefs().getObject("LNAME", String.class);

        name = fname+" "+lname;
         email = AppController.getInstance().getAppPrefs().getObject("EMAIL",String.class);
        mobile =  AppController.getInstance().getAppPrefs().getObject("MOBILE", String.class);

         address = AppController.getInstance().getAppPrefs().getObject("ADDRESS",String.class);
         pincode =  AppController.getInstance().getAppPrefs().getObject("PINCODE", String.class);

         if(userid !=null && userid.length()>0){
             ll_login.setVisibility(View.VISIBLE);
             ll_edit.setVisibility(View.VISIBLE);
             llNotLogin.setVisibility(View.GONE);
             register_btn.setVisibility(View.GONE);
         }else{
             ll_login.setVisibility(View.GONE);
             ll_edit.setVisibility(View.GONE);
             llNotLogin.setVisibility(View.VISIBLE);
             register_btn.setVisibility(View.VISIBLE);
         }




        tvName.setText(name);
        tvNumber.setText(mobile);
        tvEmail.setText(email);

        cardAboutUs.setOnClickListener(this);
        cardHelpSupport.setOnClickListener(this);
        cardTramsCondition.setOnClickListener(this);
        cardPrivacyPolicy.setOnClickListener(this);
        cardShare.setOnClickListener(this);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
             ProfileEditDlg pdlg = new ProfileEditDlg(display.getHeight(), display.getWidth(), getContext(),userid,fname, lname,address, pincode, new ProfileEditDlg.OnProfileUpdateListener() {
                 @Override
                 public void onProfileUpdateSuccess() {
                     userid = AppController.getInstance().getAppPrefs().getObject("USER_ID",String.class);
                     fname =  AppController.getInstance().getAppPrefs().getObject("FNAME", String.class);
                     lname =  AppController.getInstance().getAppPrefs().getObject("LNAME", String.class);

                     name = fname+" "+lname;
                     email = AppController.getInstance().getAppPrefs().getObject("EMAIL",String.class);
                     mobile =  AppController.getInstance().getAppPrefs().getObject("MOBILE", String.class);

                     address = AppController.getInstance().getAppPrefs().getObject("ADDRESS",String.class);
                     pincode =  AppController.getInstance().getAppPrefs().getObject("PINCODE", String.class);




                     tvName.setText(name);
                     tvNumber.setText(mobile);
                     tvEmail.setText(email);
                 }

                 @Override
                 public void onProfileUpdateFailure() {

                 }
             });
                pdlg.show();
            }
        });
        //toolbar
        return v;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardAboutUs:
                Intent in=new Intent(getActivity(), CommonActivity.class);
                in.putExtra("key","aboutUs");
                startActivity(in);

                break;
            case R.id.cardHelpSupport:
                Intent in1=new Intent(getActivity(), CommonActivity.class);
                in1.putExtra("key","HelpSupport");
                startActivity(in1);
                break;
            case R.id.cardTramsCondition:
                Intent in2=new Intent(getActivity(), CommonActivity.class);
                in2.putExtra("key","TramsCondition");
                startActivity(in2);
                break;
            case R.id.cardPrivacyPolicy:
                Intent in3=new Intent(getActivity(), CommonActivity.class);
                in3.putExtra("key","PrivacyPolicy");
                startActivity(in3);
                break;
            case R.id.cardShare:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_PACKAGE_NAME,"https://www.oioindia.com");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "OIO INDIA is a leading national online search directory that features listings for millions of businesses.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                break;
        }

    }
}
