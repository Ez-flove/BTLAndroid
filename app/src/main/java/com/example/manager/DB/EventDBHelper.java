package com.example.manager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.manager.models.Event;

import java.util.ArrayList;

public class EventDBHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME="EVENT";
    private static final String TAG= "EVENT SQLLite";
    private  static final String COLUMN_idEvent="MASUKIEN";
    private static final String COLUMN_nameEvent="TENSUKIEN";
    private static final String COLUMN_startTime="BATDAU";
    private static final String COLUMN_endTime="KETTHUC";
    private static final String COLUMN_day="NGAYTHANG";
    private static final String COLUMN_place="DIADIEM";
    public EventDBHelper(@Nullable Context context) {
        super(context, DBConfig.getDatabaseName(), null, DBConfig.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE= String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT , %s TEXT , %s TEXT  )",
                TABLE_NAME, COLUMN_idEvent,COLUMN_nameEvent,COLUMN_startTime,COLUMN_endTime,COLUMN_day,COLUMN_place);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    // add event
    public boolean AddEvent(Event event){
        Log.i(TAG,"ADD EVENT " + event.getNameEvent());
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_nameEvent,event.getNameEvent());
        values.put(COLUMN_startTime,event.getStartTime());
        values.put(COLUMN_endTime,event.getEndTime());
        values.put(COLUMN_day,event.getDay());
        values.put(COLUMN_place,event.getPlace());
        try {
            sqLiteDatabase.insert(TABLE_NAME, null,values);
            sqLiteDatabase.close();
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    // default
    public void createDefaultEvent(){
        Event event1=new Event(0,"Họp quản lý dự án","07:00","11:00","15/04/2022","Phòng họp 1");
        Event event2=new Event(0,"Họp đánh giá sự cố","07:00","17:00","26/03/2022","Phòng họp 2");
        Event event3=new Event(0,"Interview Intern","00:00","24:00","14/02/2022","Phòng nhân sự");
        Event event4 = new Event(0, "Họp báo cáo tuần", "08:00", "09:00", "01/06/2022", "Phòng họp 3");
        Event event5 = new Event(0, "Họp khẩn", "14:00", "15:00", "10/07/2022", "Phòng họp 4");
        Event event6 = new Event(0, "Buổi giao lưu nhân viên", "16:00", "18:00", "25/08/2022", "Sảnh chính");
        Event event7 = new Event(0, "Họp quản lý nhân sự", "09:00", "12:00", "05/09/2022", "Phòng họp 5");
        Event event8 = new Event(0, "Hội thảo kỹ thuật", "13:30", "16:30", "20/10/2022", "Hội trường");


        this.AddEvent(event1);
        this.AddEvent(event2);
        this.AddEvent(event3);
        this.AddEvent(event4);
        this.AddEvent(event5);
        this.AddEvent(event6);
        this.AddEvent(event7);
        this.AddEvent(event8);

    }
    //getALl
    public ArrayList<Event> getAllEvents(){
        Log.i(TAG, "getAllEvent " );
        ArrayList<Event> eventList= new ArrayList<Event>();
        String selectQuery="SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase database= this.getReadableDatabase();
        Cursor cursor= database.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                try {
                    Event event= new Event();
                    event.setIdEvent(Integer.parseInt(cursor.getString(0)));
                    event.setNameEvent(cursor.getString(1));
                    event.setStartTime(cursor.getString(2));
                    event.setEndTime(cursor.getString(3));
                    event.setDay(cursor.getString(4));
                    event.setPlace(cursor.getString(5));
                    eventList.add(event);
                }catch (Exception exception){
                    Log.i(TAG,"getALLEvent Error");
                }

            }while (cursor.moveToNext());
        }
        return eventList;
    }
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
    public void deletedAndCreateTable(){
        SQLiteDatabase database =this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(database);
        createDefaultEvent();
    }
    public boolean UpdateEvent(Event event){
        Log.i(TAG,"UPDATE EVENT " + event.getNameEvent());
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_nameEvent,event.getNameEvent());
        values.put(COLUMN_startTime,event.getStartTime());
        values.put(COLUMN_endTime,event.getEndTime());
        values.put(COLUMN_day,event.getDay());
        values.put(COLUMN_place,event.getPlace());

        try {
            sqLiteDatabase.update(TABLE_NAME, values, "MASUKIEN= ?", new String[]{event.getIdEvent()+""});
            sqLiteDatabase.close();
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    public boolean DeleteEvent(Event event){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "MASUKIEN= ?", new String[]{event.getIdEvent()+""}) >0;
    }
}
