package com.example.todo_list.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo_list.model.Task;
import com.example.todo_list.model.User;

import java.util.ArrayList;

public class PortalDB extends SQLiteOpenHelper {
    private static String databaseName = "PortalDb";
    SQLiteDatabase PortalDb;

    public PortalDB(@Nullable Context context) {
        super(context, databaseName, null, 15);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create DB");
        db.execSQL("create table User(UserName text primary key ,Email text, password text not null)");
        db.execSQL("create table Task (title text,status text,UserName text,Foreign key(UserName) References User(UserName) )");
    }
    public String validateData(String userName, String password) {
        PortalDb = getReadableDatabase();
        String[] rowdetails = {"UserName"};
        String[] args = {userName, password};
        String UserName = "Not Found";
        Cursor c = PortalDb.query("User", rowdetails, "UserName=? and password = ?", args, null, null, null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                UserName = c.getString(0);
            }
        }
        PortalDb.close();

        return UserName;
    }
    public boolean addNewUser(User user) {
        PortalDb = getReadableDatabase();
        Cursor c = PortalDb.rawQuery("Select * from User where UserName=? ", new String[]{user.getUsername()});
        if (c.getCount() > 0) {
            return false;
        }

        ContentValues row = new ContentValues();
        row.put("Email", user.getEmail());
        row.put("UserName", user.getUsername());
        row.put("password", user.getPassword());
        PortalDb = getWritableDatabase();
        PortalDb.insert("User", null, row);
        PortalDb.close();
        return true;
    }
    public boolean addNewTask(Task task) {
        PortalDb = getReadableDatabase();
        Cursor c = PortalDb.rawQuery("Select * from Task where title=? ", new String[]{task.getTitle()});
        if (c.getCount() > 0) {
            return false;
        }
        ContentValues row = new ContentValues();
        row.put("title", task.getTitle());
        row.put("UserName", task.getUsername());
        row.put("status", task.getStatus());
        PortalDb = getWritableDatabase();
        PortalDb.insert("Task", null, row);
        PortalDb.close();
        return true;
    }
    public static String login(PortalDB db, String username, String password) {

        String validate = String.valueOf(db.validateData(username, password));
        if (validate == "Not Found") {
            System.out.println("not found");
            return "Not Found";
        }
        System.out.println("validate  " + validate);
        return validate;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Task");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
    public ArrayList RetrieveTask(String user,String type) {
        ArrayList list = new ArrayList<>();
        String title = "Error";
        PortalDb = getReadableDatabase();
        Cursor c = PortalDb.rawQuery("Select * from Task where UserName=? And status=? ", new String[]{user, type});
        if (c != null) {
            c.moveToFirst();
            while(!c.isAfterLast())
             {
                title = c.getString(0);
                list.add(title);
                c.moveToNext();
                System.out.println(c.toString());
            }
        }
        PortalDb.close();
        return list;
    }
    public void RemoveTask(String username, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Task", "UserName=? and title=?", new String[]{username, title});
        db.close();
    }
    public void DeleteAll(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Task", "UserName=?", new String[]{username});
        db.close();
    }
    public void updateStat(String username, String title) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", "completed");
        PortalDb = getReadableDatabase();
        PortalDb.update("Task", contentValues, "UserName=? and title=?", new String[]{username, title});
        PortalDb.close();
    }
}
