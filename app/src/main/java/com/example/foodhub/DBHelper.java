package com.example.foodhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.foodhub.Models.OrdersModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
   final static String dbName="mydatabase.db";
  final static int dbVersion=3;
    public DBHelper(@Nullable Context context) {
        super(context,
                dbName,
                null,
                dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table orders "+
                        "(id integer primary key autoincrement," +
                "name text," +
                "phone text," +
                "price int," +
                "image int," +
                "quantity int," +
               "description text," +
                "foodname text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         sqLiteDatabase.execSQL("DROP table if exists orders");
         onCreate(sqLiteDatabase);
    }

    public boolean insertOrder(String name,String phone,int price,int image,String description,String foodName,int quantity)
    {
        SQLiteDatabase database=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("quantity",quantity);
       values.put("description",description);
        values.put("foodname",foodName);
       long id=database.insert("orders",null,values);
       if(id<0)
           return false; // nothing added in our database

        return true;
    }

    public ArrayList<OrdersModel> getOrders()
    {
        ArrayList<OrdersModel> orders=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();

        Cursor cursor=database.rawQuery("Select id,foodname,image,price from orders",null);
        //System.out.println("Hello");
        if(cursor.moveToFirst())
        {
            cursor.moveToPrevious();
            while (cursor.moveToNext())
            {
                OrdersModel model=new OrdersModel();

                model.setOrderNumber(cursor.getInt(0)+"");
                model.setSoldItemName(cursor.getString(1));
                model.setOrderImage(cursor.getInt(2));
                model.setPrice(cursor.getInt(3)+"");
               // System.out.println(cursor.)
                orders.add(model);
            }
        }
        cursor.close();
        database.close();
        return orders;
    }

    public Cursor getOrderById(int id)
    {
        SQLiteDatabase database=this.getWritableDatabase();

        Cursor cursor=database.rawQuery("Select * from orders where id = "+ id,null);
        //System.out.println("Hello");


        if(cursor!=null)
            cursor.moveToFirst();

        
        return cursor;
    }

    public boolean updateOrder(String name,String phone,int price,int image,String description,String foodName,int quantity,int id)
    {
        SQLiteDatabase database=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("quantity",quantity);
        values.put("description",description);
        values.put("foodname",foodName);
        long row=database.update("orders",values,"id="+id,null);
        if(row<0)
            return false; // nothing added in our database

        return true;
    }

    public int deleteOrder(String id)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        return  database.delete("orders","id="+id,null);
    }
}
