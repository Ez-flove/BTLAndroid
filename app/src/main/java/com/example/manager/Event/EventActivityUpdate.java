package com.example.manager.Event;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.manager.DB.EventDBHelper;
import com.example.manager.R;
import com.example.manager.models.Event;

import java.util.Calendar;

public class EventActivityUpdate extends AppCompatActivity {

    Event event;
    EditText NameEv,StartEv,EndEV,DateEv,PlaceEV;
    Button ConfirmUpdate;
    EventDBHelper eventDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_update);
        event= (Event) getIntent().getSerializableExtra("EV");
        eventDBHelper=new EventDBHelper(this);
        setControl();
        setEvent();
    }

    private void setEvent() {
        ConfirmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.setNameEvent(NameEv.getText().toString().trim());
                event.setStartTime(StartEv.getText().toString());
                event.setEndTime(EndEV.getText().toString());
                event.setDay(DateEv.getText().toString());
                event.setPlace(PlaceEV.getText().toString());

                if (eventDBHelper.UpdateEvent(event)){
                    Toast.makeText(EventActivityUpdate.this,"Cap nhat thanh cong",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("Evupdate",event);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(EventActivityUpdate.this,"Cap nhat that bai",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void setControl() {
        NameEv= findViewById(R.id.eventNameupdate);
        StartEv=findViewById(R.id.StartEVupdate);
        EndEV=findViewById(R.id.endEVupdate);
        DateEv=findViewById(R.id.dateEVupdate);
        PlaceEV=findViewById(R.id.eventPlaceupdate);
        ConfirmUpdate=findViewById(R.id.eventUpdateButtonConfirm);

        NameEv.setText(event.getNameEvent());
        StartEv.setText(event.getStartTime());
        EndEV.setText(event.getEndTime());
        DateEv.setText(event.getDay());
        PlaceEV.setText(event.getPlace());
        DateEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(EventActivityUpdate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                        DateEv.setText(dd + "/" + (mm + 1) + "/" + yy);
                    }
                }, y, m, d);
                dialog.show();
            }
        });
        StartEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivityUpdate.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int minu) {
                        StartEv.setText(h+":"+minu);
                    }
                },hours,minute,false);
                timePickerDialog.show();
            }
        });
        EndEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivityUpdate.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int minu) {
                        EndEV.setText(h+":"+minu);
                    }
                },hours,minute,false);
                timePickerDialog.show();
            }
        });
    }

}