package com.example.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;

import com.example.manager.Event.EventActivity;
import com.example.manager.department.DepartmentActivity;
import com.example.manager.models.Managerment;

import java.lang.ref.WeakReference;

public class TopBarHomeMenuFragment extends Fragment {
    private TextView txtNameGV, txtIDGV;
    private ImageButton btnMenu;
    private App appState;
    Context context;
    public static WeakReference<TopBarHomeMenuFragment> weakActivity;

    public static TopBarHomeMenuFragment getmInstanceActivity() {
        return weakActivity.get();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_bar_menu_icon, container, false);
        weakActivity = new WeakReference<>(TopBarHomeMenuFragment.this);

        txtNameGV = view.findViewById(R.id.txtNameGV);
        txtIDGV = view.findViewById(R.id.txtIDGV);
        btnMenu = view.findViewById(R.id.btnMenu);

        setEvent();

        getData();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        Managerment gv = appState.getManagerment();
        txtNameGV.setText(gv.getName());
        txtIDGV.setText("Mã quản lí: " + gv.getId());
    }

    public void setData(Managerment managerment) {
        appState.setManagerment(managerment);
        getData();
    }

    @SuppressLint("RestrictedApi")
    private void setEvent() {
        MenuBuilder menuBuilder = new MenuBuilder(context);
        MenuInflater inflater = new MenuInflater(context);

        inflater.inflate(R.menu.menu_home_sidebar, menuBuilder);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPopupHelper menuElement = new MenuPopupHelper(context, menuBuilder, view);
                menuElement.setForceShowIcon(true);
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.classroom:
                                intent = new Intent(context, DepartmentActivity.class);
                                startActivity(intent);
                                return true;
//                            case R.id.subject:
//                                intent = new Intent(context, SubjectActivity.class);
//                                startActivity(intent);
//                                return true;
                            case R.id.event:
                                intent = new Intent(context, EventActivity.class);
                                startActivity(intent);
                                return true;
//                            case R.id.mark:
//                                intent = new Intent(context, ScoreSubjectActivity.class);
//                                startActivity(intent);
//                                return true;
//                            case R.id.statistics:
//                                intent = new Intent(context, StatisticActivity.class);
//                                startActivity(intent);
//                                return true;
//                            case R.id.settings:
//                                intent = new Intent(context, SettingsActivity.class);
//                                startActivity(intent);
//                                return true;
                            case R.id.signout:
                                if (appState.getManagerment() != null) {
                                    appState.setManagerment(null);
                                }
                                intent = new Intent(context, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(context, "Đăng xuất thành công !", Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                throw new IllegalStateException("Unexpected value: " + item.getItemId());
                        }
                    }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                menuElement.show();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appState = (App) getActivity().getApplication();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
