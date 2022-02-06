package com.kgstrivers.payoneer.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kgstrivers.payoneer.Models.Applicable;
import com.kgstrivers.payoneer.Repositories.FirstPageRepository;
import java.util.List;

public class FirstpageViewModel extends ViewModel {
    private MutableLiveData<List<Applicable>> applicabledata;
    private FirstPageRepository repo;
    public void init()
    {
        if(applicabledata!=null)
        {
            return;
        }
        repo = FirstPageRepository.getInstance();
        applicabledata = repo.getApplicableList();
    }

    public LiveData<List<Applicable>> getApplicable()
    {
        return applicabledata;
    }
    public LiveData<List<Applicable>> searchApplicable(String value)
    {
        applicabledata = repo.getApplicableSearch(value);
        return applicabledata;
    }
}
