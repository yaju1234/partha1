package com.oxilo.oioindia.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.GridLayoutManager;

import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.dialog.LoginDlg;
import com.oxilo.oioindia.interfaces.Login_Interface;
import com.oxilo.oioindia.retrofit.restservice.RestService;
import com.oxilo.oioindia.view.activity.BaseActivity;
import com.oxilo.oioindia.view.activity.MainActivity;
import com.oxilo.oioindia.view.activity.SplashActivity;
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
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FaviouriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FaviouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaviouriteFragment extends Fragment implements View.OnClickListener, Login_Interface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String login;

    private OnFragmentInteractionListener mListener;
    TextView tv_login;
//    LinearLayout ll_login;
//    EditText email;
//    EditText password;
//    Button register_btn;

    private Map<String, String> param = new HashMap<>();
    public ProgressDialog prsDlg;
    private String user_id;
    RecyclerView recyclerView;
    ArrayList<FaviouriteItem> faviouriteItems = new ArrayList<FaviouriteItem>();
    FaviouriteViewAdapter faviouriteViewAdapter;
    LoginDlg loginDlg;

    public FaviouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter ic_name.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FaviouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FaviouriteFragment newInstance(String param1, String param2) {
        FaviouriteFragment fragment = new FaviouriteFragment();
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
        View v = inflater.inflate(R.layout.fragment_faviourite, container, false);

        tv_login = (TextView) v.findViewById(R.id.tv_login);
        prsDlg = new ProgressDialog(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.categorylist);

        faviouriteItems.clear();
        faviouriteViewAdapter = new FaviouriteViewAdapter(getActivity(), faviouriteItems);

        recyclerView.setAdapter(faviouriteViewAdapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        if (getView() != null)
            showPage();

        return v;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPage();

                }
            }, 100);
        } else {

        }
    }

    private void showPage() {
        login = AppController.getInstance().getAppPrefs().getObject("LOGIN", String.class);
        user_id = AppController.getInstance().getAppPrefs().getObject("USER_ID", String.class);
        tv_login.setVisibility(View.GONE);
        tv_login.setText("Please login");
        param.clear();
        param.put("user_id", user_id);
        recyclerView.setVisibility(View.GONE);
        if (user_id != null && user_id.trim().length() > 0) {
            tv_login.setVisibility(View.VISIBLE);
            showProgressDailog();
            getFavorites();
        } else {
            tv_login.setVisibility(View.VISIBLE);
            tv_login.setOnClickListener(this);
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            loginDlg = new LoginDlg(display.getHeight(), display.getWidth(), FaviouriteFragment.this, getContext());
            loginDlg.show();
        }
    }

    @Override
    public void cancel() {
        if (loginDlg != null) {
            loginDlg.dismiss();
            loginDlg = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                loginDlg = new LoginDlg(display.getHeight(), display.getWidth(), FaviouriteFragment.this, getContext());
                loginDlg.show();
                break;
//            case R.id.register_btn:
//                if (email.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0) {
//
//                    //showProgressDailog();
//                    //login_api();
//
//
//                } else {
//                    if (email.getText().toString().trim().length() > 0) {
//                        Toast.makeText(getContext(), "Enter the valid email", Toast.LENGTH_LONG).show();
//
//                    } else if (password.getText().toString().trim().length() > 0) {
//                        Toast.makeText(getContext(), "Enter the password", Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
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
                        AppController.getInstance().getAppPrefs().commit();
                        showPage();
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

    public void getFavorites() {
        Call<ResponseBody> getDepartment = RestService.getInstance().restInterface.get_favorites(param);
        getDepartment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

//                    System.out.println("#### getFavorites:=  " + response.body().string());
                    JSONObject jsonObject = new JSONObject(response.body().string().trim());
                    if (jsonObject.getString("result").equals("0")) {
                        recyclerView.setVisibility(View.GONE);
                        tv_login.setVisibility(View.VISIBLE);
                        tv_login.setText("No favorites Found");
                    } else {
                        JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
                        faviouriteItems.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String product_id = (jsonObject1.getString("product_id"));
                            String name = (jsonObject1.getString("name"));
                            String address1 = (jsonObject1.getString("address1"));
                            String address2 = (jsonObject1.getString("address2"));
                            String phonenumber1 = (jsonObject1.getString("phonenumber1"));
                            String phonenumber2 = (jsonObject1.getString("phonenumber2"));
                            String image = (jsonObject1.getString("image"));
                            FaviouriteItem faviouriteItem = new FaviouriteItem(product_id, name, address1, address2, phonenumber1, phonenumber2, image);
                            faviouriteItems.add(faviouriteItem);
                        }
                        faviouriteViewAdapter.notifyDataSetChanged();

                        recyclerView.setVisibility(View.VISIBLE);
                        tv_login.setVisibility(View.GONE);
                        tv_login.setText("");
                    }
                } catch (Exception e) {
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        } catch (Exception e) {

        }
    }
}
