package com.oxilo.oioindia.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oxilo.oioindia.R;
import com.oxilo.oioindia.interfaces.Login_Interface;
import com.oxilo.oioindia.view.activity.BaseActivity;


public class LoginDlg extends Dialog {
    private Login_Interface login_interface;
    private LinearLayout llMain;
    public int height, width;
    private ImageView iv_cancel;
    private Button register_btn;
    private EditText email;
    private EditText password;
    private Context context;

    public LoginDlg(int height, int width, Login_Interface login_interface, Context context) {
        super(context);


        this.login_interface = login_interface;
        this.context = context;
        this.height = height;
        this.width = width;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_login);
        setCancelable(false);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        register_btn = (Button) findViewById(R.id.register_btn);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        llMain.getLayoutParams().height = (int) (height * 1.02);
        llMain.getLayoutParams().width = (int) (width * 1.02);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0) {
                    hideKeyboard();
                    login_interface.login_details(email.getText().toString(),password.getText().toString());

                } else {
                    if (email.getText().toString().trim().length() > 0) {
                        Toast.makeText(getContext(), "Enter the valid email", Toast.LENGTH_LONG).show();

                    } else if (password.getText().toString().trim().length() > 0) {
                        Toast.makeText(getContext(), "Enter the password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_interface.cancel();
            }
        });

    }
    public void hideKeyboard() {
        /*
        if (!isKeyboardVisible())
           return;
        */
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            if (inputMethodManager.isAcceptingText()) {
                try {
                    inputMethodManager.hideSoftInputFromWindow(this.findViewById(android.R.id.content).getWindowToken(), 0);
                } catch (Exception e) {
                    //  MyLog.printStackTrace(e);
                }
                try {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                } catch (Exception e) {
                    //  MyLog.printStackTrace(e);
                }
                // inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            }
        } else {
            if (view instanceof EditText) {
                ((EditText) view).setText(((EditText) view).getText().toString()); // reset edit text bug on some keyboards bug
                try {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {
                    // MyLog.printStackTrace(e);
                }
                try {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                } catch (Exception e) {
                    //MyLog.printStackTrace(e);
                }
                // inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            }
        }
    }


}
