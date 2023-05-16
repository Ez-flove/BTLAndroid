package com.example.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.manager.models.Managerment;

public class TopBarHomeIconFragment extends Fragment {
    private TextView txtNameGV, txtIDGV;
    private ImageButton btnHome;
    private App appState;
    private AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_bar_home_icon, container, false);

        txtNameGV = view.findViewById(R.id.txtNameGV);
        txtIDGV = view.findViewById(R.id.txtIDGV);
        btnHome = view.findViewById(R.id.btnHome);

        setEvent();
        getData();

        return view;
    }

    private void getData() {
        Managerment gv = appState.getManagerment();
        txtNameGV.setText(gv.getName());
        txtIDGV.setText("Mã quản lí: " + gv.getId());
    }

    private void setEvent() {
        btnHome.setOnClickListener(view -> {
            Intent mainActivity = new Intent(activity, HomeActivity.class);
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivity);
            activity.finish();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appState = (App) getActivity().getApplication();
        activity = (AppCompatActivity) getActivity();
    }
}
