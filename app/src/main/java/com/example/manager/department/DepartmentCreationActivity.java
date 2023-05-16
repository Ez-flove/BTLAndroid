package com.example.manager.department;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.manager.R;
import com.example.manager.models.Employee;
import com.example.manager.models.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DepartmentCreationActivity extends AppCompatActivity {
    Session session;
    EditText familyName, firstName, birthday;
    RadioButton male, female;
    AppCompatButton buttonConfirm, buttonCancel;
    ImageButton buttonBirthday;
    private final Calendar cal = Calendar.getInstance();
    private final int year = cal.get(Calendar.YEAR);
    private final int month = cal.get(Calendar.MONTH) + 1;
    private final int day = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_creation);
        session = new Session(DepartmentCreationActivity.this);
        setControl();
        setEvent();

        String today = day + "/" + month + "/" + year;
        birthday.setText("01/05/2001");
    }

    private void setControl() {
        buttonConfirm = findViewById(R.id.classroomCreationButtonConfirm);
        buttonCancel = findViewById(R.id.classroomCreationButtonGoBack);


        familyName = findViewById(R.id.classroomCreationFamilyName);
        firstName = findViewById(R.id.classroomCreationFirstName);

        male = findViewById(R.id.classroomCreationRadioButtonMale);
        female = findViewById(R.id.classroomCreationRadioButtonFemale);

        buttonBirthday = findViewById(R.id.classroomCreationButtonBirthday2);
        birthday = findViewById(R.id.classroomCreationBirthday);
    }

    private void setEvent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            birthday.setOnClickListener(this::openDatePicker);
        }
        buttonConfirm.setOnClickListener(view -> {

            int gender = male.isChecked() ? 0 : 1;
            int gradeId = Integer.parseInt(session.get("gradeId"));

            Employee employee = new Employee();
            employee.setFamilyName(familyName.getText().toString());
            employee.setFirstName(firstName.getText().toString());
            employee.setGender(gender);
            employee.setGradeId(gradeId);
            employee.setBirthday(birthday.getText().toString());

            boolean flag = validateEmployeeInformation(employee);
            if (!flag)
                return;

            DepartmentActivity.getmInstanceActivity().createEmployee(employee);
            finish();
        });

        buttonCancel.setOnClickListener(view -> finish());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    private boolean validateEmployeeInformation(Employee employee) {

        String VIETNAMESE_DIACRITIC_CHARACTERS
                = "ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ" +
                "áảấẩắẳóỏốổớởíỉýỷéẻếểạậặọộợịỵẹệãẫẵõỗỡĩỹẽễàầằòồờìỳèềaâăoôơiyeêùừụựúứủửũữuư";

        Pattern pattern = Pattern.compile("(?:[" + VIETNAMESE_DIACRITIC_CHARACTERS + "]|[a-zA-Z])++");

        boolean flagFamilyName = pattern.matcher(employee.getFamilyName()).matches();
        boolean flagFirstName = pattern.matcher(employee.getFirstName()).matches();

        if (!flagFamilyName || !flagFirstName) {
            Toast.makeText(DepartmentCreationActivity.this, "Nội dung nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
            return false;
        }

        int yearBirhday = Integer.parseInt(employee.getBirthday().substring(6));
        int flagAge = year - yearBirhday;
        if (flagAge < 18) {
            Toast.makeText(DepartmentCreationActivity.this, "Tuổi không nhỏ hơn 18", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}