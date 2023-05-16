package com.example.manager.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.manager.R;
import com.example.manager.department.DepartmentActivity;
import com.example.manager.department.DepartmentIndividuaActivity;
import com.example.manager.models.Employee;


import java.util.ArrayList;

public class DepartmentListViewModel extends ArrayAdapter<Employee> {

    Context context;
    int resource;
    ArrayList<Employee> objects;

    public DepartmentListViewModel(@NonNull Context context, int resource, @NonNull ArrayList<Employee> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public int count()
    {
        return  objects.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);

        /*Step 1*/
        ImageView avatar = convertView.findViewById(R.id.studentAvatar);
        TextView name = convertView.findViewById(R.id.studentName);
        TextView grade = convertView.findViewById(R.id.studentGrade);

        /*Step 2*/
        Employee employee = objects.get(position);
        String employeeName = employee.getFamilyName() + " " + employee.getFirstName();
        String employeeGrade = employee.getGradeName();

        /*Step 3*/
        name.setText(employeeName);
        grade.setText(employeeGrade);

        /*Step 4*/
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Open the screen which shows student's detail*/
                Intent intent = new Intent(context, DepartmentIndividuaActivity.class);
//                /*Pass student object to next activity - Student class must implements Serializable*/
                intent.putExtra("employee", employee );
//                /*start next activity*/
                ((DepartmentActivity)context).startActivity(intent);
            }
        });

        return convertView;
    }
}
