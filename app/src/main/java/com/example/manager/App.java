package com.example.manager;

import android.app.Application;

import com.example.manager.models.Managerment;

public class App extends Application {
    private Managerment gv = null;

    public Managerment getManagerment() {
        return gv;
    }

    public void setManagerment(Managerment gv) {
        this.gv = gv;
    }
}
