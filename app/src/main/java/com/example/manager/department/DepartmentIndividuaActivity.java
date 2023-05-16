package com.example.manager.department;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.manager.R;
import com.example.manager.helpers.Alert;
import com.example.manager.models.Employee;

import java.lang.ref.WeakReference;

public class DepartmentIndividuaActivity extends AppCompatActivity {
    public static WeakReference<DepartmentIndividuaActivity> weakActivity;

    TextView employeeFamilyName, employeeFirstName, employeeGradeName, employeeBirthday, employeeGender, contentAlert;
    Button buttonUpdate, buttonDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_individua);
        weakActivity = new WeakReference<>(DepartmentIndividuaActivity.this);
        setControl();

        setScreen();
        setEvent();
    }

    private void setControl() {
        employeeFamilyName = findViewById(R.id.studentFamilyName);
        employeeFirstName = findViewById(R.id.studentFirstName);

        employeeGradeName = findViewById(R.id.gradeName);
        employeeBirthday = findViewById(R.id.birthday);
        
        buttonUpdate = findViewById(R.id.individualButtonUpdate);
        buttonDelete = findViewById(R.id.individualButtonDelete);

        employeeGender = findViewById(R.id.gender);
    }
    private void setScreen() {
        Employee employee = (Employee) getIntent().getSerializableExtra("employee");

        String familyName = employee.getFamilyName();
        String firstName = employee.getFirstName();
        String birthday = employee.getBirthday();
        String gradeName = employee.getGradeName();

        /*Step 2*/
        employeeFamilyName.setText(familyName);
        employeeFirstName.setText(firstName);
        employeeBirthday.setText(birthday);
        employeeGradeName.setText(gradeName);

        if( employee.getGender() == 0 )
        {
            employeeGender.setText("Nam");
        }
        else
        {
            employeeGender.setText("Nữ");
        }
    }
    private void setEvent() {
        Employee employee = (Employee) getIntent().getSerializableExtra("employee");


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thông báo xóa!");
                builder.setTitle("Bạn có chắc chắn muốn xóa " + employee.getFirstName() + " không?");
                builder.setIcon(R.drawable.info_icon);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DepartmentActivity.getmInstanceActivity().deleteEmployee(employee);
                        finish();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        buttonUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(DepartmentIndividuaActivity.this, DepartmentUpdateActivity.class);
            intent.putExtra("updatedEmployee",employee );
            startActivity((intent));
        });
    }
    public static DepartmentIndividuaActivity getmInstanceActivity() {
        return weakActivity.get();
    }
    public void updateEmployee(Employee employee)
    {
        employeeFamilyName.setText(employee.getFamilyName());
        employeeFirstName.setText(employee.getFirstName());
        employeeBirthday.setText(employee.getBirthday());
        employeeGradeName.setText(employee.getGradeName());

        if( employee.getGender() == 0 )
        {
            employeeGender.setText("Nam");
        }
        else
        {
            employeeGender.setText("Nữ");
        }
    }
}