package com.oxilo.oioindia.view.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.camera.GetCameraInfo;
import com.oxilo.oioindia.camera.PhotoDetails;
import com.oxilo.oioindia.constant.Constant;
import com.oxilo.oioindia.dialog.LoginDlg;
import com.oxilo.oioindia.even_bus.MessageEventContect;
import com.oxilo.oioindia.event.EventPhotoChosenFromGallery;
import com.oxilo.oioindia.event.EventPickImageViaGallery;
import com.oxilo.oioindia.handlers.CustomSSLSocketFactory;
import com.oxilo.oioindia.handlers.CustomX509TrustManager;
import com.oxilo.oioindia.interfaces.Login_Interface;
import com.oxilo.oioindia.permission.Permission;
import com.oxilo.oioindia.retrofit.restservice.RestService;
import com.oxilo.oioindia.view.activity.MainActivity;
import com.oxilo.oioindia.view.common.NavigationController;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.security.SecureRandom;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * Created by kamal on 02/15/2018.
 */

public class AddBusinessFragment extends Fragment implements View.OnClickListener,Login_Interface {
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
    public String m_spin_d11 = "";
    public String m_spin_d12 = "";
    public String m_spin_d21 = "";
    public String m_spin_d22 = "";
    public String m_spin_d31 = "";
    public String m_spin_d32 = "";
    public String m_spin_d41 = "";
    public String m_spin_d42 = "";
    public String m_spin_d51 = "";
    public String m_spin_d52 = "";
    public String m_spin_d61 = "";
    public String m_spin_d62 = "";
    public String m_spin_d71 = "";
    public String m_spin_d72 = "";
    private String[] sSl = { "24 hrs. OPEN","CLOSE","1 AM","2 AM","3 AM","4 AM","5 AM","6 AM","7 AM","8 AM","9 AM","10 AM","11 AM","12 AM","1 PM","2 PM","3 PM","4 PM","5 PM","6 PM","7 PM","8 PM","9 PM","10 PM","11 PM","12 PM"};
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


    public String m_et_company_name = "";
    public String m_et_city = "";
    public String m_et_area = "";
    public String m_et_address_1 = "";
    public String m_et_address_2 = "";
    public String m_et_zip_code = "";
    public String m_et_landmark = "";
    public String m_et_description = "";
    public String m_et_phone_1 = "";
    public String m_et_phone_2 = "";
    public String m_et_landline_number = "";
    public String m_et_conatct_person = "";
    public String m_et_website = "";
    public String m_et_e_mail = "";
    public String m_et_facebook = "";
    public String m_et_twitter = "";
    public String m_et_google_plus = "";

    private GetCameraInfo getCameraInfo;
    private Bitmap bitmap;
    private static String picturePath = "";
    private static String picturePath1 = "";
    private HttpEntity resEntity;
    public ProgressDialog prsDlg;
    LoginDlg loginDlg;
    String user_id="";
    private double lat, lng;
    private Map<String, String> param = new HashMap<>();

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

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        View v = inflater.inflate(R.layout.fragment_my_add_business, null, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        user_id = AppController.getInstance().getAppPrefs().getObject("USER_ID", String.class);
        sLAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, sSl);
        sLAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lat =  AppController.getInstance().getAppPrefs().getObject("LOCATION_LAT", Double.class);
        lng =  AppController.getInstance().getAppPrefs().getObject("LOCATION_LON", Double.class);

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

        prsDlg = new ProgressDialog(getContext());
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



