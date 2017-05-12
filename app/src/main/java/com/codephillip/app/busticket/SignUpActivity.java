package com.codephillip.app.busticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.codephillip.app.busticket.mymodels.Customer;
import com.codephillip.app.busticket.retrofit.ApiClient;
import com.codephillip.app.busticket.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask authTask = null;
    
    private EditText nameView;
    private EditText addressView;
    private EditText emailAddressView;
    private EditText phoneView;
    private EditText passwordView;
    private EditText passwordView2;
    private View progressView;
    private View loginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameView = (EditText) findViewById(R.id.name);
        addressView = (EditText) findViewById(R.id.address);
        emailAddressView = (EditText) findViewById(R.id.email);
        phoneView = (EditText) findViewById(R.id.phone);
        passwordView = (EditText) findViewById(R.id.password);
        passwordView2 = (EditText) findViewById(R.id.password2);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void attemptLogin() {
        if (authTask != null) {
            return;
        }

        // Reset errors.
        nameView.setError(null);
        addressView.setError(null);
        emailAddressView.setError(null);
        phoneView.setError(null);
        passwordView.setError(null);
        passwordView2.setError(null);

        String name = nameView.getText().toString();
        String address = addressView.getText().toString();
        String emailAddress = emailAddressView.getText().toString();
        String phoneNumber = phoneView.getText().toString();
        String password = passwordView.getText().toString();
        String password2 = passwordView2.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password)
                && !isPasswordValid(password)
                && !TextUtils.isEmpty(password2)
                && !isPasswordValid(password2)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        //compare passwords
        if (!Objects.equals(password, password2)) {
            passwordView.setError(getString(R.string.error_unmactched_passwords));
            passwordView2.setError(getString(R.string.error_unmactched_passwords));
            focusView = passwordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            nameView.setError(getString(R.string.error_field_required));
            focusView = nameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(address)) {
            addressView.setError(getString(R.string.error_field_required));
            focusView = addressView;
            cancel = true;
        }

        if (TextUtils.isEmpty(address)) {
            emailAddressView.setError(getString(R.string.error_field_required));
            focusView = emailAddressView;
            cancel = true;
        }

        // Check for a valid phoneNumber address.
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneView.setError(getString(R.string.error_field_required));
            focusView = phoneView;
            cancel = true;
        } else if (!isPhoneNumberValid(phoneNumber)) {
            phoneView.setError(getString(R.string.error_invalid_phoneNumber));
            focusView = phoneView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            authTask = new UserLoginTask(phoneNumber, password, name, address, emailAddress);
            authTask.execute((Void) null);
        }
    }

    private boolean isPhoneNumberValid(String phone) {
        return phone.length() == 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mPhoneNumber;
        private final String mPassword;
        private final String mName;
        private final String mAddress;
        private final String mEmailAddress;

        UserLoginTask(String phoneNumber, String password, String name, String address, String emailAddress) {
            mPhoneNumber = phoneNumber;
            mPassword = password;
            mName = name;
            mAddress = address;
            mEmailAddress = emailAddress;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Log.d(TAG, "doInBackground: "+ mPhoneNumber + "#" + mPassword);
                signUpUser(mPhoneNumber, mPassword, mName, mAddress, mEmailAddress);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        private void signUpUser(String phoneNumber, String password, String name, String address, String emailAddress) {
            ApiInterface apiInterface = ApiClient.getClient(Utils.BASE_URL).create(ApiInterface.class);
            //todo extract user's location
            double longitude = 43.5;
            double latitude = 26.9;
            Customer user = new Customer(name, address, phoneNumber, password, emailAddress, longitude, latitude);
            Call<Customer> call = apiInterface.createCustomer(user);
            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, retrofit2.Response<Customer> response) {
                    int statusCode = response.code();
                    Log.d(TAG, "onResponse: #" + statusCode);
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            showProgress(false);

            if (success) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            showProgress(false);
        }
    }
}

