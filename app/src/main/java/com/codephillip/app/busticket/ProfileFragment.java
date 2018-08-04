package com.codephillip.app.busticket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    TextView name, phone, email, address;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);

        name = rootView.findViewById(R.id.customer_name);
        phone = rootView.findViewById(R.id.phone);
        email = rootView.findViewById(R.id.email);
        address = rootView.findViewById(R.id.address);

        name.setText(Utils.customer.getName());
        phone.setText(Utils.customer.getPhone());
        email.setText(Utils.customer.getEmail());
        address.setText(Utils.customer.getAddress());

        return rootView;
    }
}
