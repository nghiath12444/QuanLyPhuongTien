package com.example.mock1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PhuongTienDAO {
    MySQL mySQL;
    SQLiteDatabase db;
    public PhuongTienDAO(Context context) {
        mySQL = new MySQL(context);
        db = mySQL.getWritableDatabase();
    }

    public static final String TB_Name = "phuongtien";
    public static final String sql = "Create table "+ TB_Name + "(" +
            "id int primary key not null," +
            "ten text not null, " +
            "loai text not null," +
            "tien float not null );";

    //insert
    public int them(PhuongTien phuongTien){
        ContentValues values = new ContentValues();
        values.put("id", phuongTien.getId());
        values.put("ten", phuongTien.getTen());
        values.put("loai", phuongTien.getLoai());
        values.put("tien", phuongTien.getTien());
        if (db.insert(TB_Name, null, values) > 0){
            return 1;
        }
        else return -1;
    }

    //list
    public List<PhuongTien> getList(){
        List<PhuongTien> list = new ArrayList<>();
        Cursor cursor = db.query(TB_Name, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setId(cursor.getInt(0));
            phuongTien.setTen(cursor.getString(1));
            phuongTien.setLoai(cursor.getString(2));
            phuongTien.setTien(cursor.getFloat(3));
            list.add(phuongTien);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //delete
    public int xoa(String id){
        if (db.delete(TB_Name, "id=?", new String[]{id})>0){
            return 1;
        }
        else return 1;
    }

    //update
    public int sua(String id, String ten, String loai, Float tien){
        ContentValues values = new ContentValues();
        values.put("ten", ten);
        values.put("loai", loai);
        values.put("tien", tien);
        if (db.update(TB_Name, values, "id=?", new String[]{id})>0){
            return 1;
        }
        else return -1;
    }

    //tên a->z
    public List<PhuongTien> getNameASC(){
        List<PhuongTien> list = new ArrayList<>();
        String sql = "SELECT * FROM phuongtien ORDER BY ten";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setId(cursor.getInt(0));
            phuongTien.setTen(cursor.getString(1));
            phuongTien.setLoai(cursor.getString(2));
            phuongTien.setTien(cursor.getFloat(3));
            list.add(phuongTien);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    //tên z->a
    public List<PhuongTien> getNameDESC(){
        List<PhuongTien> list = new ArrayList<>();
        String sql = "SELECT * FROM phuongtien ORDER BY ten DESC";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setId(cursor.getInt(0));
            phuongTien.setTen(cursor.getString(1));
            phuongTien.setLoai(cursor.getString(2));
            phuongTien.setTien(cursor.getFloat(3));
            list.add(phuongTien);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //giá tiền nhỏ->lớn
    public List<PhuongTien> getTienASC(){
        List<PhuongTien> list = new ArrayList<>();
        String sql = "SELECT * FROM phuongtien ORDER BY tien ASC";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setId(cursor.getInt(0));
            phuongTien.setTen(cursor.getString(1));
            phuongTien.setLoai(cursor.getString(2));
            phuongTien.setTien(cursor.getFloat(3));
            list.add(phuongTien);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    //giá tiền lớn->nhỏ
    public List<PhuongTien> getTienDESC(){
        List<PhuongTien> list = new ArrayList<>();
        String sql = "SELECT * FROM phuongtien ORDER BY tien DESC";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setId(cursor.getInt(0));
            phuongTien.setTen(cursor.getString(1));
            phuongTien.setLoai(cursor.getString(2));
            phuongTien.setTien(cursor.getFloat(3));
            list.add(phuongTien);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //giá min
    public List<PhuongTien> getTienMIN(float gia){
        List<PhuongTien> list = new ArrayList<>();
        String sql = "SELECT * FROM phuongtien WHERE tien >= ' " + gia +"'  ORDER BY tien DESC;";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setId(cursor.getInt(0));
            phuongTien.setTen(cursor.getString(1));
            phuongTien.setLoai(cursor.getString(2));
            phuongTien.setTien(cursor.getFloat(3));
            list.add(phuongTien);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //tìm kiếm
    public List<PhuongTien> search(String ten){
        List<PhuongTien> list = new ArrayList<>();
        String sql = "SELECT * FROM phuongtien WHERE ten = '" + ten +"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setId(cursor.getInt(0));
            phuongTien.setTen(cursor.getString(1));
            phuongTien.setLoai(cursor.getString(2));
            phuongTien.setTien(cursor.getFloat(3));
            list.add(phuongTien);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
