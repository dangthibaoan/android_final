package com.example.ezorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ezorder.Model.Food;
import com.example.ezorder.Model.Role;
import com.example.ezorder.Model.Table;
import com.example.ezorder.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "Database TAG";

    // TODO : Database info
    private static final String DATABASE_NAME = "OrderDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // TODO : Table User
    private static final String TABLE_USER = "tblUser";
    private static final String COLUMN_USER_ID = "User_ID";
    private static final String COLUMN_USER_NAME = "User_Name";
    private static final String COLUMN_USER_PHONE = "User_Phone";
    private static final String COLUMN_USER_ACCOUNT_NAME = "Account_name";
    private static final String COLUMN_USER_ACCOUNT_PASS = "Account_password";
    private static final String COLUMN_USER_ROLE_FK = "Role_ID";

    // TODO : Table Role
    private static final String TABLE_ROLE = "tblRole";
    private static final String COLUMN_ROLE_ID = "Role_ID";
    private static final String COLUMN_ROLE_NAME = "Role_Name";

    // TODO : Table "Table"
    private static final String TABLE_TABLE = "tblTable";
    private static final String COLUMN_TABLE_ID = "Table_ID";
    private static final String COLUMN_TABLE_NUMBER = "Table_number";
    private static final String COLUMN_TABLE_STATUS = "Table_status";

    // TODO : Table Food
    private static final String TABLE_FOOD = "tblFood";
    private static final String COLUMN_FOOD_ID = "Food_ID";
    private static final String COLUMN_FOOD_NAME = "Food_Name";
    private static final String COLUMN_FOOD_UNIT = "Food_Unit";
    private static final String COLUMN_FOOD_PRICE = "Food_Price";
    private static final String COLUMN_FOOD_IMAGE = "Food_Image";
    private static final String COLUMN_FOOD_STATUS = "Food_Status";

    // TODO : Table Order
    private static final String TABLE_ORDER = "tblOrder";
    private static final String COLUMN_ORDER_ID = "Order_ID";
    private static final String COLUMN_ORDER_NUMBER = "Order_Number";
    private static final String COLUMN_ORDER_TIME = "Order_Time";
    private static final String COLUMN_ORDER_NOTE = "Order_Note";
    private static final String COLUMN_ORDER_FOOD_FK = "Food_ID";
    private static final String COLUMN_ORDER_TABLE_FK = "Table_ID";

    // TODO : Table Bill
    private static final String TABLE_BILL = "tblBill";
    private static final String COLUMN_BILL_ID = "Bill_ID";
    private static final String COLUMN_BILL_TOTAL = "Bill_total";
    private static final String COLUMN_BILL_TIME = "Bill_time";
    private static final String COLUMN_BILL_USER_FK = "User_ID";
    private static final String COLUMN_BILL_ORDER_FK = "Order_ID";


    //TODO**: STRING CREATE TABLE

    // TODO : table User
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "( " +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_NAME + " TEXT," +
            COLUMN_USER_PHONE + " TEXT," +
            COLUMN_USER_ACCOUNT_NAME + " TEXT," +
            COLUMN_USER_ACCOUNT_PASS + " TEXT," +
            COLUMN_USER_ROLE_FK + " INTEGER REFERENCES " + TABLE_ROLE + ")";

    // TODO : table Role
    private static final String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE + "( " +
            COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ROLE_NAME + " TEXT )";

    // TODO : table Table
    private static final String CREATE_TABLE_TABLE = "CREATE TABLE " + TABLE_TABLE + "( " +
            COLUMN_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TABLE_NUMBER + " INTEGER," +
            COLUMN_TABLE_STATUS + " INTEGER )";

    // TODO : table Food
    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD + "( " +
            COLUMN_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_FOOD_NAME + " TEXT," +
            COLUMN_FOOD_PRICE + " INTEGER," +
            COLUMN_FOOD_IMAGE + " BLOB," +
            COLUMN_FOOD_UNIT + " TEXT," +
            COLUMN_FOOD_STATUS + " INTEGER )";

    // TODO : table Order
    private static final String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_ORDER + "( " +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ORDER_NUMBER + " INTEGER," +
            COLUMN_ORDER_TIME + " TEXT," +
            COLUMN_ORDER_NOTE + " TEXT," +
            COLUMN_ORDER_FOOD_FK + " INTEGER REFERENCES " + TABLE_FOOD + "," +
            COLUMN_ORDER_TABLE_FK + " INTEGER REFERENCES " + TABLE_TABLE + ")";

    // TODO : table Bill
    private static final String CREATE_TABLE_BILL = "CREATE TABLE " + TABLE_BILL + "( " +
            COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_BILL_TIME + " TEXT," +
            COLUMN_BILL_TOTAL + " INTEGER," +
            COLUMN_BILL_USER_FK + " INTEGER REFERENCES " + TABLE_USER + "," +
            COLUMN_BILL_ORDER_FK + " INTEGER REFERENCES " + TABLE_ORDER + ")";

    // TODO : STRING DROP
    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    private static final String DROP_TABLE_ROLE = "DROP TABLE IF EXISTS " + TABLE_ROLE;
    private static final String DROP_TABLE_TABLE = "DROP TABLE IF EXISTS " + TABLE_TABLE;
    private static final String DROP_TABLE_FOOD = "DROP TABLE IF EXISTS " + TABLE_FOOD;
    private static final String DROP_TABLE_ORDER = "DROP TABLE IF EXISTS " + TABLE_ORDER;
    private static final String DROP_TABLE_BILL = "DROP TABLE IF EXISTS " + TABLE_BILL;


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ROLE);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TABLE);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_BILL);

        Log.d(TAG, "onCreate: Running ...!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_BILL);
        db.execSQL(DROP_TABLE_ORDER);
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_ROLE);
        db.execSQL(DROP_TABLE_TABLE);
        db.execSQL(DROP_TABLE_FOOD);
        onCreate(db);

        Log.d(TAG, "onUpgrade: Running ...");
    }

