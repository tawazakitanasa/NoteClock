package com.example.noteclock.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.noteclock.model.Note;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NoteClock.db";
    private static int DATBASE_VERSION = 1;
    public SQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE notes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," + "" +
                "content TEXT," +
                "date TEXT," +
                "time TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(sqLiteDatabase);
    }
    //Lấy danh sách thông tin
    public List<Note> getAll(){
        List<Note> list=new ArrayList<>();
        SQLiteDatabase st=getReadableDatabase();
        String order="date DESC";
        Cursor rs=st.query("notes",null,null,null,null,null,order);
        while (rs!=null && rs.moveToNext()){
            int id=rs.getInt(0);
            String title=rs.getString(1);
            String content=rs.getString(2);
            String date=rs.getString(3);
            String time=rs.getString(4);
            list.add(new Note(id,title,content,date,time));
        }
        return list;
    }
    //Thêm thông tin
    public long Add(Note note){
        ContentValues values=new ContentValues();
        values.put("title",note.getTitle());
        values.put("content",note.getContent());
        values.put("date",note.getDate());
        values.put("time",note.getTime());
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.insert("notes",null,values);
    }
    //lấy thông tin thoe ngày hiện tại
    public List<Note> getBydate(String date){
        List<Note> list=new ArrayList<>();
        String item="date like ?";
        String[] whereArgs={date};
        SQLiteDatabase st=getReadableDatabase();
        Cursor rs=st.query("notes",null,item,whereArgs,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id=rs.getInt(0);
            String title=rs.getString(1);
            String content=rs.getString(2);
            String date1=rs.getString(3);
            String time=rs.getString(4);
            list.add(new Note(id,title,content,date1,time));
        }
        return list;
    }
    //Chỉnh sửa thông tin
    public int Update(Note note){
        ContentValues values=new ContentValues();
        values.put("title",note.getTitle());
        values.put("content",note.getContent());
        values.put("date",note.getDate());
        values.put("time",note.getTime());
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String item="id= ?";
        String[] whereArgs={Integer.toString(note.getId())};
        return sqLiteDatabase.update("notes",values,item,whereArgs);
    }
    //Xóa thông tin
    public int Delete(int id){
        String item="id= ?";
        String[] whereArgs={Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.delete("notes",item,whereArgs);
    }
    //Tim kiem thong tin
    public List<Note> getBySearch(String key) {
        List<Note> list = new ArrayList<>();
        String selection = "title LIKE ? OR content LIKE ?";
        String[] selectionArgs = new String[]{"%" + key + "%", "%" + key + "%"};

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("notes", null, selection, selectionArgs, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String date = cursor.getString(3);
            String time = cursor.getString(4);
            list.add(new Note(id, title, content, date, time));
        }

        if (cursor != null) {
            cursor.close();
        }

        return list;
    }
}
