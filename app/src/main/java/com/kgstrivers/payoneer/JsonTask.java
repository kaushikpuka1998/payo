package com.kgstrivers.payoneer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.kgstrivers.payoneer.Activities.MainActivity;
import com.kgstrivers.payoneer.Adapters.GatewayAdapter;
import com.kgstrivers.payoneer.Interface.AsyncResponse;
import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.Models.Networks;
import com.kgstrivers.payoneer.Models.SearchinRecycle;
import com.kgstrivers.payoneer.Models.WholeModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonTask extends AsyncTask<SearchinRecycle, String, String> {
    public AsyncResponse delegate;
    public String searchvalue;
    public Context context;

    public JsonTask(AsyncResponse asyncResponse)
    {
        this.delegate = asyncResponse;
    }

    public String doInBackground(SearchinRecycle... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0].getUrl());
            searchvalue = params[0].getSearchkeywords();
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            String value = buffer.toString();
            Log.d("Hello 1",value);

            return value;


        } catch (MalformedURLException e) {
            Log.d("Error1:",e.toString());
        } catch (IOException e) {
            Log.d("Error2:",e.toString());
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onPostExecute(String value) {
        List<Applicable> applicables = null;
        List<Applicable> searchcontent = new ArrayList<>();
        if(value!=null)
        {
            Gson gson = new Gson();
            WholeModel wholeModel = gson.fromJson(value,WholeModel.class);
            Networks networks = wholeModel.getNetworks();
            applicables = networks.getApplicable();

            if (searchvalue != "") {

                for(int i=0;i<applicables.size();i++)
                {
                    if(applicables.get(i).getCode().toLowerCase().equals(searchvalue))
                    {
                        Log.d("Searched Value:",applicables.get(i).getLabel());
                        searchcontent.add(applicables.get(i));
                    }
                    Log.d("Value:",applicables.get(i).getLabel());
                }
            }




        }
        delegate.processFinish(searchvalue.length() == 0?applicables:searchcontent);
        super.onPostExecute(value);

    }




}