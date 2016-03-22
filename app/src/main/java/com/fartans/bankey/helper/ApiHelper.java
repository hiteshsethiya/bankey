package com.fartans.bankey.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.fartans.bankey.model.ApiConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * Created by adithyar on 3/10/2016.
 */
public class ApiHelper  {
    String apiresponse = "";


    public String getApiresponse() {
        return apiresponse;
    }

    public void setApiresponse(String apiresponse) {
        this.apiresponse = apiresponse;
    }



    public String getResponse(Context context,String endpoint,String...options){
        GetResponse response = new GetResponse(context);
//        if (options.length > 0){
//            response.execute(ApiConstants.API_BASE+endpoint+ApiConstants.CLIENT_ID+ApiConstants.ACCESS_TOKEN+options[0]);
//        }else{
//            response.execute(ApiConstants.API_BASE + endpoint + ApiConstants.CLIENT_ID + ApiConstants.ACCESS_TOKEN);
//        }
        String test = null;
        try {
            //test = response.execute(ApiConstants.API_BASE + endpoint + ApiConstants.CLIENT_ID + ApiConstants.ACCESS_TOKEN).get();
            test = response.execute(endpoint).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return test;
        //return getApiresponse();
    }

    public class GetResponse extends AsyncTask<String,String,String>{

        public String ApiResponse = "";
        private Context mcontext;

        public GetResponse(Context context){
            this.mcontext = context;
        }

//       ProgressDialog progressDialog = new ProgressDialog(mcontext);


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
       //     progressDialog.setProgressStyle(2);
       //     progressDialog.setMessage("Processing");
        //    progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
               // connection.setDoOutput(true);
                connection.connect();

                int responsecode = connection.getResponseCode();
                if(responsecode == 200){
                   String response =  readResponseString(connection);
                    return response;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            setApiresponse(s);
          //  progressDialog.dismiss();
        }

        public String readResponseString(HttpURLConnection connection){
            StringBuilder responseOutput = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  responseOutput.toString();


        }

    }
}

