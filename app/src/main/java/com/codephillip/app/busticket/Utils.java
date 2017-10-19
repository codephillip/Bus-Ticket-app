package com.codephillip.app.busticket;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codephillip.app.busticket.mymodels.Customer;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by codephillip on 12/05/17.
 */

public class Utils {
    public static final String[] screenNames = {"Route", "Receipts", "History", "Profile"};
    public static final String ROUTE_CODE = "route_code";
    //local environment
    //use http://10.0.3.2 when using genymotion emulator
//    public static final String BASE_URL = "http://10.0.3.2:8000";
    //production environment
    public static final String BASE_URL = "https://busticket-backend.herokuapp.com";

    public static final Utils ourInstance = new Utils();
    public static final String CURSOR_POSITION = "cursor_position";
    public static final String SOURCE = "source";
    public static final String DESTINATION = "destination";
    public static final String ORDERS = "orders";
    public static final String HISTORY = "history";
    public static final String SEAT_NUMBER = "1";
    private static final String PREFERENCE_NAME = "just_go";
    public static boolean HISTROY_FRAG_ACTIVE = false;
    public static Cursor cursor;
    public static Customer customer;
    // airtel and mtn valid numbers only
    public static final String PHONE_PATTERN = "^((07)+(0|5|7|8)[0-9]{7})$";
    public static final String PASSWORD_PATTERN = "^[_A-Za-z0-9-\\+].{7,}$";
    public static final String MM_CODE_PATTERN = "^\\d{4}$";

    public static String soapString = "<?xml version=\"1.0\"?>\n" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:b2b=\"http://b2b.mobilemoney.mtn.zm_v1.0\">\n" +
            "  <soapenv:Header>\n" +
            "    <RequestSOAPHeader xmlns=\"http://www.huawei.com.cn/schema/common/v2_1\">\n" +
            "      <spId>2560110004776</spId>\n" +
            "      <spPassword>BD3B1BB8B4822636B7520B96C4A568C7</spPassword>\n" +
            "      <bundleID/>\n" +
            "      <serviceId/>\n" +
            "      <timeStamp>20170503144638</timeStamp>\n" +
            "    </RequestSOAPHeader>\n" +
            "  </soapenv:Header>\n" +
            "  <soapenv:Body>\n" +
            "    <b2b:processRequest>\n" +
            "      <serviceId>200</serviceId>\n" +
            "      <parameter>\n" +
            "        <name>DueAmount</name>\n" +
            "        <value>500</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>MSISDNNum</name>\n" +
            "        <value>256789900760</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>ProcessingNumber</name>\n" +
            "        <!-- generate random java value -->\n" +
            "        <value>%s</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>serviceId</name>\n" +
            "        <value>appchallenge3.sp</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>AcctRef</name>\n" +
            "         <value>101</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>AcctBalance</name>\n" +
            "        <value>300000</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>MinDueAmount</name>\n" +
            "        <value>200</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>Narration</name>\n" +
            "        <value>You have made payment for a bus ticket</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>PrefLang</name>\n" +
            "        <value>en</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>OpCoID</name>\n" +
            "        <value>25601</value>\n" +
            "      </parameter>\n" +
            "      <parameter>\n" +
            "        <name>CurrCode</name>\n" +
            "        <value>UGX</value>\n" +
            "      </parameter>\n" +
            "    </b2b:processRequest>\n" +
            "  </soapenv:Body>\n" +
            "</soapenv:Envelope>";
    public static final String MM_URL = "http://40.68.208.28:9002/ThirdPartyServiceUMMImpl/UMMServiceService/RequestPayment/v17";
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;
    private Context context;

    public static Utils getInstance() {
        return ourInstance;
    }

    public Utils() {
    }

    public Utils(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        edit = pref.edit();
    }

    public static boolean isConnectedToInternet(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void picassoLoader(Context context, ImageView imageView, String url) {
        Log.d("PICASSO", "loading image");
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.bus)
                .error(R.drawable.bus)
                .into(imageView);
    }

    public static int randInt(int min, int max) {
        int randomNum = min + (int) (Math.random() * ((max - min) + 1));
        Log.d("RANDOM", String.valueOf(randomNum));
        return randomNum;
    }

    public static boolean validateData(final String text, final String PATTERN) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static String formatDateString(String date) {
        return date.substring(0, 10);
    }

    public static void displayInfoDialog(Context context, String titleText, String messageText) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.info_dialog);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(titleText);

        TextView message = (TextView) dialog.findViewById(R.id.message);
        message.setText(messageText);

        Button ok = (Button) dialog.findViewById(R.id.ok_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void displayErrorDialog(Context context, String titleText, String messageText) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_dialog);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(titleText);

        TextView message = (TextView) dialog.findViewById(R.id.message);
        message.setText(messageText);

        Button ok = (Button) dialog.findViewById(R.id.ok_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void clearPref(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear().apply();
    }

    public void savePrefString(String key, String value) {
        pref.edit().putString(key, value).apply();
    }

    public String getPrefString(String key) {
        return pref.getString(key, "");
    }

    public void savePrefBoolean(String key, Boolean value) {
        pref.edit().putBoolean(key, value).apply();
    }

    public boolean getPrefBoolean(String key) {
        return pref.getBoolean(key, false);
    }
}
