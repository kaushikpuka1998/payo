package com.kgstrivers.payoneer.Interface;

import com.kgstrivers.payoneer.Models.Applicable;

import java.util.List;

public interface AsyncResponse {
    void processFinish(List<Applicable> output);
}
