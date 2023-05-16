package com.example.manager.department;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.manager.DB.EmployeeDBHelper;
import com.example.manager.DB.GradeDBHelper;
import com.example.manager.R;
import com.example.manager.adapter.DepartmentListViewModel;
import com.example.manager.models.Employee;
import com.example.manager.models.Grade;
import com.example.manager.models.Session;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

public class DepartmentActivity extends AppCompatActivity {
    public static WeakReference<DepartmentActivity> weakActivity;

    Session session;

    ListView listView;
    ArrayList<Employee> objects = new ArrayList<>();
    DepartmentListViewModel listViewModel;

    ArrayList<Grade> gradeObjects;
    GradeDBHelper gradeOpenHelper = new GradeDBHelper(this);
    EmployeeDBHelper employeeDBHelper = new EmployeeDBHelper(this);


    AppCompatButton buttonCreation, buttonExport;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        weakActivity = new WeakReference<>(DepartmentActivity.this);
        session = new Session(DepartmentActivity.this);
        /*The command line belows that make sure that keyboard only pops up only if user clicks into EditText */
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*Step 2*/
        gradeObjects = gradeOpenHelper.retrieveAllGrades();
        objects = employeeDBHelper.retrieveAllEmployees();

        /*Step 3*/
        setControl();
        /*Step 4*/
        setEvent();
        searchByKeyword();
        /*Step 5*/
        String managermentId = session.get("managermentId");
        String value = gradeOpenHelper.retriveIdByManagermentId(managermentId);
        session.set("gradeId", value );
    }


    public static DepartmentActivity getmInstanceActivity() {
        return weakActivity.get();
    }

    private void setControl() {
        listView = findViewById(R.id.classroomListView);
        buttonCreation = findViewById(R.id.classroomButtonCreation);
        searchView = findViewById(R.id.classroomSearchView);
    }

    private void setEvent() {
        listViewModel = new DepartmentListViewModel(this, R.layout.activity_department_element, objects);
        listView.setAdapter(listViewModel);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Employee employee = (Employee) adapterView.getAdapter().getItem(position);

                /*Open the screen which shows student's detail*/
                Intent intent = new Intent(DepartmentActivity.this, DepartmentIndividuaActivity.class);
//                /*Pass student object to next activity - Student class must implements Serializable*/
                intent.putExtra("employee", employee);
//                /*start next activity*/
                startActivity(intent);
            }
        });

        buttonCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentActivity.this, DepartmentCreationActivity.class);
                startActivity(intent);
            }
        });
    }
    public void createEmployee(Employee employee)
    {
        /* Temporary Solution so as to the Grade Name is null */
        employee.setGradeName( objects.get(0).getGradeName() );
        /*Step 1*/ objects.add(employee);

        /*Step 2*/ employeeDBHelper.create(employee);

        /*Step 3*/ listViewModel.notifyDataSetChanged();

        /*Step 4*/ Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show();
    }
    public void deleteEmployee(Employee employee)
    {
        if( employee.getId() == 0 ) {
            Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_LONG).show();
            return;
        }
        /*Step 1*/
        for(int i = 0; i < objects.size(); i++)
        {
            if( objects.get(i).getId() == employee.getId())
            {
                objects.remove( objects.get(i));
            }
        }
        /*Step 2*/ listViewModel.notifyDataSetChanged();

        /*Step 3*/ employeeDBHelper.delete(employee);

        /*Step 4*/ Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show();
    }
    public void updateEmployee(Employee employee)
    {
        if( employee.getId() == 0 ) {
            Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_LONG).show();
            return;
        }
        /*Step 1*/
        for (Employee element: objects) {
            if(element.getId() == employee.getId())
            {
                element.setFamilyName( employee.getFamilyName() );
                element.setFirstName( employee.getFirstName() );

                element.setGender( employee.getGender() );
                element.setBirthday( employee.getBirthday() );

                element.setGradeId( employee.getGradeId() );
                element.setGradeName( employee.getGradeName() );
            }
        }

        /*Step 2*/ listViewModel.notifyDataSetChanged();

        /*Step 3*/ employeeDBHelper.update(employee);

        /*Step 4*/ Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
    }
    private void searchByKeyword() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Employee> array = new ArrayList<>();

                for (Employee employee: objects) {
                    String firstName = employee.getFirstName().toLowerCase(Locale.ROOT);
                    String keyword = s.toLowerCase(Locale.ROOT);

                    if( firstName.contains(keyword) )
                    {
                        array.add(employee);
                    }
                }
                setListView(array);

                return false;
            }
        });
    }
    private void setListView(ArrayList<Employee> array)
    {
        listViewModel = new DepartmentListViewModel(this, R.layout.activity_department_element, array);
        listView.setAdapter(listViewModel);
        listViewModel.notifyDataSetChanged();
    }
}