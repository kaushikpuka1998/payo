package com.kgstrivers.payoneer;
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.kgstrivers.payoneer.Interface.AsyncResponse;
import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.Models.Networks;
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

public class JsonTask extends AsyncTask<String, String, String> {
    public AsyncResponse delegate;
    public String searchvalue;

    public JsonTask(AsyncResponse asyncResponse)
    {
        this.delegate = asyncResponse;
    }

    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
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
    protected void onPostExecute(String value) {
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

                        Log.d("Searched Value:",applicables.get(i).getLabel());
                        searchcontent.add(applicables.get(i));

                }
            }
        }
        delegate.processFinish(searchcontent);
        super.onPostExecute(value);
    }
}