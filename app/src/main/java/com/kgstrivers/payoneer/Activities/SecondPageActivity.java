package com.kgstrivers.payoneer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.R;

public class SecondPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        Intent i =getIntent();
        Applicable applicable =i.getParcelableExtra("Applicable");
        Toast.makeText(this,applicable.getLabel() , Toast.LENGTH_SHORT).show();
    }
}