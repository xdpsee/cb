package com.zhenhui.apps.cnbeta.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;
import com.zhenhui.apps.cnbeta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_back)
    ImageView mBackImageView;
    @BindView(R.id.mobile_image_view)
    ImageView mMobileImageView;
    @BindView(R.id.password_image_view)
    ImageView mPasswordImageView;
    @BindView(R.id.et_mobile)
    EditText mMobileTextEdit;
    @BindView(R.id.et_password)
    EditText mPasswordTextEdit;
    @BindView(R.id.button_login)
    Button mButtonLogin;

    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPasswordTextEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mButtonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        IconicsDrawable drawable = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_angle_left)
                .color(Color.GRAY)
                .paddingDp(2)
                .sizeDp(24);

        mBackImageView.setImageDrawable(drawable);

        drawable = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_mobile)
                .color(Color.GRAY)
                .paddingDp(2)
                .sizeDp(24);
        mMobileImageView.setImageDrawable(drawable);

        drawable = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_credit_card)
                .color(Color.GRAY)
                .paddingDp(2)
                .sizeDp(24);
        mPasswordImageView.setImageDrawable(drawable);

        mBackImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!mLoading) {
            super.onBackPressed();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mLoading) {
            return;
        }

        // Reset errors.
        mMobileTextEdit.setError(null);
        mPasswordTextEdit.setError(null);

        String mobile = mMobileTextEdit.getText().toString();
        String password = mPasswordTextEdit.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            focusView = mPasswordTextEdit;
            Toast.makeText(this, R.string.error_invalid_password, Toast.LENGTH_SHORT).show();
            cancel = true;
        }

        if (TextUtils.isEmpty(mobile)) {
            focusView = mMobileTextEdit;
            Toast.makeText(this, R.string.prompt_mobile, Toast.LENGTH_SHORT).show();
            cancel = true;
        } else if (!isMobileValid(mobile)) {
            Toast.makeText(this, R.string.error_invalid_mobile, Toast.LENGTH_SHORT).show();
            focusView = mMobileTextEdit;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mBackImageView.setEnabled(false);
            mButtonLogin.setEnabled(false);
            mLoading = true;

            UMSSDK.loginWithPhoneNumber("86", mobile, password, new OperationCallback<User>() {
                @Override
                public void onSuccess(User user) {
                    mLoading = false;
                    finish();
                }

                @Override
                public void onFailed(Throwable throwable) {
                    Toast.makeText(LoginActivity.this, R.string.error_login_failure, Toast.LENGTH_SHORT).show();
                    mPasswordTextEdit.requestFocus();
                    mBackImageView.setEnabled(true);
                    mButtonLogin.setEnabled(true);
                    mLoading = false;
                }

                @Override
                public void onCancel() {
                    mLoading = false;
                }
            });
        }
    }

    private boolean isMobileValid(String email) {
        String pattern = "^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$";
        return email.matches(pattern);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() < 16;
    }
}

