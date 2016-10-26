package com.tn3.mobile.hermes.dao;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class ChaveNfeDAO {

    public static final String TABELA = "chaves_nfe_coleta";

    public static final String ID = "_id";
    public static final String ID_COLETA = "id_coleta";
    public static final String CHAVE = "chave";
    public static final String FK_COLETAS_CHAVES = "fk_coleta_chaves";

    private Context context;

    public ChaveNfeDAO(Context context) {
        this.context = context;
    }

    public List<String> findChavesDaColetaById (SQLiteDatabase db, Long id){

        List<String> lista = new ArrayList<>();
        //SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = null;

        try {

            String query = "SELECT * FROM " + TABELA + " WHERE " + ID_COLETA + "=" + id;
            Log.d("query", query);
            cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()) {
                String chave = cursor.getString(cursor.getColumnIndex(CHAVE));
                lista.add(chave);
            }

        } catch (SQLiteException e) {
            Log.d("ChaveNfeDAO_findChaves", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return lista;
    }

    public void salvarChave(Long id, String chave) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        try {

            ContentValues chaves = new ContentValues();
            chaves.put(ChaveNfeDAO.ID_COLETA, id);
            chaves.put(ChaveNfeDAO.CHAVE, chave);
            db.insert(ChaveNfeDAO.TABELA, null, chaves);
            Log.d("SALVACHAVE", chave);

        } catch (SQLiteException e) {
            Log.d("ChaveNfeDAO_salvarChave", e.getMessage());

        } finally{
            //db.close();
        }
    }

    public void deleteChave(String chave){
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Log.i("ChaveNfeDAO", "Deletando chave " + chave);
        db.execSQL("DELETE FROM " + ChaveNfeDAO.TABELA + " WHERE " + ChaveNfeDAO.CHAVE +"='"+chave+"' ");
        //db.close();
    }
}