        if (user_id != null && user_id.trim().length() > 0) {

        }else{
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            loginDlg = new LoginDlg(display.getHeight(), display.getWidth(), AddBusinessFragment.this, getContext());
            loginDlg.show();
        }
        return v;

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                getValue();
                break;
                case R.id.bt_choose_file:
                    if (new Permission().check_WRITE_FolderPermission2(getContext())) {
                        getCameraInfo = new GetCameraInfo();
                        EventBus.getDefault().post(new EventPickImageViaGallery());

                    }
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
        if(isValid()){
            new ProfileAdd().execute();
        }


    }
    public void onEventMainThread(MessageEventContect event) {
        if (event.getFlag().equals("pic_add")) {
            System.out.println("pir......");
            getCameraInfo = new GetCameraInfo();
            EventBus.getDefault().post(new EventPickImageViaGallery());
        }
    }
    public void onEventMainThread(EventPhotoChosenFromGallery e) {
        PhotoDetails photoDetails = e.getPhotoDetails();
        bitmap = getCameraInfo.getOrantationBitmap(photoDetails.getAbsolutePath());
        picturePath = getCameraInfo.SaveBitmapToInternal(bitmap);
        iv_image.setImageBitmap(bitmap);
        iv_image.setVisibility(View.VISIBLE);
        bitmap = null;
        profilePick(picturePath);
    }
    private void profilePick(final String picturePath) {
        picturePath1 = picturePath;
        System.out.println("Path:-" + picturePath);
    }

    @Override
    public void cancel() {
        if (loginDlg != null) {
            loginDlg.dismiss();
            loginDlg = null;


        }
    }

    @Override
    public void login_details(String email, String password) {
        if (loginDlg != null) {
            loginDlg.dismiss();
            loginDlg = null;
        }
        showProgressDailog();
        login_api(email, password);

    }

    public void showProgressDailog() {
       /* prsDlg = new ProgressDialog(this);*/
        prsDlg.setMessage("Please wait...");
        prsDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prsDlg.setIndeterminate(true);
        prsDlg.setCancelable(false);
        prsDlg.show();
    }

    public void dismissProgressDialog() {
        try {
            if (null != prsDlg) {
                if (prsDlg.isShowing()) {
                    prsDlg.dismiss();
                }
            }
        }catch (Exception e){

        }
    }

    public void login_api(String email, String password) {
        param.clear();
        param.put("email", email);
        param.put("password", password);

        Call<ResponseBody> getDepartment = RestService.getInstance().restInterface.login(param);
        getDepartment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
//                    System.out.println("#### login:=  " + response.body().string());
                    JSONObject mapping = new JSONObject(response.body().string().trim());
                    if (mapping.getString("message").equals("Success Login")) {
                        AppController.getInstance().getAppPrefs().putObject("LOGIN", "1");
//                        AppController.getInstance().getAppPrefs().putObject("LOGIN_DETAILS",mapping.toString());
                        AppController.getInstance().getAppPrefs().putObject("USER_ID", mapping.getString("userid"));
                        AppController.getInstance().getAppPrefs().putObject("FNAME", mapping.getString("first name"));
                        AppController.getInstance().getAppPrefs().putObject("FNAME", mapping.getString("last name"));
                        AppController.getInstance().getAppPrefs().putObject("EMAIL", mapping.getString("email"));
                        AppController.getInstance().getAppPrefs().putObject("MOBILE", mapping.getString("mobileno"));
                        AppController.getInstance().getAppPrefs().putObject("ADDRESS", mapping.getString("address"));
                        AppController.getInstance().getAppPrefs().putObject("PINCODE", mapping.getString("pincode"));
                        AppController.getInstance().getAppPrefs().putObject("CITY", mapping.getString("city"));
                        AppController.getInstance().getAppPrefs().putObject("STATE", mapping.getString("state"));
                        AppController.getInstance().getAppPrefs().commit();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }


    class ProfileAdd extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDailog();
           // baseActivity.showProgressDailog();
        }

        @Override
        protected String doInBackground(String... param) {
            try {

                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[] { new CustomX509TrustManager() },
                        new SecureRandom());
                HttpClient httpClient = new DefaultHttpClient();

                SSLSocketFactory ssf = new CustomSSLSocketFactory(ctx);
                ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                ClientConnectionManager ccm = httpClient.getConnectionManager();
                SchemeRegistry sr = ccm.getSchemeRegistry();
                sr.register(new Scheme("https", ssf, 443));
                DefaultHttpClient sslClient = new DefaultHttpClient(ccm,
                        httpClient.getParams());
               // trustEveryone();
//                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost("https://oioindia.com/api/add-business.php");
                //String basicAuth = "Basic YWRtaW46MTIzNDU";
               // httpPost.setHeader("Authorization", basicAuth);
                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                if (picturePath1.length() != 0)
                    entity.addPart("image", new FileBody(new File(picturePath1)));

                entity.addPart("name", new StringBody(m_et_company_name));
                entity.addPart("address1", new StringBody(m_et_address_1));
                entity.addPart("address2", new StringBody(m_et_address_2));
                entity.addPart("slug", new StringBody(m_et_company_name.toLowerCase().replace(" ", "-")));
                entity.addPart("description", new StringBody(m_et_description));
                entity.addPart("email", new StringBody(m_et_e_mail));
                entity.addPart("phonenumber1", new StringBody(m_et_phone_1 ));
                entity.addPart("phonenumber2", new StringBody(m_et_phone_2 ));
                entity.addPart("city", new StringBody(m_et_city ));
                entity.addPart("state", new StringBody(m_et_area ));
                entity.addPart("longitude", new StringBody(String.valueOf(lat) ));
                entity.addPart("latitude", new StringBody(String.valueOf(lng)  ));
                entity.addPart("contactperson", new StringBody(m_et_conatct_person ));
                entity.addPart("landlinenumber", new StringBody(m_et_landmark ));
                entity.addPart("website", new StringBody(m_et_website ));
                entity.addPart("facebook", new StringBody(m_et_facebook ));
                entity.addPart("google", new StringBody(m_et_google_plus));
                entity.addPart("twitter", new StringBody(m_et_twitter ));
                entity.addPart("userid", new StringBody(user_id));
                entity.addPart("d11", new StringBody(m_spin_d11 ));
                entity.addPart("d12", new StringBody(m_spin_d12 ));
                entity.addPart("d21", new StringBody(m_spin_d21 ));
                entity.addPart("d22", new StringBody(m_spin_d22 ));
                entity.addPart("d31", new StringBody(m_spin_d31 ));
                entity.addPart("d32", new StringBody(m_spin_d32 ));
                entity.addPart("d41", new StringBody(m_spin_d41  ));
                entity.addPart("d42", new StringBody(m_spin_d42 ));
                entity.addPart("d51", new StringBody(m_spin_d51  ));
                entity.addPart("d52", new StringBody(m_spin_d52  ));
                entity.addPart("d61", new StringBody(m_spin_d61  ));
                entity.addPart("d62", new StringBody(m_spin_d62  ));
                entity.addPart("d71", new StringBody(m_spin_d71  ));
                entity.addPart("d72", new StringBody(m_spin_d72  ));
                httpPost.setEntity(entity);

                HttpResponse response;
                response = httpClient.execute(httpPost);
                resEntity = response.getEntity();

                final String response_str = EntityUtils.toString(resEntity);
                Log.e("!!TAG12", "Response " + response_str);
                return response_str;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(String sResponse) {
            //  baseActivity.dismissProgressDialog();
            //  Toast.makeText(baseActivity,"sResponse:-"+sResponse,Toast.LENGTH_LONG).show();
            dismissProgressDialog();
            JSONObject response;
            try {
                response = new JSONObject(sResponse);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavigationController navigationController2 = new NavigationController((MainActivity) getActivity());
                        navigationController2.navigateToList();
                        ((MainActivity) getActivity()).navigation.setSelectedItemId(R.id.business_list);
                    }
                }, 500);



            } catch (Exception e) {

            }
        }
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

    public boolean isValid(){
        boolean f = true;
        if(m_et_company_name.length() == 0){
            Toast.makeText(getActivity(), "Please enter Name", Toast.LENGTH_LONG).show();
            f = false;
        }else if(picturePath1.length() == 0){
            Toast.makeText(getActivity(), "Please chose image", Toast.LENGTH_LONG).show();
            f = false;
        }
        return f;
    }


}
