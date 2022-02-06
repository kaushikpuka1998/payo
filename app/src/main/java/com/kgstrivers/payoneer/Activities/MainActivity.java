package com.kgstrivers.payoneer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kgstrivers.payoneer.Adapters.GatewayAdapter;
import com.kgstrivers.payoneer.Interface.AsyncResponse;
import com.kgstrivers.payoneer.JsonTask;
import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.Models.Networks;
import com.kgstrivers.payoneer.Models.SearchinRecycle;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    ProgressDialog pd;
    RecyclerView recyclerView;
    GatewayAdapter gatewayAdapter;
    Button enterbutton;
    EditText entervalue;
    Privatedata privatedata;
    List<Applicable> listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initialize();
        createprogressdialog(1000);
        JsonTask jsonTask =new JsonTask(new AsyncResponse(){
            @Override
            public void processFinish(List<Applicable> output) {
                listdata.addAll(output);
                gatewayAdapter = new GatewayAdapter(getApplicationContext(),output);
                LinearLayoutManager linearlayout = new LinearLayoutManager(getApplicationContext());
                linearlayout.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearlayout);
                recyclerView.setAdapter(gatewayAdapter);

            }
        });
        SearchinRecycle normal = new SearchinRecycle(privatedata.getJsonurl(),"");
        jsonTask.execute(normal);
        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createprogressdialog(500);
                String val = entervalue.getText().toString().trim();
                SearchinRecycle searchkeyword = new SearchinRecycle(privatedata.getJsonurl(),val);
                JsonTask jsonTask =new JsonTask(new AsyncResponse(){
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void processFinish(List<Applicable> output) {
                        List<Applicable> temp = new ArrayList<>();
                        temp.addAll(output);
                        gatewayAdapter = new GatewayAdapter(getApplicationContext(),temp);
                        recyclerView.setAdapter(gatewayAdapter);
                        gatewayAdapter.notifyDataSetChanged();

                    }
                });
                jsonTask.execute(searchkeyword);
                //Toast.makeText(MainActivity.this, "Find Button Entered", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initialize()
    {
        recyclerView = findViewById(R.id.recycleview);
        entervalue = findViewById(R.id.Gatewaynameedittext);
        enterbutton = findViewById(R.id.enter);
        privatedata = new Privatedata();
        listdata = new ArrayList<>();
        pd = new ProgressDialog(getApplicationContext());
    }

    private void createprogressdialog(int timer)
    {
        pd= ProgressDialog.show(this,"Doing something", "Please wait....",true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timer);
                    pd.dismiss();
                }
                catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }


}