package com.oxilo.oioindia.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oxilo.oioindia.AppController;
import com.oxilo.oioindia.R;
import com.oxilo.oioindia.interfaces.Login_Interface;
import com.oxilo.oioindia.interfaces.Search_Interface;
import com.oxilo.oioindia.modal.search.Result;
import com.oxilo.oioindia.modal.search.SearchResult;
import com.oxilo.oioindia.retrofit.restservice.RestService;
import com.oxilo.oioindia.view.adapter.SearchAdapter;

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


public class SearchDlg extends Dialog implements Search_Interface {
    private Search_Interface login_interface;
    private LinearLayout llMain;
    public int height, width;
    private ImageView iv_cancel;
    private Context context;
    private TextView header;
    private EditText search_location;
    private String mParam1;
    private String mParam2;
    public ProgressDialog prsDlg;
    private static long l;
    Map<String, String> param = new HashMap<>();
    RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<Result> resultArrayList = new ArrayList<Result>();

    public SearchDlg(int height, int width, Search_Interface login_interface, Context context, String mParam1, String mParam2) {
        super(context);


        this.login_interface = login_interface;
        this.context = context;
        this.height = height;
        this.width = width;
        this.mParam1 = mParam1;
        this.mParam2 = mParam2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_search);
        setCancelable(false);
        llMain = (LinearLayout) findViewById(R.id.llMain);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        search_location = (EditText) findViewById(R.id.search_location);
        header = (TextView) findViewById(R.id.location);
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        llMain.getLayoutParams().height = (int) (height * 1.00);
        llMain.getLayoutParams().width = (int) (width * 1.00);
        String LOCATION = AppController.getInstance().getAppPrefs().getObject("LOCATION", String.class);
        if (LOCATION != null && LOCATION.trim().length() > 0)
            header.setText(LOCATION);
        else
            header.setText(mParam2);
        prsDlg = new ProgressDialog(context);

        search_location.addTextChangedListener(new TextWatcher() {
            final android.os.Handler handler = new android.os.Handler();
            Runnable runnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(runnable);

            }


            @Override
            public void afterTextChanged(Editable s) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String text = s.toString();
                        search(text);
                    }
                };
                handler.postDelayed(runnable, 1500);
            }
        });


        searchAdapter = new SearchAdapter(SearchDlg.this, resultArrayList);
        recyclerView.setAdapter(searchAdapter);


    }

    private void search(String text) {
//        System.out.println("### "+text);
        search_api(text, header.getText().toString().trim());
        showProgressDailog();
    }

    public void search_api(String businessname, String cityid) {

        param.clear();
        param.put("businessname", businessname);
        param.put("cityid", cityid);

        Call<ResponseBody> getDepartment = RestService.getInstance().restInterface.search(param);
        getDepartment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                System.out.println("#### search");
                String result = "";
                try {

                    result = response.body().string();
//                    System.out.println("#### search:=  " +result);
                    SearchResult searchResult = new Gson().fromJson(result, SearchResult.class);
                    resultArrayList.clear();

                    for (int i = 0; i < searchResult.getResults().size(); i++) {
                        resultArrayList.add(searchResult.getResults().get(i));
//                        System.out.println("#### search" + resultArrayList.get(i).getAddress1());
                    }
                    searchAdapter.notifyDataSetChanged();
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

    @Override
    public void select(String pogition) {
        System.out.println("#### asdfas:==" + resultArrayList.get(Integer.parseInt(pogition)).getAddress1());
        selectValue(resultArrayList.get(Integer.parseInt(pogition)));
    }

    @Override
    public void serarch_details(String s, String s1) {

    }

    @Override
    public void selectValue(Result result) {
        login_interface.selectValue(result);
    }
}
