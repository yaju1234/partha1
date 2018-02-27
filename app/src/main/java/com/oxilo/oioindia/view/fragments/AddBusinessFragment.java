package com.oxilo.oioindia.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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

    private Spinner spin_d11;
    private Spinner spin_d12;
    private Spinner spin_d21;
    private Spinner spin_d22;
    private Spinner spin_d31;
    private Spinner spin_d32;
    private Spinner spin_d41;
    private Spinner spin_d42;
    private Spinner spin_d51;
    private Spinner spin_d52;
    private Spinner spin_d61;
    private Spinner spin_d62;
    private Spinner spin_d71;
    private Spinner spin_d72;
    private String m_spin_d11 = "";
    private String m_spin_d12 = "";
    private String m_spin_d21 = "";
    private String m_spin_d22 = "";
    private String m_spin_d31 = "";
    private String m_spin_d32 = "";
    private String m_spin_d41 = "";
    private String m_spin_d42 = "";
    private String m_spin_d51 = "";
    private String m_spin_d52 = "";
    private String m_spin_d61 = "";
    private String m_spin_d62 = "";
    private String m_spin_d71 = "";
    private String m_spin_d72 = "";
    private String[] sSl = {"","OPEN", "CLOSE"};
    private ArrayAdapter sLAdapter;

    private EditText et_company_name;
    private EditText et_city;
    private EditText et_area;
    private EditText et_address_1;
    private EditText et_address_2;
    private EditText et_zip_code;
    private EditText et_landmark;
    private EditText et_description;
    private EditText et_phone_1;
    private EditText et_phone_2;
    private EditText et_landline_number;
    private EditText et_conatct_person;
    private EditText et_website;
    private EditText et_e_mail;
    private EditText et_facebook;
    private EditText et_twitter;
    private EditText et_google_plus;

    private ImageView iv_image;


    private Button bt_submit;
    private Button bt_choose_file;


    private String m_et_company_name = "";
    private String m_et_city = "";
    private String m_et_area = "";
    private String m_et_address_1 = "";
    private String m_et_address_2 = "";
    private String m_et_zip_code = "";
    private String m_et_landmark = "";
    private String m_et_description = "";
    private String m_et_phone_1 = "";
    private String m_et_phone_2 = "";
    private String m_et_landline_number = "";
    private String m_et_conatct_person = "";
    private String m_et_website = "";
    private String m_et_e_mail = "";
    private String m_et_facebook = "";
    private String m_et_twitter = "";
    private String m_et_google_plus = "";

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
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        sLAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, sSl);
        sLAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_d11 = (Spinner) v.findViewById(R.id.spin_d11);
        spin_d12 = (Spinner) v.findViewById(R.id.spin_d12);
        spin_d21 = (Spinner) v.findViewById(R.id.spin_d21);
        spin_d22 = (Spinner) v.findViewById(R.id.spin_d22);
        spin_d31 = (Spinner) v.findViewById(R.id.spin_d31);
        spin_d32 = (Spinner) v.findViewById(R.id.spin_d32);
        spin_d41 = (Spinner) v.findViewById(R.id.spin_d41);
        spin_d42 = (Spinner) v.findViewById(R.id.spin_d42);
        spin_d51 = (Spinner) v.findViewById(R.id.spin_d51);
        spin_d52 = (Spinner) v.findViewById(R.id.spin_d52);
        spin_d61 = (Spinner) v.findViewById(R.id.spin_d61);
        spin_d62 = (Spinner) v.findViewById(R.id.spin_d62);
        spin_d71 = (Spinner) v.findViewById(R.id.spin_d71);
        spin_d72 = (Spinner) v.findViewById(R.id.spin_d72);

        et_company_name = (EditText) v.findViewById(R.id.et_company_name);
        et_city = (EditText) v.findViewById(R.id.et_city);
        et_area = (EditText) v.findViewById(R.id.et_area);
        et_address_1 = (EditText) v.findViewById(R.id.et_address_1);
        et_address_2 = (EditText) v.findViewById(R.id.et_address_2);
        et_zip_code = (EditText) v.findViewById(R.id.et_zip_code);
        et_landmark = (EditText) v.findViewById(R.id.et_landmark);
        et_description = (EditText) v.findViewById(R.id.et_description);
        et_phone_1 = (EditText) v.findViewById(R.id.et_phone_1);
        et_phone_2 = (EditText) v.findViewById(R.id.et_phone_2);
        et_landline_number = (EditText) v.findViewById(R.id.et_landline_number);
        et_conatct_person = (EditText) v.findViewById(R.id.et_conatct_person);
        et_website = (EditText) v.findViewById(R.id.et_website);
        et_e_mail = (EditText) v.findViewById(R.id.et_e_mail);
        et_facebook = (EditText) v.findViewById(R.id.et_facebook);
        et_twitter = (EditText) v.findViewById(R.id.et_twitter);
        et_google_plus = (EditText) v.findViewById(R.id.et_google_plus);

        iv_image = (ImageView) v.findViewById(R.id.iv_image);
        iv_image.setVisibility(View.GONE);
        bt_submit = (Button) v.findViewById(R.id.bt_submit);
        bt_choose_file = (Button) v.findViewById(R.id.bt_choose_file);


        spin_d11.setAdapter(sLAdapter);
        spin_d12.setAdapter(sLAdapter);
        spin_d21.setAdapter(sLAdapter);
        spin_d22.setAdapter(sLAdapter);
        spin_d31.setAdapter(sLAdapter);
        spin_d32.setAdapter(sLAdapter);
        spin_d41.setAdapter(sLAdapter);
        spin_d42.setAdapter(sLAdapter);
        spin_d51.setAdapter(sLAdapter);
        spin_d52.setAdapter(sLAdapter);
        spin_d61.setAdapter(sLAdapter);
        spin_d62.setAdapter(sLAdapter);
        spin_d71.setAdapter(sLAdapter);
        spin_d72.setAdapter(sLAdapter);

        spin_d11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d11 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d12 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d21.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d21 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d22 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d31.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d31 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d32.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d32 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d41.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d41 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d42.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d42 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d51.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d51 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d52.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d52 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d61.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d61 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d62.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d62 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d71.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d71 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_d72.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_spin_d72 = sSl[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bt_submit.setOnClickListener(this);
        bt_choose_file.setOnClickListener(this);
        return v;

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                getValue();
                break;
                case R.id.bt_choose_file:

                break;
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

    private void getValue() {
        m_et_company_name = et_company_name.getText().toString().trim();
        m_et_city = et_city.getText().toString().trim();
        m_et_area = et_area.getText().toString().trim();
        m_et_address_1 = et_address_1.getText().toString().trim();
        m_et_address_2 = et_address_2.getText().toString().trim();
        m_et_zip_code = et_zip_code.getText().toString().trim();
        m_et_landmark = et_landmark.getText().toString().trim();
        m_et_description = et_description.getText().toString().trim();
        m_et_phone_1 = et_phone_1.getText().toString().trim();
        m_et_phone_2 = et_phone_2.getText().toString().trim();
        m_et_landline_number = et_landline_number.getText().toString().trim();
        m_et_conatct_person = et_conatct_person.getText().toString().trim();
        m_et_website = et_website.getText().toString().trim();
        m_et_e_mail = et_e_mail.getText().toString().trim();
        m_et_facebook = et_facebook.getText().toString().trim();
        m_et_twitter = et_twitter.getText().toString().trim();
        m_et_google_plus = et_google_plus.getText().toString().trim();

        System.out.println("!!!m_et_company_name:="+m_et_company_name);
        System.out.println("!!!m_et_city:="+m_et_city);
        System.out.println("!!!m_et_area:="+m_et_area);
        System.out.println("!!!m_et_address_1:="+m_et_address_1);
        System.out.println("!!!m_et_address_2:="+m_et_address_2);
        System.out.println("!!!m_et_zip_code:="+m_et_zip_code);
        System.out.println("!!!m_et_landmark:="+m_et_landmark);
        System.out.println("!!!m_et_description:="+m_et_description);
        System.out.println("!!!m_et_phone_1:="+m_et_phone_1);
        System.out.println("!!!m_et_phone_2:="+m_et_phone_2);
        System.out.println("!!!m_et_landline_number:="+m_et_landline_number);
        System.out.println("!!!m_et_conatct_person:="+m_et_conatct_person);
        System.out.println("!!!m_et_website:="+m_et_website);
        System.out.println("!!!m_et_e_mail:="+m_et_e_mail);
        System.out.println("!!!m_et_facebook:="+m_et_facebook);
        System.out.println("!!!m_et_twitter:="+m_et_twitter);
        System.out.println("!!!m_et_google_plus:="+m_et_google_plus);


        System.out.println("!!!m_spin_d11:="+m_spin_d11);
        System.out.println("!!!m_spin_d12:="+m_spin_d12);
        System.out.println("!!!m_spin_d21:="+m_spin_d21);
        System.out.println("!!!m_spin_d22:="+m_spin_d22);
        System.out.println("!!!m_spin_d31:="+m_spin_d31);
        System.out.println("!!!m_spin_d32:="+m_spin_d32);
        System.out.println("!!!m_spin_d41:="+m_spin_d41);
        System.out.println("!!!m_spin_d42:="+m_spin_d42);
        System.out.println("!!!m_spin_d51:="+m_spin_d51);
        System.out.println("!!!m_spin_d52:="+m_spin_d52);
        System.out.println("!!!m_spin_d61:="+m_spin_d61);
        System.out.println("!!!m_spin_d62:="+m_spin_d62);
        System.out.println("!!!m_spin_d71:="+m_spin_d71);
        System.out.println("!!!m_spin_d72:="+m_spin_d72);


    }
}
