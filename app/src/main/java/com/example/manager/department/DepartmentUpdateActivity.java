package com.example.manager.department;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.manager.R;
import com.example.manager.models.Employee;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DepartmentUpdateActivity extends AppCompatActivity {
    EditText familyName, firstName, birthday;
    RadioButton male, female;

    ImageButton buttonBirthday;
    AppCompatButton buttonConfirm;

    private final Calendar cal = Calendar.getInstance();
    private final int year = cal.get(Calendar.YEAR);
    private final int month = cal.get(Calendar.MONTH) + 1;
    private final int day = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_update);
        setControl();
        setScreen();
        setEvent();
    }

    private void setControl() {
        buttonBirthday = findViewById(R.id.classroomUpdateButtonBirthday);
        buttonConfirm = findViewById(R.id.classroomUpdateButtonConfirm);

        familyName = findViewById(R.id.classroomUpdateFamilyName);
        firstName = findViewById(R.id.classroomUpdateFirstName);
        birthday = findViewById(R.id.classroomUpdateBirthday);

        male = findViewById(R.id.classroomUpdateRadioButtonMale);
        female = findViewById(R.id.classroomUpdateRadioButtonFemale);
    }

    private void setScreen() {
        Employee employee = (Employee) getIntent().getSerializableExtra("updatedEmployee");

        /*Step 2*/
        familyName.setText(employee.getFamilyName());
        firstName.setText(employee.getFirstName());

        if (employee.getGender() == 0) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
        birthday.setText(employee.getBirthday());
    }

    public void openDatePicker(View view) {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                birthday.setText(dd + "/" + (mm + 1) + "/" + yy);
            }
        }, y, m, d);
        dialog.show();
    }

    private void setEvent() {
        /*Step 1*/
        Employee employee = (Employee) getIntent().getSerializableExtra("updatedEmployee");

        /*Step 2*/
        Employee myEmployee = new Employee();

        /*Step 3*/
        birthday.setOnClickListener(this::openDatePicker);
        buttonConfirm.setOnClickListener(view ->
        {
            int id = employee.getId();
            int gradeId = employee.getGradeId();

            String gradeName = employee.getGradeName();
            String family = familyName.getText().toString();

            String first = firstName.getText().toString();
            int gender = male.isChecked() ? 0 : 1;
            String birth = birthday.getText().toString();

            myEmployee.setId(id);
            myEmployee.setFamilyName(family);

            myEmployee.setFirstName(first);
            myEmployee.setGender(gender);

            myEmployee.setBirthday(birth);
            myEmployee.setGradeId(gradeId);
            myEmployee.setGradeName(gradeName);

            boolean flag = validateEmployeeInformation(myEmployee);
            if (!flag)
                return;

            DepartmentActivity.getmInstanceActivity().updateEmployee(myEmployee);
            DepartmentIndividuaActivity.getmInstanceActivity().updateEmployee(myEmployee);
        });
    }

    private boolean validateEmployeeInformation(Employee employee) {

        String VIETNAMESE_DIACRITIC_CHARACTERS
                = "ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ" +
                "áảấẩắẳóỏốổớởíỉýỷéẻếểạậặọộợịỵẹệãẫẵõỗỡĩỹẽễàầằòồờìỳèềaâăoôơiyeêùừụựúứủửũữuư";

        Pattern pattern = Pattern.compile("(?:[" + VIETNAMESE_DIACRITIC_CHARACTERS + "]|[a-zA-Z])++");

        boolean flagFamilyName = pattern.matcher(employee.getFamilyName()).matches();
        boolean flagFirstName = pattern.matcher(employee.getFirstName()).matches();

        if (!flagFamilyName || !flagFirstName) {
            Toast.makeText(DepartmentUpdateActivity.this, "Nội dung nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
            return false;
        }

        int yearBirhday = Integer.parseInt(employee.getBirthday().substring(6));
        int flagAge = year - yearBirhday;
        if (flagAge < 18) {
            Toast.makeText(DepartmentUpdateActivity.this, "Tuổi không nhỏ hơn 18", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}