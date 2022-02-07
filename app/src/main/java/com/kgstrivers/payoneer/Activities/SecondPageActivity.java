package com.kgstrivers.payoneer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.Models.InputElement;
import com.kgstrivers.payoneer.R;

import java.util.List;

public class SecondPageActivity extends AppCompatActivity {


    EditText et1,et2,et3,et4,et5;
    String Inputelemets[]= {"number","expiryMonth","expiryYear","verificationCode","holderName","bic","iban"};
    String Inputelemetstype[]= {"number","integer","integer","integer","string","string","string"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        initialize();
        Intent i =getIntent();
        Applicable applicable =i.getParcelableExtra("Applicable");
        int id = (int) i.getSerializableExtra("id");

        if(id == 0 || id == 1 || id == 2 || id == 4 || id == 5 ||id == 13||id == 14||id == 15 ||id == 16)
        {
            enableandsethint(et1,Inputelemets[0],Inputelemetstype[0]);
            enableandsethint(et2,Inputelemets[1],Inputelemetstype[1]);
            enableandsethint(et3,Inputelemets[2],Inputelemetstype[2]);
            enableandsethint(et4,Inputelemets[3],Inputelemetstype[3]);
            enableandsethint(et5,Inputelemets[4],Inputelemetstype[4]);
        }
        else if(id == 3)
        {
            enableandsethint(et1,Inputelemets[5],Inputelemetstype[5]);
        }
        else if(id == 6)
        {
            enableandsethint(et1,Inputelemets[0],Inputelemetstype[0]);
            enableandsethint(et2,Inputelemets[2],Inputelemetstype[2]);
            enableandsethint(et3,Inputelemets[1],Inputelemetstype[1]);
        }
        else if(id == 9)
        {
            enableandsethint(et1,Inputelemets[4],Inputelemetstype[4]);
            enableandsethint(et2,Inputelemets[0],Inputelemetstype[0]);
            enableandsethint(et3,Inputelemets[2],Inputelemetstype[2]);
            enableandsethint(et4,Inputelemets[1],Inputelemetstype[1]);
            enableandsethint(et5,Inputelemets[3],Inputelemetstype[3]);
        }
        else if(id == 11)
        {
            enableandsethint(et1,Inputelemets[4],Inputelemetstype[4]);
            enableandsethint(et2,Inputelemets[6],Inputelemetstype[6]);
            enableandsethint(et3,Inputelemets[5],Inputelemetstype[5]);
        }
        Toast.makeText(this,applicable.getLabel()+id , Toast.LENGTH_SHORT).show();


    }
    private void enableandsethint(EditText et,String hint,String inputtype)
    {
        et.setVisibility(View.VISIBLE);
        if(inputtype == "string")
        {
            et.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        else
        {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if(hint == "expiryYear" || hint == "expiryMonth" )
        {
            et.setInputType(InputType.TYPE_CLASS_DATETIME);
        }

        et.setHint(hint);
    }

    private void initialize()
    {
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 =  findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
    }
}