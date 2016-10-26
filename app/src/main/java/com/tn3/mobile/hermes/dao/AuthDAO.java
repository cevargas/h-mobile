package com.tn3.mobile.hermes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.tn3.mobile.hermes.dto.ResultWSDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthDAO {

    public static final String TABELA = "auth";

    public static final String ID = "_id";
    public static final String DHCONSULTA = "data";
    public static final String STATUS = "status";
    public static final String URL = "url";
    public static final String MSG = "msg";
    public static final String IMEI = "imei";
    public static final String TOKEN = "token";

    boolean stat;
    private Context context;

    public AuthDAO(Context context) {
        this.context = context;
    }

    public void salvar(ResultWSDTO resultWSDTO, String token, String imei) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        try {

            ContentValues auth = new ContentValues();
            stat = resultWSDTO.stat.equals("success");
            auth.put(AuthDAO.STATUS, stat);
            auth.put(AuthDAO.MSG, resultWSDTO.msg);
            auth.put(AuthDAO.DHCONSULTA, new Date().toString());
            auth.put(AuthDAO.URL, resultWSDTO.url);
            auth.put(AuthDAO.IMEI, imei);
            auth.put(AuthDAO.TOKEN, token);

            db.insert(AuthDAO.TABELA, null, auth);

        } catch (SQLiteException e) {
            Log.d("AuthDAO_salvar", e.getMessage());
        }
    }

    public boolean checkAuth(){

        Cursor cursor = null;
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        boolean ret = false;

        try {

            String query = "SELECT * FROM " + TABELA;
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                ret = cursor.getInt(cursor.getColumnIndex(STATUS)) != 0;
                Log.d("AUTHDB", String.valueOf(ret));
            }
        } catch (SQLiteException e) {
            Log.d("AuthDAO_checkAuth", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return ret;
    }

    public List<String> findAuth(){

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = null;
        List<String> dados = new ArrayList<>();

        try {

            String query = "SELECT * FROM "+TABELA;
            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                String token = cursor.getString(cursor.getColumnIndex(TOKEN));
                String imei = cursor.getString(cursor.getColumnIndex(IMEI));

                dados.add(token);
                dados.add(imei);
            }

        } catch (SQLiteException e) {
            Log.d("AuthDAO_findAuth", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return dados;
    }

    public void delete() {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        db.execSQL("DELETE FROM " + AuthDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ AuthDAO.TABELA +"'");
    }
}
