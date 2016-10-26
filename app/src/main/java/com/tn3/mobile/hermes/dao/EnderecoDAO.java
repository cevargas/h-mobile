package com.tn3.mobile.hermes.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.tn3.mobile.hermes.models.Cidade;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.models.Endereco;

import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    public static final String TABELA = "enderecos";

    public static final String ID = "_id";
    public static final String ID_COLETA = "id_coleta";
    public static final String LOGRADOURO = "logradouro";
    public static final String NUMERO = "numero";
    public static final String COMPLEMENTO = "complemento";
    public static final String CIDADE = "cidade";
    public static final String UF = "estado";
    public static final String BAIRRO = "bairro";
    public static final String SETOR = "setor";
    public static final String FK_ENDERECO_COLETA = "fk_endereco_coleta";

    private Context context;

    public EnderecoDAO(Context context) {
        this.context = context;
    }

    public Endereco findEnderecoPelaColetaId (SQLiteDatabase db, Long id){

        Endereco endereco = new Endereco();
        Cursor cursor = null;

        try {
            //SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
            String query = "SELECT * FROM " + TABELA + " WHERE " + ID_COLETA + "=" + id;

            Log.d("query", query);
            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                endereco.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                endereco.setLogradouro(cursor.getString(cursor.getColumnIndex(LOGRADOURO)));
                endereco.setNumero(cursor.getInt(cursor.getColumnIndex(NUMERO)));
                endereco.setComplemento(cursor.getString(cursor.getColumnIndex(COMPLEMENTO)));
                endereco.setCidade(cursor.getString(cursor.getColumnIndex(CIDADE)));
                endereco.setEstado(cursor.getString(cursor.getColumnIndex(UF)));
                endereco.setBairro(cursor.getString(cursor.getColumnIndex(BAIRRO)));
                endereco.setSetor(cursor.getString(cursor.getColumnIndex(SETOR)));
            }

        } catch (SQLiteException e) {
            Log.d("EnderecoDAO_findEnde", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        //db.close();

        return endereco;
    }
}
