package com.example.manager.Event;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.manager.R;
import com.example.manager.models.Event;

import java.util.Calendar;

public class EventActivityCreation extends AppCompatActivity {
    Button saveEvent;
    Button troVeBT;
    EditText eventName, startEV,endEV,Place, date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        setControl();
        setEvent();
    }
    private void setEvent () {
        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EVname=eventName.getText().toString();
                String EVstart=startEV.getText().toString();
                String EVend=endEV.getText().toString();
                String EVdate=date1.getText().toString();
                String EVPlace=Place.getText().toString();

                Event event=new Event();
                event.setNameEvent(EVname);
                event.setStartTime(EVstart);
                event.setEndTime(EVend);
                event.setDay(EVdate);
                event.setPlace(EVPlace);

                EventActivity.weakReference.get().AddEvent(event);

                Intent intent=new Intent();
                intent.putExtra("Evupdate",event);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        troVeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(EventActivityCreation.this,"Thoat",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setControl () {
        saveEvent=findViewById(R.id.eventCreationButtonConfirm);
        troVeBT=findViewById(R.id.eventCreationButtonGoBack);
        eventName=findViewById(R.id.eventNameAdd);
        startEV=findViewById(R.id.StartEV);
        endEV=findViewById(R.id.endEV);
        date1=findViewById(R.id.dateEV);
        Place=findViewById(R.id.eventPlaceAdd);

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(EventActivityCreation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                        date1.setText(dd + "/" + (mm + 1) + "/" + yy);
                    }
                }, y, m, d);
                dialog.show();
            }
        });
        startEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivityCreation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int minu) {
                        startEV.setText(h+":"+minu);
                    }
                },hours,minute,false);
                timePickerDialog.show();
            }
        });
        endEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivityCreation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int minu) {
                        endEV.setText(h+":"+minu);
                    }
                },hours,minute,false);
                timePickerDialog.show();
            }
        });
    }
}