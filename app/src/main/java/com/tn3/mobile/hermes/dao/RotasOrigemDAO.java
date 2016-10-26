package com.tn3.mobile.hermes.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.tn3.mobile.hermes.models.Cidade;

public class RotasOrigemDAO {

    public static final String TABELA = "rotas_origem";

    public static final String ID = "_id";
    public static final String ID_ROTA = "id_rota";
    public static final String NOME = "nome";
    public static final String UF = "uf";
    public static final String FK_ROTAS_ORIGEM = "fk_rotas_origem";

    private Context context;

    public RotasOrigemDAO(Context context) {
        this.context = context;
    }

    public Cidade findRotasOrigemById (SQLiteDatabase db, Long id){

        Cidade cidade = new Cidade();
        Cursor cursor = null;

        try {

            String query = "SELECT * FROM "+TABELA+" WHERE " + ID_ROTA + "=" + id;

            Log.d("findRotasOrigemById", query);
            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                cidade.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                cidade.setUf(cursor.getString(cursor.getColumnIndex(UF)));
            }

        } catch (SQLiteException e) {
            Log.d("RotasOrigemDAO", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return cidade;
    }
}
