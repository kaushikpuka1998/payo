package com.kgstrivers.payoneer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kgstrivers.payoneer.Adapters.GatewayAdapter;
import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.R;
import com.kgstrivers.payoneer.ViewModels.FirstpageViewModel;
import com.kgstrivers.payoneer.privatedetails.Privatedata;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    ProgressDialog pd;
    RecyclerView recyclerView;
    GatewayAdapter gatewayAdapter;
    Button enterbutton,crossbutton;
    EditText entervalue;
    Privatedata privatedata;
    List<Applicable> listdata;
    FirstpageViewModel firstpageViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initialize();
        createprogressdialog(1000);
        firstpageViewModel = ViewModelProviders.of(this).get(FirstpageViewModel.class);
        firstpageViewModel.init();
        firstpageViewModel.getApplicable().observe(this, new Observer<List<Applicable>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Applicable> applicables) {
                recyclerviewinit(applicables);
            }
        });
        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String val = entervalue.getText().toString().trim().toLowerCase();

                if(val.length() == 0)
                {
                    crossbutton.setVisibility(View.INVISIBLE);
                    entervalue.setError("Please Enter Gateway Name");
                }else
                {
                    createprogressdialog(500);
                    crossbutton.setVisibility(View.VISIBLE);
                    firstpageViewModel.searchApplicable(val).observe(MainActivity.this, new Observer<List<Applicable>>() {
                        @Override
                        public void onChanged(List<Applicable> applicables) {
                            recyclerviewinit(applicables);
                        }
                    });
                }

                //Toast.makeText(MainActivity.this, "Find Button Entered", Toast.LENGTH_SHORT).show();
            }
        });

        crossbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entervalue.setText("");
                createprogressdialog(500);
                firstpageViewModel.searchApplicable("").observe(MainActivity.this, new Observer<List<Applicable>>() {
                    @Override
                    public void onChanged(List<Applicable> applicables) {
                        recyclerviewinit(applicables);
                    }
                });
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
        crossbutton = findViewById(R.id.cross);
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

    @SuppressLint("NotifyDataSetChanged")
    private void recyclerviewinit(List<Applicable>applicables)
    {
        gatewayAdapter = new GatewayAdapter(getApplicationContext(),applicables);
        LinearLayoutManager linearlayout = new LinearLayoutManager(getApplicationContext());
        linearlayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearlayout);
        recyclerView.setAdapter(gatewayAdapter);
        gatewayAdapter.notifyDataSetChanged();
    }


}