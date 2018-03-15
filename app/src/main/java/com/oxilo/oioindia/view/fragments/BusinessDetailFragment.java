package com.oxilo.oioindia.view.fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.data.DataManager;
import com.oxilo.oioindia.databinding.FragmentBusinessDetailBinding;
import com.oxilo.oioindia.dialog.LoginDlg;
import com.oxilo.oioindia.dialog.ReviewDlg;
import com.oxilo.oioindia.interfaces.Login_Interface;
import com.oxilo.oioindia.modal.BusinessDetails;
import com.oxilo.oioindia.modal.Details;
import com.oxilo.oioindia.retrofit.restservice.RestService;
import com.oxilo.oioindia.view.activity.LoginActivity;
import com.oxilo.oioindia.view.activity.MainActivity;
import com.oxilo.oioindia.view.adapter.CustomPagerAdapter;
import com.oxilo.oioindia.view.common.NavigationController;
import com.oxilo.oioindia.viewmodal.BusinesDetailViewModal;
import com.oxilo.oioindia.viewmodal.MainViewModal;
import com.squareup.picasso.Picasso;

import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusinessDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessDetailFragment extends Fragment implements View.OnClickListener, Login_Interface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView reviews_rating_list;
    FragmentBusinessDetailBinding binding;
    private ImageView iv_favorites;
    private Map<String, String> param = new HashMap<>();
    public ProgressDialog prsDlg;
    String user_id="";
    private boolean b_Is_Favorites = false;
    private LinearLayout ll_rate_this;
    private TextView name;
    LoginDlg loginDlg;
    RatingBar rating;
    TextView txtRating;
    TextView noofreview;
    private LinearLayout llmessage;
    private ImageView image;
    ViewPager viewPager;
    CustomPagerAdapter pagerAdapter;
    ArrayList<String> setImag = new ArrayList<>();
    private LinearLayout call;
    String ph;
    private LinearLayout llmap;
    double lat = 22.5726;
    double lng = 88.3639;
    private LinearLayout ll_all_review;
    private TextView tv_all_review;
    private TextView tv_rating_count;
    public BusinessDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter ic_name.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusinessDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusinessDetailFragment newInstance(String param1, String param2) {
        BusinessDetailFragment fragment = new BusinessDetailFragment();
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

        binding = DataBindingUtil.bind(inflater.inflate(R.layout.fragment_business_detail, container, false));
        BusinesDetailViewModal.Factory factory = new BusinesDetailViewModal.Factory(getActivity().getApplication());
        BusinesDetailViewModal viewModal = ViewModelProviders.of(this, factory).get(BusinesDetailViewModal.class);
        binding.setVm(viewModal);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        View v = binding.getRoot();
        iv_favorites = (ImageView) v.findViewById(R.id.iv_favorites);
        image = (ImageView) v.findViewById(R.id.image);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        pagerAdapter = new CustomPagerAdapter(getActivity(),setImag);
        viewPager.setAdapter(pagerAdapter);
        ll_rate_this = (LinearLayout) v.findViewById(R.id.ll_rate_this);
        llmessage = (LinearLayout) v.findViewById(R.id.llmessage);
        call = (LinearLayout) v.findViewById(R.id.call);
        ll_all_review = (LinearLayout) v.findViewById(R.id.ll_all_review);
        llmap = (LinearLayout) v.findViewById(R.id.llmap);
        tv_rating_count = (TextView) v.findViewById(R.id.tv_rating_count);
        txtRating = (TextView) v.findViewById(R.id.txtRating);
        tv_all_review = (TextView) v.findViewById(R.id.tv_all_review);
        name = (TextView) v.findViewById(R.id.name);
        noofreview = (TextView) v.findViewById(R.id.noofreview);
        rating = (RatingBar) v.findViewById(R.id.rating);
        ll_rate_this.setOnClickListener(this);
        iv_favorites.setOnClickListener(this);
        llmessage.setOnClickListener(this);
        user_id = AppController.getInstance().getAppPrefs().getObject("USER_ID", String.class);
        prsDlg = new ProgressDialog(getContext());
        llmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationController navigationController = new NavigationController((MainActivity) getActivity());
                navigationController.navigateToMap(lat, lng,name.getText().toString());
            }
        });
