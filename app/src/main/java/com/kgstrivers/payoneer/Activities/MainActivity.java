package com.kgstrivers.payoneer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kgstrivers.payoneer.Adapters.GatewayAdapter;
import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.Models.Networks;
import com.kgstrivers.payoneer.Models.WholeModel;
import com.kgstrivers.payoneer.R;
import com.kgstrivers.payoneer.privatedetails.Privatedata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pd;
    RecyclerView recyclerView;
    GatewayAdapter gatewayAdapter;
    Button enterbutton;
    EditText entervalue;
    List<Applicable> applicableListglobal1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        Privatedata privatedata = new Privatedata();
        entervalue = findViewById(R.id.Gatewaynameedittext);
        enterbutton = findViewById(R.id.enter);
        String val = entervalue.getText().toString();
        new JsonTask().execute(privatedata.getJsonurl());
        List<Applicable> listdata = null;
        entervalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                gatewayAdapter = new GatewayAdapter(getApplicationContext(),listdata);
                for(Applicable applicable :applicableListglobal1)
                {
                    if(applicable.getLabel()==charSequence.toString())
                    {
                        listdata.add(applicable);
                    }
                }
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }



    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
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

                    //here u ll get whole response...... :-)

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



            List<Applicable> applicables;

            if(value!=null)
            {
                Gson gson = new Gson();
                WholeModel wholeModel = gson.fromJson(value,WholeModel.class);
                Networks networks = wholeModel.getNetworks();
                applicables = networks.getApplicable();
                for(int i=0;i<applicables.size();i++)
                {

                    Log.d("Value:",applicables.get(i).getLabel());
                }
                applicableListglobal1 = applicables;
                gatewayAdapter = new GatewayAdapter(getApplicationContext(),applicableListglobal1);

                LinearLayoutManager linearlayout = new LinearLayoutManager(getApplicationContext());
                linearlayout.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearlayout);
                recyclerView.setAdapter(gatewayAdapter);


            }

            super.onPostExecute(value);
            if (pd.isShowing()) {
                pd.dismiss();
            }



        }
    }


}