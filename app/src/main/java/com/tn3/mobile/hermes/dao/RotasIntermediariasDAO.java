package com.tn3.mobile.hermes.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.tn3.mobile.hermes.models.Cidade;

import java.util.ArrayList;
import java.util.List;

public class RotasIntermediariasDAO {

    public static final String TABELA = "rotas_intermediarias";

    public static final String ID = "_id";
    public static final String ID_ROTA = "id_rota";
    public static final String NOME = "nome";
    public static final String UF = "uf";
    public static final String FK_ROTAS_INTERMEDIARIAS = "fk_rotas_intermediarias";

    private Context context;

    public RotasIntermediariasDAO(Context context) {
        this.context = context;
    }

    public List<Cidade> findRotasIntermediariasById (SQLiteDatabase db, Long id){

        List<Cidade> cidades = new ArrayList<>();
        Cursor cursor = null;

        try {

            String query = "SELECT * FROM "+TABELA+" WHERE " + ID_ROTA + "=" + id;

            Log.d("findRotasIntermediarias", query);
            cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()) {
                Cidade cidade = new Cidade();
                cidade.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                cidade.setUf(cursor.getString(cursor.getColumnIndex(UF)));

                cidades.add(cidade);
            }

        } catch (SQLiteException e) {
            Log.d("RotasIntermediariasDAO", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return cidades;
    }
}
