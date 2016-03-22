package com.fartans.bankey.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fartans.bankey.R;
import com.fartans.bankey.helper.ApiHelper;
import com.fartans.bankey.model.AccountDetails;
import com.fartans.bankey.model.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anuj on 3/15/16.
 */
public class AccountFragment extends Fragment

    {
        AccountDetails accountDetails = new AccountDetails();


        public AccountFragment() {

    }

        @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
            //TextView tv = (TextView)view.findViewById(R.id.AccountSummaryJson);
            TextView tvbalance = (TextView)view.findViewById(R.id.tvbalanceamt);
            TextView tvbalanceamt = (TextView)view.findViewById(R.id.tvbalance);
            TextView tvcurrency = (TextView)view.findViewById(R.id.tvCurrency);
            TextView tvaccnum = (TextView)view.findViewById(R.id.tvaccnum);
            TextView tvaccstatus = (TextView)view.findViewById(R.id.tvaccstatus);

            ApiHelper apiHelper = new ApiHelper();
           String text = apiHelper.getResponse(getActivity().getApplicationContext(),ApiConstants.test_acc);
            parseAccountSummaryJSON(text);
            tvbalanceamt.setText(accountDetails.getAccount_balance());
            tvcurrency.setText(accountDetails.getAccount_currency());
            tvaccnum.setText(accountDetails.getAccount_number());
            tvaccstatus.setText(accountDetails.getAccount_status());
            return view;



    }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inflateView();

    }

        public void parseAccountSummaryJSON(String text){
            try {
                JSONArray jsonArray = new JSONArray(text);
                JSONObject jsonObject = jsonArray.getJSONObject(1);
                String balance = jsonObject.getString("balance");
                String currency = jsonObject.getString("currency");
                String account_status = jsonObject.getString("account_status");
                String account_number = jsonObject.getString("account_no");

                accountDetails.setAccount_balance(balance);
                accountDetails.setAccount_currency(currency);
                accountDetails.setAccount_status(account_status);
                accountDetails.setAccount_number(account_number);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    public void inflateView() {




    }
}
