package com.example.manager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.manager.models.Managerment;

import java.util.ArrayList;
import java.util.List;

public class ManagermentDBHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "MANAGERMENT";
    private static final String TAG = "SQLite";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";

    public ManagermentDBHelper(@Nullable Context context) {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                String.format(
                        "CREATE TABLE %s ( " +
                                "%s INTEGER PRIMARY KEY, " +
                                "%s TEXT, " +
                                "%s TEXT)", TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    // If Managerment table has no data
    // default, Insert 2 records.
    public void createDefaultManagermentsIfNeed()  {
        int count = this.getManagermentsCount();
        if(count == 0 ) {
            Managerment managerment1 = new Managerment(1, "Mai Xuân Ngọc", "123456");
            Managerment managerment2 = new Managerment(2, "Ronaldo", "123456");
            Managerment managerment3 = new Managerment(3, "Messi", "123456");
            this.addManagerment(managerment1);
            this.addManagerment(managerment2);
            this.addManagerment(managerment3);
        }
    }

    //insert table Managerment
    public void addManagerment(Managerment Managerment) {
        Log.i(TAG, "ManagermentDBHelper.addManagerment ... " + Managerment.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, Managerment.getId());
        values.put(COLUMN_NAME, Managerment.getName());
        values.put(COLUMN_PASSWORD, Managerment.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //get Managerment by Id
    public Managerment getManagerment(int id) {
        Log.i(TAG, "ManagermentDBHelper.getManagerment ... " + id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_PASSWORD }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        try {
            Log.i(TAG, "ManagermentDBHelper.getManagerment ... " + cursor.getString(2));
            Managerment Managerment = new Managerment(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            return Managerment;
        } catch(Exception ex){
            return null;
        }
    }

    //getAll Managerment
    public List<Managerment> getAllManagerments() {
        Log.i(TAG, "ManagermentDBHelper.getAllManagerments ... " );

        List<Managerment> managermentList = new ArrayList<Managerment>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Managerment Managerment = new Managerment();
                Managerment.setId(Integer.parseInt(cursor.getString(0)));
                Managerment.setName(cursor.getString(1));
                Managerment.setPassword(cursor.getString(2));
                // Adding Managerment to list
                managermentList.add(Managerment);
            } while (cursor.moveToNext());
        }
        // return Managerment list
        return managermentList;
    }

    // count
    public int getManagermentsCount() {
        Log.i(TAG, "ManagermentDBHelper.getManagermentsCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //update
    public int updateManagerment(Managerment Managerment) {
        Log.i(TAG, "ManagermentDBHelper.updateManagerment ... "  + Managerment.getName());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, Managerment.getId());
        values.put(COLUMN_NAME, Managerment.getName());
        values.put(COLUMN_PASSWORD, Managerment.getPassword());
        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(Managerment.getId())});
    }

    //delete
    public void deleteManagerment(Managerment Managerment) {
        Log.i(TAG, "ManagermentDBHelper.updateManagerment ... " + Managerment.getName() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(Managerment.getId()) });
        db.close();
    }
    public void deleteAndCreatTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        createDefaultManagermentsIfNeed();
    }
}
