package com.example.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.manager.Event.EventActivity;
import com.example.manager.department.DepartmentActivity;

public class HomeActivity extends AppCompatActivity {
    ImageButton buttonHomeStatistic,
            buttonHomeClassroom,
            buttonHomeSubject,
            buttonHomeEvent,
            buttonHomeScore,
            buttonHomeAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setControl();
        setEvent();
    }

    private void setControl() {
        buttonHomeClassroom = findViewById(R.id.buttonHomeClassroom);

        buttonHomeEvent = findViewById(R.id.buttonHomeEvent);

//        buttonHomeAccount = findViewById(R.id.buttonHomeAccount);
    }
    private void setEvent() {
        buttonHomeClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DepartmentActivity.class);
                startActivity(intent);
            }
        });
        buttonHomeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });
    }
}