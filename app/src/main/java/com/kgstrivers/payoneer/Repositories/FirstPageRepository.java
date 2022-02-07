package com.kgstrivers.payoneer.Repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.kgstrivers.payoneer.Interface.AsyncResponse;
import com.kgstrivers.payoneer.JsonTask;
import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.privatedetails.Privatedata;

import java.util.ArrayList;
import java.util.List;

public class FirstPageRepository {
    private static FirstPageRepository instance;
    Privatedata privatedata = new Privatedata();
    List<Applicable> dataset = new ArrayList<>();
    public static FirstPageRepository getInstance()
    {
        if(instance == null)
        {
            instance = new FirstPageRepository();
        }
        return instance;
    }
    public MutableLiveData<List<Applicable>> getApplicableList()
    {
        MutableLiveData<List<Applicable>> data = new MutableLiveData<>();
        JsonTask jsonTask =new JsonTask(new AsyncResponse(){
            @Override
            public void processFinish(List<Applicable> output) {
                dataset = output;
                data.setValue(output);
            }
        });
        //SearchinRecycle normal = new SearchinRecycle(privatedata.getJsonurl(),"");
        jsonTask.execute(privatedata.getJsonurl());
        Log.d("Networking call Working","Yes");
        return data;

    }
    public MutableLiveData<List<Applicable>> getApplicableSearch(String value)
    {
        MutableLiveData<List<Applicable>> data = new MutableLiveData<>();
        List<Applicable> searchedvalue=new ArrayList<>();
        if(value.equals(""))
        {
            searchedvalue = dataset;
        }else
        {
            for(int i=0;i<dataset.size();i++)
            {
                //Log.d("Value Done ",dataset.get(i).getCode().toLowerCase());
                if(dataset.get(i).getCode().toLowerCase().equals(value))
                {

                    Log.d("Value Done ",dataset.get(i).getCode().toLowerCase());
                    searchedvalue.add(dataset.get(i));
                }
            }
        }

        data.postValue(searchedvalue);
        Log.d("Searching Working: ","Yes");

        return data;
    }
}