//AIzaSyBC5tkJ579RY05RXMxUDTCrAqF0EAoM0hY

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ph));
                startActivity(intent);
            }
        });

        String userid = "0";
                if(AppController.getInstance().getAppPrefs().getObject("LOGIN", String.class)!=null && AppController.getInstance().getAppPrefs().getObject("LOGIN", String.class).equalsIgnoreCase("1")){
            userid = AppController.getInstance().getAppPrefs().getObject("USER_ID", String.class);;
                }
        viewModal.getBusinessDetail(mParam1,userid).subscribe(new Consumer<Response<ResponseBody>>() {
            @Override
            public void accept(Response<ResponseBody> responseBodyResponse) throws Exception {
                viewModal.enable.set(false);
                try {
                    String sd = new String(responseBodyResponse.body().bytes());
                    JSONObject mapping = new JSONObject(sd);
                    int evgrating  = mapping.getJSONArray("result1").getJSONObject(0).getInt("avgrating");
                    txtRating.setText(""+evgrating);
                    rating.setRating(evgrating);
                    b_Is_Favorites = mapping.getString("isfav").equalsIgnoreCase("false")?false:true;
//  entity.addPart("longitude", new StringBody(String.valueOf(lat) ));
   //                 entity.addPart("latitude", new StringBody(String.valueOf(lng)  ));
                    if (b_Is_Favorites) {
                        iv_favorites.setImageResource(R.drawable.favorite);
                    } else {
                        iv_favorites.setImageResource(R.drawable.un_favorite);
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    List<BusinessDetails> businessDetailsList = mapper.readValue(mapping.getString("result1"), new TypeReference<List<BusinessDetails>>() {
                    });
//
                    Log.e("SIZE==", "" + businessDetailsList.size());
                    /*if (businessDetailsList.get(0).getImage() == null)
                        businessDetailsList.get(0).setImage("http://cumbrianrun.co.uk/wp-content/uploads/2014/02/default-placeholder-300x300.png");*/
                    binding.setRepo(businessDetailsList.get(0));
                    binding.desc.setText(Html.fromHtml(businessDetailsList.get(0).getDescription()));

                   /* String url = businessDetailsList.get(0).getImage();
                    if (!url.equals("") && url != null) {
                        try {
                            Picasso.with(getActivity()).load(url).placeholder(R.drawable.no_image).fit().centerCrop().into(binding.image);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }*/

                    JSONArray result2 = mapping.getJSONArray("result2");
                    if(result2.length() == 0){
                        image.setBackgroundResource(R.drawable.no_image);
                        image.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.GONE);
                    }else if(result2.length() > 0){
                       // Picasso.with(getActivity()).load(result2.getJSONObject(0).getString("img_name")).placeholder(R.drawable.no_image).fit().centerCrop().into(image);
                        String uuu = result2.getJSONObject(0).getString("img_name");
                        Log.e("uuu", uuu);
                        Picasso.with(getActivity())
                                .load(result2.getJSONObject(0).getString("img_name"))
                                .placeholder(R.drawable.no_image)
                                .fit()
                                .centerInside()
                                .into(binding.image, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError() {
                                        Log.e("Error", "Error .......................");
                                    }
                                });

                        image.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.GONE);
                    }else{
                        image.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                        for(int i=0; i<result2.length(); i++){
                            JSONObject c = result2.getJSONObject(i);
                            setImag.add(c.getString("img_name"));
                        }
                        pagerAdapter.notifyDataSetChanged();

                    }

                    int totalreviews = mapping.getJSONArray("result1").getJSONObject(0).getInt("totalreviews");
                    ph = mapping.getJSONArray("result1").getJSONObject(0).getString("phonenumber1");
                    noofreview.setText(""+totalreviews+" Review");
                    tv_rating_count.setText(""+totalreviews+" Ratings");
                    if(totalreviews>=1){
                        tv_all_review.setText("View All Reviews");
                       //ll_all_review.setVisibility(View.VISIBLE);
                    }else{
                        tv_all_review.setText("No Reviews");
                      //  ll_all_review.setVisibility(View.GONE);
                    }

                    JSONObject jobj =  mapping.getJSONArray("result1").getJSONObject(0);
                    if(jobj.has("latitude")){
                        lat = Double.parseDouble(jobj.getString("latitude"));
                    }

                    if(jobj.has("longitude")){
                        lng = Double.parseDouble(jobj.getString("longitude"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });


        ll_all_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
              ReviewDlg loginDlg = new ReviewDlg(display.getHeight(), display.getWidth(), getContext(), getArguments().getString(ARG_PARAM1));
                loginDlg.show();
            }
        });


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_favorites:
                // "11031"
                if (user_id != null && user_id.trim().length() > 0) {
                    param.clear();
                    param.put("userid", user_id);
                    param.put("businessid", getArguments().getString(ARG_PARAM1));
                    if (b_Is_Favorites) {
                        param.put("action", "delete");
                        iv_favorites.setImageResource(R.drawable.un_favorite);
                        b_Is_Favorites = false;
                    } else {
                        param.put("action", "add");
                        iv_favorites.setImageResource(R.drawable.favorite);
                        b_Is_Favorites = true;
                    }
                    showProgressDailog();
                    feb_Api();
                }else{
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
                break;

            case R.id.ll_rate_this:
                if (user_id != null && user_id.trim().length() > 0) {
                    NavigationController navigationController = new NavigationController((MainActivity) getActivity());
                    navigationController.navigateToRating(getArguments().getString(ARG_PARAM1),name.getText().toString());
                }else{
                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    loginDlg = new LoginDlg(display.getHeight(), display.getWidth(), BusinessDetailFragment.this, getContext());
                    loginDlg.show();
                }




                break;

            case R.id.llmessage:

                if (user_id != null && user_id.trim().length() > 0) {
                    NavigationController navigationController = new NavigationController((MainActivity) getActivity());
                    navigationController.navigateToMessage(getArguments().getString(ARG_PARAM1),name.getText().toString());
                }else{
                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    loginDlg = new LoginDlg(display.getHeight(), display.getWidth(), BusinessDetailFragment.this, getContext());
                    loginDlg.show();
                }
                break;
        }

    }
    public void feb_Api(){
        Call<ResponseBody> getDepartment = RestService.getInstance().restInterface.add_del_fav(param);
        getDepartment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println("#### response:=  "+response.body().string());
                } catch (IOException e) {
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
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reviews_rating_list = (RecyclerView) view.findViewById(R.id.reviews_rating_list);
        setHasOptionsMenu(true);
        reviews_rating_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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
                      //  login = AppController.getInstance().getAppPrefs().getObject("LOGIN", String.class);
                        user_id = AppController.getInstance().getAppPrefs().getObject("USER_ID", String.class);
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
}
