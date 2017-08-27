package com.codephillip.app.busticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codephillip.app.busticket.mymodels.Customer;
import com.codephillip.app.busticket.mymodels.Customers;
import com.codephillip.app.busticket.retrofit.ApiClient;
import com.codephillip.app.busticket.retrofit.ApiInterface;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.getSimpleName();
    private EditText phoneView;
    private EditText passwordView;
    private TextView createAccountView;
    private View progressView;
    private View loginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo activate sign in
        setContentView(R.layout.activity_sign_in);
        phoneView = (EditText) findViewById(R.id.phone);
        passwordView = (EditText) findViewById(R.id.password);

        createAccountView = (TextView) findViewById(R.id.create_account);
        createAccountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        //todo remove on release
//        signInUser("0771234566", "password123");

        Log.d(TAG, "onCreate: auto start main activity#");
//        todo remove on release. testing app without data
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
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

        Log.d(TAG, "attemptLogin: started login");
        // Reset errors.
        phoneView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String phoneNumber = phoneView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
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
            try {
                if (Utils.isConnectedToInternet(this))
                    signInUser(phoneNumber, password);
                else
                    Toast.makeText(this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPhoneNumberValid(String phone) {
        return phone.length() == 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    private void signInUser(String phoneNumber, String password) {
        ApiInterface apiInterface = ApiClient.getClient(Utils.BASE_URL).create(ApiInterface.class);
        Log.d(TAG, "signInUser: " + phoneNumber + password);
        Customer customers = new Customer(phoneNumber, password);

        Call<Customers> call = apiInterface.signInCustomer(customers);
        call.enqueue(new Callback<Customers>() {
            @Override
            public void onResponse(Call<Customers> call, retrofit2.Response<Customers> response) {
                int statusCode = response.code();
                Customers customers = response.body();
                //make server return 400 incase user failed to sign-in(has wrong credentials)
                processResult((statusCode == HttpsURLConnection.HTTP_ACCEPTED), customers);
            }

            @Override
            public void onFailure(Call<Customers> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        });
    }

    private void processResult(boolean success, Customers customers) {
//        showProgress(false);
        if (success) {
            Utils.customer = new Customer(customers.getCustomers().get(0).getId(), customers.getCustomers().get(0).getName(), customers.getCustomers().get(0).getAddress(), customers.getCustomers().get(0).getPhone());
            //get infor from the server
//            startService(new Intent(this, SetUpService.class));
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            passwordView.setError(getString(R.string.error_incorrect_password));
            passwordView.requestFocus();
        }
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
}