//    TODO : Role _ method ...

    public long addRole(Role role) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLE_NAME, role.getRoleName());
        long res = database.insert(TABLE_ROLE, null, values);
        database.close();
        return res;
    }

    public int updateRole(Role role) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLE_NAME, role.getRoleName());
        int res = database.update(TABLE_ROLE, values, COLUMN_ROLE_ID + "=?",
                new String[]{String.valueOf(role.getRoleID())});
        database.close();
        return res;
    }

    public int deleteRole(Role role) {
        SQLiteDatabase database = this.getWritableDatabase();
        int res = database.delete(TABLE_ROLE, COLUMN_ROLE_ID + "=?",
                new String[]{String.valueOf(role.getRoleID())});
        database.close();
        return res;
    }

    public String getNamwRole(String id) {
        String name = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT Role_Name FROM " + TABLE_ROLE + " WHERE " + COLUMN_ROLE_ID + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{id});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return name;
    }

    public List<Role> getAllRole() {
        List<Role> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ROLE;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Role role = new Role(
                    cursor.getInt(0),
                    cursor.getString(1)
            );
            list.add(role);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //    TODO : User _ method ...

    public int checkUser(User user) {
        int res = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * " +
                " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ACCOUNT_NAME + "=? " +
                " AND " + COLUMN_USER_ACCOUNT_PASS + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{user.getAccountName(), user.getAccountPass()});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            res = cursor.getColumnCount();
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return res;
    }

    public long addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_ACCOUNT_NAME, user.getAccountName());
        values.put(COLUMN_USER_ACCOUNT_PASS, user.getAccountPass());
        values.put(COLUMN_USER_ROLE_FK, user.getUser_role());
        long res = database.insert(TABLE_USER, null, values);
        database.close();
        return res;
    }

    public int updateUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_ACCOUNT_NAME, user.getAccountName());
        values.put(COLUMN_USER_ACCOUNT_PASS, user.getAccountPass());
        values.put(COLUMN_USER_ROLE_FK, user.getUser_role());

        int res = database.update(TABLE_USER, values, COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(user.getUserID())});
        database.close();
        return res;
    }

    public int deleteUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        int res = database.delete(TABLE_USER, COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(user.getUserID())});
        database.close();
        return res;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
            list.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

//    TODO : Table method

    public long addTable(Table table) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_NUMBER, table.getNumber());
        values.put(COLUMN_TABLE_STATUS, table.getStatus());
        long res = database.insert(TABLE_TABLE, null, values);
        database.close();
        return res;
    }

    public int updateTable(Table table) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_NUMBER, table.getNumber());
        values.put(COLUMN_TABLE_STATUS, table.getStatus());
        int res = database.update(TABLE_TABLE, values, COLUMN_TABLE_ID + "=?",
                new String[]{String.valueOf(table.getTableID())});
        database.close();
        return res;
    }

    public int deleteTable(Table table) {
        SQLiteDatabase database = this.getWritableDatabase();
        int res = database.delete(TABLE_TABLE, COLUMN_TABLE_ID + "=?",
                new String[]{String.valueOf(table.getTableID())});
        database.close();
        return res;
    }

    public ArrayList<Table> getAllTable() {
        ArrayList<Table> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Table table = new Table(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2)
            );
            list.add(table);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //    TODO : Food method

    public long addFood(Food food) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, food.getFoodName());
        values.put(COLUMN_FOOD_UNIT, food.getFoodUnit());
        values.put(COLUMN_FOOD_PRICE, food.getFoodPrice());
        values.put(COLUMN_FOOD_IMAGE, food.getFoodImage());
        values.put(COLUMN_FOOD_STATUS, food.getFoodStatus());
        long res = database.insert(TABLE_FOOD, null, values);
        database.close();
        return res;
    }

    public int updateFood(Food food) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, food.getFoodName());
        values.put(COLUMN_FOOD_PRICE, food.getFoodPrice());
        values.put(COLUMN_FOOD_IMAGE, food.getFoodImage());
        values.put(COLUMN_FOOD_UNIT, food.getFoodUnit());
        values.put(COLUMN_FOOD_STATUS, food.getFoodStatus());

        int res = database.update(TABLE_FOOD, values, COLUMN_FOOD_ID + "=?",
                new String[]{String.valueOf(food.getFoodID())});
        database.close();
        return res;
    }

    public int deleteFood(Food food) {
        SQLiteDatabase database = this.getWritableDatabase();
        int res = database.delete(TABLE_FOOD, COLUMN_FOOD_ID + "=?",
                new String[]{String.valueOf(food.getFoodID())});
        database.close();
        return res;
    }

    public ArrayList<Food> getAllFood() {
        ArrayList<Food> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FOOD;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Food food = new Food(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5));
            list.add(food);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}

