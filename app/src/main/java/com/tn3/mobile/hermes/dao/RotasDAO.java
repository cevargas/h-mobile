package com.tn3.mobile.hermes.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.tn3.mobile.hermes.models.Cidade;
import com.tn3.mobile.hermes.models.Rota;
import java.util.List;

public class RotasDAO {

    public static final String TABELA = "rotas";

    public static final String ID = "_id";
    public static final String ID_MANIFESTO = "id_manifesto";
    public static final String DESCRICAO = "descricao";

    public static final String FK_MANIFESTO_ROTAS = "fk_manifesto_rotas";

    private Context context;

    public RotasDAO(Context context) {
        this.context = context;
    }

    public Rota findRotasManifestoById(SQLiteDatabase db, Long id){

        //SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Rota rota = new Rota();
        Cursor cursor = null;

        try {

            String query = "SELECT * FROM "+TABELA+"  WHERE " + ID_MANIFESTO + "=" + id;
            Log.d("findManifestoById", query);
            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                rota.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                rota.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));

                RotasOrigemDAO rotasOrigemDAO = new RotasOrigemDAO(context);
                Cidade origem = rotasOrigemDAO.findRotasOrigemById(db, rota.getId());
                rota.setOrigem(origem);

                RotasDestinoDAO rotasDestinoDAO = new RotasDestinoDAO(context);
                Cidade destino = rotasDestinoDAO.findRotasDestinoById(db, rota.getId());
                rota.setDestino(destino);

                RotasIntermediariasDAO rotasIntermediariasDAO = new RotasIntermediariasDAO(context);
                List<Cidade> cidades = rotasIntermediariasDAO.findRotasIntermediariasById(db, rota.getId());
                rota.setViagem(cidades);
            }

        } catch (SQLiteException e) {
            Log.d("RotasDAO", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return rota;
    }
}


