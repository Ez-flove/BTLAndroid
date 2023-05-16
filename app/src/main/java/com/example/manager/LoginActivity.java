package com.example.manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manager.DB.EmployeeDBHelper;
import com.example.manager.DB.GradeDBHelper;
import com.example.manager.DB.ManagermentDBHelper;
import com.example.manager.helpers.Alert;
import com.example.manager.models.Session;
import com.example.manager.models.Managerment;

public class LoginActivity extends AppCompatActivity {
    Session session;
    EditText txtUsername, txtPassword;
    AppCompatButton btnSignIn, btnRegister;
    ManagermentDBHelper db = new ManagermentDBHelper(this);
    Boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(LoginActivity.this);
        checkAuth();
        setControl();
        setEvent();
    }

    private void checkAuth() {
        Managerment gv = ((App) LoginActivity.this.getApplication()).getManagerment();
        if (gv == null) return;
        gotoHome();
    }

    private void setControl() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if (username.isEmpty()) {
                    txtUsername.setError("Hãy nhập username!");
                    return;
                } else if (password.isEmpty()) {
                    txtPassword.setError("Hãy nhập password!");
                    return;
                }
                // khởi tạo alert
                Alert alert = new Alert(LoginActivity.this);
                alert.normal();

                // lấy Data từ csdl dựa trên input
                Managerment gv = db.getManagerment(Integer.parseInt(username));

                // Kiểm tra login
                if (gv == null) {
                    isLogin = false;
                    alert.showAlert("Tài khoản không tồn tại!", R.drawable.info_icon);
                } else if (!gv.getPassword().equals(password)) {
                    isLogin = false;
                    alert.showAlert("Sai mật khẩu!", R.drawable.info_icon);
                } else {
                    isLogin = true;
                    // set biến toàn cục
                    ((App) LoginActivity.this.getApplication()).setManagerment(gv);

                    session.set("managermentName", gv.getName());
                    session.set("managermentId", String.valueOf(gv.getId()));

//                    alert.showAlert("Đăng nhập thành công!", R.drawable.check_icon);
                }
                if (isLogin) {
                            gotoHome();
                        }
                alert.btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isLogin) {
                            gotoHome();
                        }
                        alert.dismiss();
                    }
                });

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagermentDBHelper dbDemoManagerment = new ManagermentDBHelper(LoginActivity.this);
                dbDemoManagerment.deleteAndCreatTable();

                GradeDBHelper dbDemoGrade = new GradeDBHelper(LoginActivity.this);
                dbDemoGrade.deleteAndCreatTable();

                EmployeeDBHelper dbDemoEmployee = new EmployeeDBHelper(LoginActivity.this);
                dbDemoEmployee.deleteAndCreateTable();
//
//        SubjectDBHelper dbDemoSubject = new SubjectDBHelper(LoginActivity.this);
//        dbDemoSubject.deleteAndCreateTable();
//
//        ScoreDBHelper scoreDBHelper = new ScoreDBHelper(LoginActivity.this);
//        scoreDBHelper.deleteAndCreateTable();
//
//        EventDBHelper eventDBHelper = new EventDBHelper(LoginActivity.this);
//        eventDBHelper.deletedAndCreateTable();


                Toast.makeText(LoginActivity.this, "Tạo dữ liệu mẫu thành công !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gotoHome() {
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(i);
    }
}