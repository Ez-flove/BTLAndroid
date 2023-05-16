package com.example.manager.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.manager.models.ReportTotal;
import com.example.manager.models.Employee;

import java.util.ArrayList;

public class EmployeeDBHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "EMPLOYEE";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FAMILY_NAME = "familyName";

    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_GENDER = "gender";

    private static final String COLUMN_BIRTHDAY = "birthday";
    private static final String COLUMN_GRADE_ID = "gradeId";

    public EmployeeDBHelper(@Nullable Context context) {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format("CREATE TABLE %s (" +
                                "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "%s TEXT, " +
                                "%s TEXT, " +
                                "%s INTEGER, " +
                                "%s TEXT, " +
                                "%s INTEGER)",
                        TABLE_NAME, COLUMN_ID, COLUMN_FAMILY_NAME, COLUMN_FIRST_NAME, COLUMN_GENDER, COLUMN_BIRTHDAY, COLUMN_GRADE_ID);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // insert student
    public void create(Employee employee) {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        /*Step 2*/
        String familyName = employee.getFamilyName();
        String firstName = employee.getFirstName();

        int gender = employee.getGender();
        String birthday = employee.getBirthday();
        int gradeId = employee.getGradeId();

        /*Step 3*/
        contentValues.put(COLUMN_FAMILY_NAME, familyName);
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_BIRTHDAY, birthday);
        contentValues.put(COLUMN_GRADE_ID, gradeId);

        /*Step 4*/
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    // delete
    public void delete(Employee employee) {
        /*Step 1*/
        int id = employee.getId();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        /*Step 2*/
        sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    //update
    public void update(Employee employee) {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        /*Step 2*/
        int id = employee.getId();
        String familyName = employee.getFamilyName();
        String firstName = employee.getFirstName();

        int gender = employee.getGender();
        String birthday = employee.getBirthday();
        int gradeId = employee.getGradeId();

        /*Step 3*/
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAMILY_NAME, familyName);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_BIRTHDAY, birthday);
        values.put(COLUMN_GRADE_ID, gradeId);

        /*Step 4*/
        sqLiteDatabase.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    //get all student
    public ArrayList<Employee> retrieveAllEmployees() {
        /*Step 1*/
        ArrayList<Employee> objects = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        /*Step 2*/
        String query = "SELECT s.*, g.name FROM employee s INNER JOIN grade g ON s.gradeId = g.id";
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        /*Step 3*/
        if (cursor.moveToFirst()) // move pointer to first
        {
            do {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(cursor.getString(0)));

                employee.setFamilyName(cursor.getString(1));
                employee.setFirstName(cursor.getString(2));

                employee.setGender(Integer.parseInt(cursor.getString(3)));
                employee.setBirthday(cursor.getString(4));

                employee.setGradeId(Integer.parseInt(cursor.getString(5)));
                employee.setGradeName(cursor.getString(6));

                objects.add(employee);
            } while (cursor.moveToNext());
        }

        return objects;
    }

    // get student trong 1 grade
    public ArrayList<Employee> getEmployeeInGrade(String grade) {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Employee> objects = new ArrayList<>();

        /*Step 2*/
        String query = "SELECT s.*" +
                "FROM employee s " +
                "WHERE s.gradeid =  " + grade;
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        /*Step 3*/
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();

                employee.setId(Integer.parseInt(cursor.getString(0)));

                employee.setFamilyName(cursor.getString(1));
                employee.setFirstName(cursor.getString(2));

                employee.setGender(Integer.parseInt(cursor.getString(3)));
                employee.setBirthday(cursor.getString(4));

                employee.setGradeId(Integer.parseInt(cursor.getString(5)));

                objects.add(employee);
            } while (cursor.moveToNext());
        }

        return objects;
    }

    //search student by Key
    public ArrayList<Employee> retrieveEmployeeWithKeyword(String keyword) {
        /*Step 1*/
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Employee> objects = new ArrayList<>();

        /*Step 2*/
        String query = "SELECT s.*, g.name " +
                "FROM employee s " +
                "INNER JOIN grade g " +
                "ON s.gradeId = g.id " +
                "WHERE s.familyName LIKE '" + keyword + "%' " +
                "OR s.firstName LIKE '" + keyword + "%' ";
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        /*Step 3*/
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();

                employee.setId(Integer.parseInt(cursor.getString(0)));

                employee.setFamilyName(cursor.getString(1));
                employee.setFirstName(cursor.getString(2));

                employee.setGender(Integer.parseInt(cursor.getString(3)));
                employee.setBirthday(cursor.getString(4));

                employee.setGradeId(Integer.parseInt(cursor.getString(5)));
                employee.setGradeName(cursor.getString(6));

                objects.add(employee);
            } while (cursor.moveToNext());
        }

        return objects;
    }

    // count number of grade
    private int count() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        int quantity = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return quantity;
    }

    //count by gender
    public ArrayList<ReportTotal> countByGender() {

        ArrayList<ReportTotal> objects = new ArrayList<>();

        String query = String.format("SELECT %s, count(id) FROM %s GROUP BY %s", COLUMN_GENDER, TABLE_NAME, COLUMN_GENDER);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Integer gender = cursor.getInt(0);
                Double value = cursor.getDouble(1);
                objects.add(new ReportTotal(gender == 0 ? "Nam" : "Nữ", value));
            }
            while (cursor.moveToNext());
        }

        return objects;
    }

    // delete
    public void deleteAndCreateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        createDefaultRecords();
    }

    private void createDefaultRecords() {
        /*Step 1*/
        int employeeQuantity = this.count();
        if (employeeQuantity != 0)
            return;

        /*Step 2*/
        Employee employee1 = new Employee("Thanh", "Ngoc", 1, "24/07/2001", 1);
        Employee employee2 = new Employee("Dang", "Hau", 0, "20/01/2000", 1);
        Employee employee3 = new Employee("Dinh", "Khang", 0, "24/03/2000", 1);
        Employee employee4 = new Employee("Duc", "Thuan", 0, "12/03/2000", 1);
        Employee employee5 = new Employee("Van", "Chung", 0, "21/07/2000", 1);
        Employee employee6 = new Employee("Ngoc", "Thanh", 1, "12/01/2000", 2);
        Employee employee7 = new Employee("Thu", "Ha", 1, "30/08/2000", 1);
        Employee employee8 = new Employee("Truc", "Thy", 1, "04/12/2000", 2);
        Employee employee9 = new Employee("Truc", "Thy", 1, "04/12/2000", 1);
        Employee employee10 = new Employee("Nguyen", "Hoa", 0, "10/05/1998", 2);
        Employee employee11 = new Employee("Truc", "Thy", 1, "04/12/2000", 1);
        Employee employee12 = new Employee("Van", "Long", 1, "21/09/1995", 1);
        Employee employee13 = new Employee("Minh", "Tuan", 1, "15/07/1997", 2);
        Employee employee14 = new Employee("Huong", "Linh", 0, "12/09/1996", 2);
        Employee employee15 = new Employee("Minh", "Thang", 1, "22/11/1999", 1);
        Employee employee16 = new Employee("Thu", "Trang", 0, "08/07/1997", 1);
        Employee employee17 = new Employee("Thanh", "Hai", 1, "31/03/1995", 2);
        Employee employee18 = new Employee("Trung", "Nhat", 1, "19/05/1998", 1);

        this.create(employee1);
        this.create(employee2);
        this.create(employee3);
        this.create(employee4);
        this.create(employee5);
        this.create(employee6);
        this.create(employee7);
        this.create(employee8);
        this.create(employee9);
        this.create(employee10);
        this.create(employee11);
        this.create(employee12);
        this.create(employee13);
        this.create(employee14);
        this.create(employee15);
        this.create(employee16);
        this.create(employee17);
        this.create(employee18);

    }
}
