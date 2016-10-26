package com.tn3.mobile.hermes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.tn3.mobile.hermes.dto.ColetaDTO;
import com.tn3.mobile.hermes.dto.ManifestosDTO;
import com.tn3.mobile.hermes.dto.CidadeDTO;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.models.Endereco;
import com.tn3.mobile.hermes.models.Manifestos;
import com.tn3.mobile.hermes.models.Rota;
import com.tn3.mobile.hermes.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManifestosDAO {

    public static final String TABELA = "manifestos";

    public static final String ID = "_id";
    public static final String DATA_MANIFESTO = "data";
    public static final String SITUACAO = "situacao_manifesto";
    public static final String TIPO = "tipo";
    public static final String ODOMETRO = "odometro";
    public static final String SINCRONIZADO = "sincronizado";
    public static final String DH_SINCRONIZADO = "dhsincronizado";

    private Utils utils = new Utils();
    private Context context;

    public Locale BRAZIL = new Locale("pt", "BR");

    public ManifestosDAO(Context context) {
        this.context = context;
    }

    public Manifestos findManifestoById(String id){

        Manifestos manifesto = new Manifestos();
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = null;

        try {

            Log.d("IDMANIFEST", id);

            String query = "SELECT * FROM "+TABELA+"  WHERE " + ID + "=" + id;

            Log.d("query", query);
            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                manifesto.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                String data = cursor.getString(cursor.getColumnIndex(DATA_MANIFESTO));
                manifesto.setDataManifesto(utils.dbStringToDateForDb(data));
                manifesto.setSituacaoManifesto(cursor.getString(cursor.getColumnIndex(SITUACAO)));
                manifesto.setTipoManifesto(cursor.getString(cursor.getColumnIndex(TIPO)));

                //ROTA
                Rota rota = new RotasDAO(context).findRotasManifestoById(db, manifesto.getId());
                manifesto.setRota(rota);

                //COLETAS LISTA
                List<Coletas> coletas = new ColetasDAO(context).findColetaManifestoById(db, manifesto.getId());
                manifesto.setColetas(coletas);
            }

        } catch (SQLiteException e) {
            Log.d("ManifestosDAO_findManif", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return manifesto;
    }

    public List<Manifestos> list() {

        List<Manifestos> manifesto = new ArrayList<>();
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] colunas = new String[] { ID, DATA_MANIFESTO,
                    SITUACAO, ODOMETRO, SINCRONIZADO, TIPO, DH_SINCRONIZADO };

            cursor = db.query(TABELA, colunas, null, null, null, null, DATA_MANIFESTO+" DESC");

            while(cursor.moveToNext()) {

                Manifestos manifestos = new Manifestos();
                manifestos.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                String data = cursor.getString(cursor.getColumnIndex(DATA_MANIFESTO));
                manifestos.setDataManifesto(utils.dbStringToDateForDb(data));
                String ndata = cursor.getString(cursor.getColumnIndex(DH_SINCRONIZADO));
                if(ndata != null) {
                    manifestos.setDhsincronizado(utils.dbStringToDate(ndata));
                }

                manifestos.setSituacaoManifesto(cursor.getString(cursor.getColumnIndex(SITUACAO)));
                manifestos.setTipoManifesto(cursor.getString(cursor.getColumnIndex(TIPO)));
                manifestos.setOdometro(cursor.getInt(cursor.getColumnIndex(ODOMETRO)));
                boolean sync = cursor.getInt(cursor.getColumnIndex(SINCRONIZADO)) > 0;
                manifestos.setSincronizado(sync);

                //ROTA
                Rota rota = new RotasDAO(context).findRotasManifestoById(db, manifestos.getId());
                manifestos.setRota(rota);

                //COLETAS LISTA
                List<Coletas> coletas = new ColetasDAO(context).findColetaManifestoById(db, manifestos.getId());
                manifestos.setColetas(coletas);

                manifesto.add(manifestos);
            }
        } catch (SQLiteException e) {
            Log.d("ManifestosDAO_list", e.getMessage());
        } finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return manifesto;
    }

    public boolean[] salvar(ManifestosDTO[] manifestosDTO) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        boolean insertManifest = false;
        boolean insertAddColetas = false;

        try {

            for(ManifestosDTO m: manifestosDTO) {

                String query = "SELECT * FROM "+ManifestosDAO.TABELA+"  WHERE " + ID + "=" + m.id;
                Log.d("query", query);
                Cursor ccursor = db.rawQuery(query, null);

                if(!ccursor.moveToFirst()) {

                    insertManifest = true;

                    Log.d("CHECKMANIFEXIST", "Salvando novo manifesto para ID " + m.id);

                    ContentValues manifest = new ContentValues();
                    manifest.put(ManifestosDAO.ID, m.id);
                    manifest.put(ManifestosDAO.DATA_MANIFESTO, m.data);
                    manifest.put(ManifestosDAO.SITUACAO, m.motivo);
                    manifest.put(ManifestosDAO.TIPO, m.tipo);
                    db.insert(ManifestosDAO.TABELA, null, manifest);

                    ContentValues rota = new ContentValues();
                    rota.put(RotasDAO.ID, m.rota.id);
                    rota.put(RotasDAO.ID_MANIFESTO, m.id);
                    rota.put(RotasDAO.DESCRICAO, m.rota.desc);
                    db.insert(RotasDAO.TABELA, null, rota);

                    ContentValues rotaOrigem = new ContentValues();
                    rotaOrigem.put(RotasOrigemDAO.ID_ROTA, m.rota.id);
                    rotaOrigem.put(RotasOrigemDAO.NOME, m.rota.origem.nome);
                    rotaOrigem.put(RotasOrigemDAO.UF, m.rota.origem.uf);
                    db.insert(RotasOrigemDAO.TABELA, null, rotaOrigem);

                    ContentValues rotaDestino = new ContentValues();
                    rotaDestino.put(RotasDestinoDAO.ID_ROTA, m.rota.id);
                    rotaDestino.put(RotasDestinoDAO.NOME, m.rota.destino.nome);
                    rotaDestino.put(RotasDestinoDAO.UF, m.rota.destino.uf);
                    db.insert(RotasDestinoDAO.TABELA, null, rotaDestino);

                    for (CidadeDTO intermediariasDAO : m.rota.intermediarias) {
                        ContentValues rotaIntermediaria = new ContentValues();
                        rotaIntermediaria.put(RotasIntermediariasDAO.ID_ROTA, m.rota.id);
                        rotaIntermediaria.put(RotasIntermediariasDAO.NOME, intermediariasDAO.nome);
                        rotaIntermediaria.put(RotasIntermediariasDAO.UF, intermediariasDAO.uf);
                        db.insert(RotasIntermediariasDAO.TABELA, null, rotaIntermediaria);
                    }

                    for (ColetaDTO coletasList : m.coletaList) {
                        ContentValues coleta = new ContentValues();

                        coleta.put(ColetasDAO.ID, coletasList.id);
                        coleta.put(ColetasDAO.ID_MANIFESTO, m.id);
                        coleta.put(ColetasDAO.STATUS_COLETA, coletasList.status);
                        coleta.put(ColetasDAO.STATUS_APP, "1");
                        coleta.put(ColetasDAO.MOTIVO_CANCELAMENTO, coletasList.motivo);
                        coleta.put(ColetasDAO.CARGA_TOTAL, coletasList.peso);
                        coleta.put(ColetasDAO.QTD_NFES, coletasList.qtdnfe);
                        coleta.put(ColetasDAO.QTD_VOLS, coletasList.qtdvolumes);
                        coleta.put(ColetasDAO.VALOR_NFE, coletasList.valornfe);
                        coleta.put(ColetasDAO.HORA_FIXA, coletasList.horafixa);
                        coleta.put(ColetasDAO.HORA_LIMITE, coletasList.horalimite);
                        coleta.put(ColetasDAO.NOME_CLIENTE, coletasList.emissor.nome);
                        coleta.put(ColetasDAO.CNPJ_CLIENTE, coletasList.emissor.cnpjCpf);
                        db.insert(ColetasDAO.TABELA, null, coleta);

                        ContentValues endereco = new ContentValues();
                        endereco.put(EnderecoDAO.ID_COLETA, coletasList.id);
                        endereco.put(EnderecoDAO.LOGRADOURO, coletasList.emissor.logradouro);
                        endereco.put(EnderecoDAO.COMPLEMENTO, coletasList.emissor.logrCom);
                        endereco.put(EnderecoDAO.NUMERO, coletasList.emissor.logrNum);
                        endereco.put(EnderecoDAO.BAIRRO, coletasList.emissor.bairro);
                        endereco.put(EnderecoDAO.SETOR, coletasList.setor);
                        endereco.put(EnderecoDAO.UF, coletasList.emissor.cidade.uf);
                        endereco.put(EnderecoDAO.CIDADE, coletasList.emissor.cidade.nome);
                        db.insert(EnderecoDAO.TABELA, null, endereco);
                    }

                    Log.i("ManifestosDAO", "Salvando Manifestos para ID " + m.id);

                    ccursor.close();
                }
                else {
                    Log.d("ManifestosDAO", "Manifesto ja existe..nao vai inserir ID " + m.id);
                    Log.d("ManifestosDAO", "Vai verificar se coletas foram adicionadas ao manifesto...");

                    for (ColetaDTO coletasList : m.coletaList) {

                        String qrycols = "SELECT * FROM "+ColetasDAO.TABELA+" " +
                                " WHERE " + ColetasDAO.ID_MANIFESTO + "=" + m.id +"" +
                                " AND " +  ColetasDAO.ID + "=" + coletasList.id;
                        Log.d("query_col", qrycols);

                        Cursor colcursor = db.rawQuery(qrycols, null);

                        if(!colcursor.moveToFirst()) {

                            insertAddColetas = true;

                            Log.d("NOVACOLETA", "Encontrou nova coleta para adicionar ao manifesto " + m.id);
                            ContentValues addcoleta = new ContentValues();
                            addcoleta.put(ColetasDAO.ID, coletasList.id);
                            addcoleta.put(ColetasDAO.ID_MANIFESTO, m.id);
                            addcoleta.put(ColetasDAO.STATUS_COLETA, coletasList.status);
                            addcoleta.put(ColetasDAO.STATUS_APP, "1");
                            addcoleta.put(ColetasDAO.MOTIVO_CANCELAMENTO, coletasList.motivo);
                            addcoleta.put(ColetasDAO.CARGA_TOTAL, coletasList.peso);
                            addcoleta.put(ColetasDAO.QTD_NFES, coletasList.qtdnfe);
                            addcoleta.put(ColetasDAO.QTD_VOLS, coletasList.qtdvolumes);
                            addcoleta.put(ColetasDAO.VALOR_NFE, coletasList.valornfe);
                            addcoleta.put(ColetasDAO.HORA_FIXA, coletasList.horafixa);
                            addcoleta.put(ColetasDAO.HORA_LIMITE, coletasList.horalimite);
                            addcoleta.put(ColetasDAO.NOME_CLIENTE, coletasList.emissor.nome);
                            addcoleta.put(ColetasDAO.CNPJ_CLIENTE, coletasList.emissor.cnpjCpf);
                            db.insert(ColetasDAO.TABELA, null, addcoleta);

                            ContentValues endereco = new ContentValues();
                            endereco.put(EnderecoDAO.ID_COLETA, coletasList.id);
                            endereco.put(EnderecoDAO.LOGRADOURO, coletasList.emissor.logradouro);
                            endereco.put(EnderecoDAO.COMPLEMENTO, coletasList.emissor.logrCom);
                            endereco.put(EnderecoDAO.NUMERO, coletasList.emissor.logrNum);
                            endereco.put(EnderecoDAO.BAIRRO, coletasList.emissor.bairro);
                            endereco.put(EnderecoDAO.SETOR, coletasList.setor);
                            endereco.put(EnderecoDAO.UF, coletasList.emissor.cidade.uf);
                            endereco.put(EnderecoDAO.CIDADE, coletasList.emissor.cidade.nome);
                            db.insert(EnderecoDAO.TABELA, null, endereco);
                        }
                        colcursor.close();
                    }

                    if(!insertAddColetas) {
                        Log.d("ManifestosDAO", "Nenhuma coleta adicionada!");
                    }

                    ccursor.close();
                }
            }

        } catch (SQLiteException e) {
            Log.d("ManifestosDAO_salvar", e.getMessage());
        } finally {
            //db.close();
        }

        return new boolean[] {insertManifest, insertAddColetas};
    }

    public void delete() {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        //DELETA TODOS OS DADOS, PARA INSERIR DE NOVO
        db.execSQL("DELETE FROM " + ManifestosDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ ManifestosDAO.TABELA +"'");

        db.execSQL("DELETE FROM " + RotasDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ RotasDAO.TABELA +"'");

        db.execSQL("DELETE FROM " + RotasOrigemDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ RotasOrigemDAO.TABELA +"'");

        db.execSQL("DELETE FROM " + RotasDestinoDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ RotasDestinoDAO.TABELA +"'");

        db.execSQL("DELETE FROM " + RotasIntermediariasDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ RotasIntermediariasDAO.TABELA +"'");

        db.execSQL("DELETE FROM " + ColetasDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ ColetasDAO.TABELA +"'");

        db.execSQL("DELETE FROM " + EnderecoDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ EnderecoDAO.TABELA +"'");

        db.execSQL("DELETE FROM " + ChaveNfeDAO.TABELA);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+ ChaveNfeDAO.TABELA +"'");

        Log.i("ManifestosDAO", "Deletou Manifestos...");

        //db.close();
    }
}
